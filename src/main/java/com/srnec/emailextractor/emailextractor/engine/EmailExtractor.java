package com.srnec.emailextractor.emailextractor.engine;

import java.io.File;
import java.util.Optional;
import java.util.Set;

/**
 * Interface defining an email extractor.
 * 
 * @author vape
 */
public interface EmailExtractor {

	/**
	 * Gets all emails.
	 * 
	 * @return set of all emails
	 * @throws Exception
	 */
	Set<String> getAllEmails() throws Exception;
	
	/**
	 * Gets an email.
	 * 
	 * @return an email
	 * @throws Exception
	 */
	Optional<String> getEmail() throws Exception;
	
	/**
	 * Returns the {@link File} for given extractor.
	 * 
	 * @return file
	 */
	File getFile();
}
