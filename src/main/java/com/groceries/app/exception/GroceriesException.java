package com.groceries.app.exception;

public class GroceriesException extends Exception {

	private static final long serialVersionUID = 1L;

	private String message;

	@Override
	public String getMessage() {
		return message;
	}

	public GroceriesException(String message, Throwable cause) {
		super(message, cause);
		this.message = message;
	}

	public GroceriesException(String message) {
		super(message);
		this.message = message;
	}

	public GroceriesException(Throwable cause) {
		super(cause);
		this.message = cause.getMessage();
	}

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();
		builder.append("Groceries-Ordering-Exception [message=").append(message).append("]");
		return builder.toString();
	}
}
