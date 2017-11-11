package hu.anitak.superchargehomework.businesslogic.object;

public class DepositRequest extends OperationRequest {
	private Double depositAmount;
	
	public void setDepositAmount(Double depositAmount) {
		this.depositAmount = depositAmount;
	}
	
	public Double getDepositAmount() {
		return depositAmount;
	}
}
