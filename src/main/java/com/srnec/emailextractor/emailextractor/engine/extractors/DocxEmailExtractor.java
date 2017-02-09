package com.srnec.emailextractor.emailextractor.engine.extractors;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import com.srnec.emailextractor.emailextractor.engine.AbstractEmailExtractor;

/**
 * Email extractor used for {@code .docx} files.
 * 
 * @author vape
 */
public class DocxEmailExtractor extends AbstractEmailExtractor {
	
	/**
	 * Constructor.
	 * 
	 * @param file
	 */
	public DocxEmailExtractor(File file) {
		super(file);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getText() throws IOException {
		try (XWPFWordExtractor extractor = new XWPFWordExtractor(new XWPFDocument(new FileInputStream(getFile())))) {
			return extractor.getText();
		}
	}

}
