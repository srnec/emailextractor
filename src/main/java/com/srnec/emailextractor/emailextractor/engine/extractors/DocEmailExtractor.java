package com.srnec.emailextractor.emailextractor.engine.extractors;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hwpf.extractor.WordExtractor;

import com.srnec.emailextractor.emailextractor.engine.AbstractEmailExtractor;

/**
 * Email extractor used for {@code .doc} files.
 * 
 * @author vape
 */
public class DocEmailExtractor extends AbstractEmailExtractor {
	
	/**
	 * Constructor.
	 * 
	 * @param file
	 */
	public DocEmailExtractor(File file) {
		super(file);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getText() throws IOException {
		try (WordExtractor extractor = new WordExtractor(new FileInputStream(getFile()))) {
			return extractor.getText();
		}
	}

}
