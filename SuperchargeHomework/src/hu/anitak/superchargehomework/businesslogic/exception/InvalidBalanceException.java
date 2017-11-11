package hu.anitak.superchargehomework.businesslogic.exception;

public class InvalidBalanceException extends Exception {
	private static final String MESSAGE = "You don't have enough money to finish the operation.";
	
	public InvalidBalanceException() {
		super(MESSAGE);
	}
}
