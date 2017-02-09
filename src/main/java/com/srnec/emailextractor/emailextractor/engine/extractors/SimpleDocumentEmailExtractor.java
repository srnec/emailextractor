package com.srnec.emailextractor.emailextractor.engine.extractors;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;

import com.srnec.emailextractor.emailextractor.engine.AbstractEmailExtractor;

/**
 * Simple document email extractor used for files like {@code .txt} {@code .rtf} {@code .html}.
 * 
 * @author vape
 */
public class SimpleDocumentEmailExtractor extends AbstractEmailExtractor {

	/**
	 * Constructor.
	 * 
	 * @param file
	 */
	public SimpleDocumentEmailExtractor(File file) {
		super(file);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getText() throws IOException {
		FileInputStream inputStream = new FileInputStream(getFile());
		String text = IOUtils.toString(inputStream, Charset.defaultCharset());
		IOUtils.closeQuietly(inputStream);
		return text;
	}

}
