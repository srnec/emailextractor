package com.srnec.emailextractor.emailextractor.exceptions;

/**
 * Exception thrown by email extractor.
 * 
 * @author vape
 */
public class EmailExtractorException extends RuntimeException {

	private static final long serialVersionUID = -4749567625173016165L;

	/**
	 * @param message
	 */
    public EmailExtractorException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public EmailExtractorException(Throwable cause) {
        super(cause);
    }
    
	/**
	 * 
	 * @param message
	 * @param cause
	 */
    public EmailExtractorException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
