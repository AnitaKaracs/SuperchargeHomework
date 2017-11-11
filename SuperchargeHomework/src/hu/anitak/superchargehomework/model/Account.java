package hu.anitak.superchargehomework.model;

import java.util.ArrayList;
import java.util.List;

public class Account {
	private Customer customer;
	private Double currentBalance;
	private List<TransactionHistory> transactionHistories;
	
	public Account(Customer customer, Double currentBalance) {
		this.customer = customer;
		this.currentBalance = currentBalance;
		this.transactionHistories = new ArrayList<TransactionHistory>();
	}

	public Customer getCustomer() {
		return customer;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public Double getCurrentBalance() {
		return currentBalance;
	}
	
	public void addToCurrentBalance(Double amountToAdd) {
		this.currentBalance += amountToAdd;
		addTransactionHistoryItem(amountToAdd, TransactionType.DEPOSIT);
	}
	
	public void extractFromCurrentBalance(Double amountToExtract) {
		this.currentBalance -= amountToExtract;
		addTransactionHistoryItem(amountToExtract, TransactionType.WITHDRAWAL);
	}
	
	public List<TransactionHistory> getTransactionHistories() {
		return transactionHistories;
	}
	
	private void addTransactionHistoryItem(Double transactionAmount, TransactionType transactionType) {
		TransactionHistory transactionHistoryItem = new TransactionHistory(transactionAmount, transactionType, this.currentBalance);
		this.transactionHistories.add(transactionHistoryItem);
	}
}
