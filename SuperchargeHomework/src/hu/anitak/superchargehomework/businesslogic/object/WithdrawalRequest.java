package hu.anitak.superchargehomework.businesslogic.object;

public class WithdrawalRequest extends OperationRequest {
	private Double withdrawalAmount;
	
	public Double getWithdrawalAmount() {
		return withdrawalAmount;
	}
	
	public void setWithdrawalAmount(Double withdrawalAmount) {
		this.withdrawalAmount = withdrawalAmount;
	}
}
