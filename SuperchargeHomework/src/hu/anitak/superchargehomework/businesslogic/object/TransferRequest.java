package hu.anitak.superchargehomework.businesslogic.object;

import hu.anitak.superchargehomework.model.Account;

public class TransferRequest extends OperationRequest {
	private Double transferAmount;
	private Account accountToTransfer;
	
	public Double getTransferAmount() {
		return transferAmount;
	}
	public void setTransferAmount(Double transferAmount) {
		this.transferAmount = transferAmount;
	}
	public Account getAccountToTransfer() {
		return accountToTransfer;
	}
	public void setAccountToTransfer(Account accountToTransfer) {
		this.accountToTransfer = accountToTransfer;
	}
	
}
