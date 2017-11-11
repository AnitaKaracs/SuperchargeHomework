package hu.anitak.superchargehomework.businesslogic.object;

public class OperationResponse {
	private OperationResult operationResult;
	private Exception exception;
	
	public OperationResult getOperationResult() {
		return operationResult;
	}
	
	public void setOperationResult(OperationResult operationResult) {
		this.operationResult = operationResult;
	}
	
	public Exception getException() {
		return exception;
	}
	
	public void setException(Exception exception) {
		this.exception = exception;
	}
}
