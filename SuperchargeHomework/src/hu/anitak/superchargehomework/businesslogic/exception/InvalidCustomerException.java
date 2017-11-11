package hu.anitak.superchargehomework.businesslogic.exception;

public class InvalidCustomerException extends Exception {
	private static final String MESSAGE = "Invalid customer, operation cannot be executed!";
	
	public InvalidCustomerException() {
		super(MESSAGE);
	}
}
