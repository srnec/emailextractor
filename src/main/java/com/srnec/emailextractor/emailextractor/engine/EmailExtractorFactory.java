package com.srnec.emailextractor.emailextractor.engine;

import java.io.File;

import org.apache.commons.io.FilenameUtils;

import com.srnec.emailextractor.emailextractor.engine.extractors.DocEmailExtractor;
import com.srnec.emailextractor.emailextractor.engine.extractors.DocxEmailExtractor;
import com.srnec.emailextractor.emailextractor.engine.extractors.PdfEmailExtractor;
import com.srnec.emailextractor.emailextractor.engine.extractors.SimpleDocumentEmailExtractor;
import com.srnec.emailextractor.emailextractor.exceptions.EmailExtractorException;

/**
 * Factory class for {@link EmailExtractor}s.
 * 
 * @author vape
 */
public class EmailExtractorFactory {
	
	/**
	 * Private constructor.
	 */
	private EmailExtractorFactory() {
		// factory class
	}
	
	/**
	 * Factory method for creating {@link EmailExtractor}} for given {@code file}.
	 * 
	 * @param file
	 * @return concrete EmailExtractor implementation
	 */
	public static EmailExtractor getExtractor(File file) {
		FileExtension fileExtension = FileExtension.parse(FilenameUtils.getExtension(file.getName().toLowerCase()));

		switch (fileExtension) {
		case DOC:
			return new DocEmailExtractor(file);
		case DOCX:
			return new DocxEmailExtractor(file);
		case PDF:
			return new PdfEmailExtractor(file);
		case RTF:
		case HTML:
		case TXT:
			return new SimpleDocumentEmailExtractor(file);
		default:
			throw new EmailExtractorException(
					String.format("For '%s' file extension there is no extractor defined!", fileExtension.name()));
		}
	}
}
