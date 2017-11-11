package hu.anitak.superchargehomework.businesslogic.exception;

public class InvalidTransferBeneficiaryException extends Exception {
	private static final String MESSAGE = "Invalid transer beneficiary, operation cannot be executed!";
	
	public InvalidTransferBeneficiaryException() {
		super(MESSAGE);
	}
}
