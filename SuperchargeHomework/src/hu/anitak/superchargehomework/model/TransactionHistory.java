package hu.anitak.superchargehomework.model;

import java.util.Date;

public class TransactionHistory {
	private Date transactionDate;
	private Double transactionAmount;
	private TransactionType transactionType;
	private Double actualBalance;
	
	public TransactionHistory(Double transactionAmount, TransactionType transactionType, Double actualBalance) {
		this.transactionDate = new Date();
		this.transactionAmount = transactionAmount;
		this.transactionType = transactionType;
		this.actualBalance = actualBalance;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(Double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public Double getActualBalance() {
		return actualBalance;
	}

	public void setActualBalance(Double actualBalance) {
		this.actualBalance = actualBalance;
	}
	
}
