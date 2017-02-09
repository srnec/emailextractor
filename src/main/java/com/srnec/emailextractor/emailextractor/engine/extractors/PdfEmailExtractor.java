package com.srnec.emailextractor.emailextractor.engine.extractors;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import com.srnec.emailextractor.emailextractor.engine.AbstractEmailExtractor;

/**
 * Email extractor used for {@code .pdf} files.
 * 
 * @author vape
 */
public class PdfEmailExtractor extends AbstractEmailExtractor {

	/**
	 * Constructor.
	 * 
	 * @param file
	 */
	public PdfEmailExtractor(File file) {
		super(file);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getText() throws IOException {
		StringBuilder text = new StringBuilder();
		
		PdfReader reader = new PdfReader(new FileInputStream(getFile()));
		PdfReaderContentParser parser = new PdfReaderContentParser(reader);
		TextExtractionStrategy strategy;
		for (int i = 1; i <= reader.getNumberOfPages(); i++) {
			strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
			text.append(strategy.getResultantText()).append(System.lineSeparator());
		}
		reader.close();
		
		return text.toString();
	}

}
