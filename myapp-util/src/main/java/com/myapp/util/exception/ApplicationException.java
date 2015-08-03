package com.myapp.util.exception;

public class ApplicationException extends RuntimeException {
	

	private static final long serialVersionUID = 4265758284484258031L;

	public static final String UNHANDLED_EXCEPTION_TXT = "Unhandled Exception !!! ";
	
	private String message;
	
	public ApplicationException(final String message) {
		this.setMessage(message);
	}

	public ApplicationException(final String message, final Throwable throwable) {
		super(throwable);
		this.setMessage(message);
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}	


}
