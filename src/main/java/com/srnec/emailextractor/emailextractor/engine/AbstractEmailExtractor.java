package com.srnec.emailextractor.emailextractor.engine;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.srnec.emailextractor.emailextractor.exceptions.EmailExtractorException;

/**
 * Base class for Email extractors.
 * 
 * @author vape
 */
public abstract class AbstractEmailExtractor implements EmailExtractor {

	/** The valid email REGEX pattern. */
	private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}", Pattern.CASE_INSENSITIVE);

	private File file;
	
	/**
	 * Constructor.
	 * 
	 * @param file to be processed
	 */
	public AbstractEmailExtractor(File file) {
		this.file = file;
	}
	
	/**
	 * Extracts document into text (String).
	 * 
	 * @return
	 * @throws IOException
	 */
	protected abstract String getText() throws IOException;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<String> getEmail() {
		Matcher matcher;
		try {
			matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(getText());
	        if (matcher.find()) {
	        	return Optional.of(matcher.group());
	        }
		} catch (IOException e) {
			throw new EmailExtractorException(String.format("Issue occured with file: %s", getFile().getAbsolutePath()), e);
		}
        
        return Optional.empty();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getAllEmails() {
		Set<String> result = new HashSet<>(3);
		
		try {
			Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(getText());
			while (matcher.find()) {
				result.add(matcher.group());
			}
		} catch (IOException e) {
			throw new EmailExtractorException(String.format("Issue occured with file: %s", getFile().getAbsolutePath()), e);
		}
        
        return result;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public File getFile() {
		return file;
	}
	
}
