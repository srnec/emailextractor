package com.srnec.emailextractor.emailextractor.engine;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;

import com.srnec.emailextractor.emailextractor.exceptions.EmailExtractorException;
import com.srnec.emailextractor.emailextractor.utils.CSVUtils;

/**
 * The Extractor engine class.
 * 
 * @author vape
 */
public class ExtractorEngine {

	private static final String OUTPUT_FILENAME = "output.csv";
	private static final String ERROR_LIST_FILENAME = "errorList.txt";

	private List<String[]> result = Collections.synchronizedList(new ArrayList<>());
	private List<String> unparsedFiles = Collections.synchronizedList(new ArrayList<>());

	private String sourcePath;
	private File outputFile;
	private Path errorFileOutputFile;
	private int maxDepth;
	private boolean debugMode;
	private int time;
	
	private ExecutorService executorService;

	/**
	 * Constructor.
	 * 
	 * @param inputFilePath input folder where it starts searching for files
	 * @param outputFilePath output folder for result file
	 * @param depth specifies the max depth level walking the tree file
	 * @param debugMode
	 * @param time specifies max. execution time in hours 
	 */
	public ExtractorEngine(String inputFilePath, String outputFilePath, int depth, boolean debugMode, int time) {
		this.sourcePath = inputFilePath;
		this.outputFile = Paths.get(outputFilePath, OUTPUT_FILENAME).toFile();
		this.errorFileOutputFile = Paths.get(outputFilePath, ERROR_LIST_FILENAME);
		this.maxDepth = depth;
		this.debugMode = debugMode;
		this.time = time;
		
		this.executorService = Executors.newWorkStealingPool();
	}

	/**
	 * Extract emails for given input parameters passed 
	 * to {@link ExtractorEngine#ExtractorEngine(String, String, int, boolean, int)}.
	 */
	public void extractEmails() {
		final LocalTime startTime = LocalTime.now();
		if (isDebug()) {
			System.err.println("Started at " + startTime);
		}

		System.err.println("Source folder: " + sourcePath);
		System.err.println("Output file: " + outputFile);
		System.err.println("=============================");
		System.err.println("Collecting email addresses...");

		try (Stream<Path> fileTree = Files.walk(Paths.get(sourcePath), maxDepth, FileVisitOption.FOLLOW_LINKS)) {
			fileTree.filter((path) -> Files.isRegularFile(path) && isFileExtensionSupported(path.getFileName().toString()))
					.forEach(this::parseDocument);

			executorService.shutdown();
			executorService.awaitTermination(time, TimeUnit.HOURS);
			
			System.err.println("=============================");
			System.err.println("Result: found " + result.size() + " email addresses");
			System.err.println("=============================");

			System.err.println("Creating " + outputFile.toPath());
			CSVUtils.write(outputFile, result);
			
			if (!unparsedFiles.isEmpty()) {
				Files.write(errorFileOutputFile, unparsedFiles, StandardOpenOption.CREATE);
				System.err.println("Some files caused problems. You can find a list of those files here: " + errorFileOutputFile.toString());
			}

			System.err.println("Program successfully finished!");

			if (isDebug()) {
				final LocalTime endTime = LocalTime.now();
				System.err.println("Ended at " + endTime);
				System.err.println("Extraction took: " + LocalTime.MIN.plus(startTime.until(endTime, ChronoUnit.SECONDS), ChronoUnit.SECONDS));
			}
			
		} catch (IOException e) {
			throw new EmailExtractorException("An exception occured during walking the file tree.", e);
		} catch (InterruptedException e) {
			System.err.println(String.format("Error occured! Program was running more than %d hours! "
							+ "Please check the depth of file tree or increase time via --time parameter.", time));
		}
	}

	private void parseDocument(Path path) {
		executorService.submit(new ExtractionJob(EmailExtractorFactory.getExtractor(path.toFile())));
	}
	
	/**
	 * The implementation of single extration job which is later run by ExecutorService.
	 * 
	 * @author vape
	 */
	private class ExtractionJob implements Runnable {
		
		private EmailExtractor extractor;
		
		public ExtractionJob(EmailExtractor extractor) {
			this.extractor = extractor;
		}

		@Override
		public void run() {
			try {
				extractor.getEmail().ifPresent((email) -> result.add(new String[] { extractor.getFile().getName(), email }));
			} catch (Exception e) {
				if (isDebug()) {
					System.err.println(String.format("Cannot extract text from: %s", extractor.getFile().getAbsolutePath()));
					e.printStackTrace();
				}
				unparsedFiles.add(extractor.getFile().getAbsolutePath());
			}
		}
		
	}

	private static boolean isFileExtensionSupported(String filename) {
		return FilenameUtils.isExtension(filename.toLowerCase(), FileExtension.getSupportedExtensions());
	}
	
	private boolean isDebug() {
		return debugMode;
	}

}
