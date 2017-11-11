package hu.anitak.superchargehomework;

import hu.anitak.superchargehomework.businesslogic.DepositBusinessLogic;
import hu.anitak.superchargehomework.businesslogic.exception.InvalidCustomerException;
import hu.anitak.superchargehomework.businesslogic.object.DepositRequest;
import hu.anitak.superchargehomework.businesslogic.object.DepositResponse;
import hu.anitak.superchargehomework.businesslogic.object.OperationResult;
import hu.anitak.superchargehomework.model.Account;
import hu.anitak.superchargehomework.model.Customer;

import org.junit.Assert;
import org.junit.Test;

public class DepositTest {
	private DepositBusinessLogic businessLogic = new DepositBusinessLogic();
	
	@Test
	public void testSuccessfulDeposit() {
		Double defaultBalance = 300000d;
		Customer testCustomer = new Customer();
		Account testAccount = new Account(testCustomer, defaultBalance);
		
		Double depositAmount = 100000d;
		DepositRequest depositRequest = new DepositRequest();
		depositRequest.setDepositAmount(depositAmount);
		depositRequest.setAccount(testAccount);
		depositRequest.setCustomer(testCustomer);
		
		Double expectedBalanceAfterDeposit = defaultBalance + depositAmount;
		
		DepositResponse response = (DepositResponse) businessLogic.doOperation(depositRequest);
		Assert.assertEquals("Deposit was unsuccessful.", expectedBalanceAfterDeposit, testAccount.getCurrentBalance());
		Assert.assertEquals("Deposit failed.", OperationResult.SUCCESS, response.getOperationResult());
		Assert.assertNull("Exception during deposit: " + response.getException(), response.getException());
	}
	
	@Test
	public void testFailedDepositWrongCustomer() {
		Double defaultBalance = 300000d;
		Customer testCustomer = new Customer();
		Customer otherCustomer = new Customer();
		Account testAccount = new Account(testCustomer, defaultBalance);
		
		Double testDepositAmount = 100000d;
		DepositRequest depositRequest = new DepositRequest();
		depositRequest.setDepositAmount(testDepositAmount);
		depositRequest.setAccount(testAccount);
		depositRequest.setCustomer(otherCustomer);
		
		DepositResponse response = (DepositResponse) businessLogic.doOperation(depositRequest);
		Assert.assertNotEquals("Customer is not wrong but should be.", otherCustomer, testAccount.getCustomer());
		Assert.assertEquals("Account balance should be the original because of wrong customer.", defaultBalance, testAccount.getCurrentBalance());
		Assert.assertEquals("Deposit was not failed, but customer should be wrong.", OperationResult.FAILED, response.getOperationResult());
		Assert.assertNotNull("Exception was not thrown, but customer should be wrong.", response.getException());
		Assert.assertEquals("Exception type is wrong.", InvalidCustomerException.class, response.getException().getClass());
	}
	
}
