package hu.anitak.superchargehomework.businesslogic.object;

import hu.anitak.superchargehomework.model.TransactionHistory;

import java.util.List;

public class TransactionHistoryResponse extends OperationResponse {
	private List<TransactionHistory> transactionHistories;
	
	public List<TransactionHistory> getTransactionHistories() {
		return transactionHistories;
	}
	
	public void setTransactionHistories(List<TransactionHistory> transactionHistories) {
		this.transactionHistories = transactionHistories;
	}
}
