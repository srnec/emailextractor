package com.srnec.emailextractor.emailextractor.engine;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.srnec.emailextractor.emailextractor.exceptions.EmailExtractorException;

/**
 * Defines the supported file extensions.
 * 
 * @author vape
 */
public enum FileExtension {
	DOC("doc"), DOCX("docx"), RTF("rtf"), PDF("pdf"), HTML("html"), TXT("txt");
	
	private String extension;
	
	FileExtension(String extension) {
		this.extension = extension.toLowerCase();
	}
	
	/**
	 * Gets extension for given {@link FileExtension}}.
	 * 
	 * @return string representation of FileExtension
	 */
	public String getExtension() {
		return extension;
	}
	
	/**
	 * Parses given {@code extension} into {@link FileExtension}.
	 * @param extension
	 * @return FileExtension instance
	 * @throws EmailExtractorException if given exntension is not supported
	 */
	public static FileExtension parse(String extension) {
		return Arrays.stream(values())
				.filter((f) -> f.getExtension().equals(extension.toLowerCase()))
				.findFirst()
				.orElseThrow(() -> new EmailExtractorException(String.format("File extension '%s' is not supported!", extension)));
	}
	
	/**
	 * Returns set of supported file extensions.
	 * 
	 * @return set of supported file extensions
	 */
	public static Set<String> getSupportedExtensions() {
		Set<String> extensions = new HashSet<>();
		for (FileExtension fileExtension : values()) {
			extensions.add(fileExtension.getExtension().toLowerCase());
		}
		return extensions;
	}
	
}
