package hu.anitak.superchargehomework.businesslogic.object;

import hu.anitak.superchargehomework.model.Account;
import hu.anitak.superchargehomework.model.Customer;

public class OperationRequest {
	private Customer customer;
	private Account account;
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	
	public void setAccount(Account account) {
		this.account = account;
	}
	
	public Account getAccount() {
		return account;
	}
}
