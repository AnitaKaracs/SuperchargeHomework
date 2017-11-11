package hu.anitak.superchargehomework.businesslogic.object;

import hu.anitak.superchargehomework.model.TransactionType;

import java.util.Date;

public class TransactionHistoryRequest extends OperationRequest {
	private Date transactionDateFilter;
	private TransactionType transactionTypeFilter;
	
	public Date getTransactionDateFilter() {
		return transactionDateFilter;
	}

	public void setTransactionDateFilter(Date transactionDateFilter) {
		this.transactionDateFilter = transactionDateFilter;
	}

	public TransactionType getTransactionTypeFilter() {
		return transactionTypeFilter;
	}

	public void setTransactionTypeFilter(TransactionType transactionTypeFilter) {
		this.transactionTypeFilter = transactionTypeFilter;
	}

	public boolean isFilterNotApplied() {
		return transactionDateFilter == null && transactionTypeFilter == null;
	}
}
