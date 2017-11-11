package hu.anitak.superchargehomework;

import hu.anitak.superchargehomework.businesslogic.WithdrawalBusinessLogic;
import hu.anitak.superchargehomework.businesslogic.exception.InvalidBalanceException;
import hu.anitak.superchargehomework.businesslogic.exception.InvalidCustomerException;
import hu.anitak.superchargehomework.businesslogic.object.WithdrawalRequest;
import hu.anitak.superchargehomework.businesslogic.object.WithdrawalResponse;
import hu.anitak.superchargehomework.businesslogic.object.OperationResult;
import hu.anitak.superchargehomework.model.Account;
import hu.anitak.superchargehomework.model.Customer;

import org.junit.Assert;
import org.junit.Test;

public class WithdrawalTest {
	private WithdrawalBusinessLogic businessLogic = new WithdrawalBusinessLogic();
	
	@Test
	public void testSuccessfulWithdrawal() {
		Double defaultBalance = 300000d;
		Customer testCustomer = new Customer();
		Account testAccount = new Account(testCustomer, defaultBalance);
		
		Double withdrawalAmount = 100000d;
		WithdrawalRequest withdrawalRequest = new WithdrawalRequest();
		withdrawalRequest.setWithdrawalAmount(withdrawalAmount);
		withdrawalRequest.setAccount(testAccount);
		withdrawalRequest.setCustomer(testCustomer);
		
		Double expectedBalanceAfterWithdrawal = defaultBalance - withdrawalAmount;
		
		WithdrawalResponse response = (WithdrawalResponse) businessLogic.doOperation(withdrawalRequest);
		Assert.assertEquals("Withdrawal was unsuccessful.", expectedBalanceAfterWithdrawal, testAccount.getCurrentBalance());
		Assert.assertEquals("Withdrawal failed.", OperationResult.SUCCESS, response.getOperationResult());
		Assert.assertNull("Exception during withdrawal: " + response.getException(), response.getException());
	}
	
	@Test
	public void testFailedWithdrawalWrongCustomer() {
		Double defaultBalance = 300000d;
		Customer testCustomer = new Customer();
		Customer otherCustomer = new Customer();
		Account testAccount = new Account(testCustomer, defaultBalance);
		
		Double withdrawalAmount = 100000d;
		WithdrawalRequest withdrawalRequest = new WithdrawalRequest();
		withdrawalRequest.setWithdrawalAmount(withdrawalAmount);
		withdrawalRequest.setAccount(testAccount);
		withdrawalRequest.setCustomer(otherCustomer);
		
		WithdrawalResponse response = (WithdrawalResponse) businessLogic.doOperation(withdrawalRequest);
		Assert.assertNotEquals("Customer is not wrong but should be.", otherCustomer, testAccount.getCustomer());
		Assert.assertEquals("Account balance should be the original because of wrong customer.", defaultBalance, testAccount.getCurrentBalance());
		Assert.assertEquals("Withdrawal was not failed, but customer should be wrong.", OperationResult.FAILED, response.getOperationResult());
		Assert.assertNotNull("Exception was not thrown, but customer should be wrong.", response.getException());
		Assert.assertEquals("Exception type is wrong.", InvalidCustomerException.class, response.getException().getClass());
	}
	
	@Test
	public void testFailedWithdrawalWrongBalance() {
		Double defaultBalance = 100000d;
		Customer testCustomer = new Customer();
		Account testAccount = new Account(testCustomer, defaultBalance);
		
		Double withdrawalAmount = 300000d;
		WithdrawalRequest withdrawalRequest = new WithdrawalRequest();
		withdrawalRequest.setWithdrawalAmount(withdrawalAmount);
		withdrawalRequest.setAccount(testAccount);
		withdrawalRequest.setCustomer(testCustomer);
		
		WithdrawalResponse response = (WithdrawalResponse) businessLogic.doOperation(withdrawalRequest);
		Assert.assertEquals("Account balance should be the original because of wrong balance.", defaultBalance, testAccount.getCurrentBalance());
		Assert.assertEquals("Withdrawal was not failed, but balance should be wrong.", OperationResult.FAILED, response.getOperationResult());
		Assert.assertNotNull("Exception was not thrown, but balance should be wrong.", response.getException());
		Assert.assertEquals("Exception type is wrong.", InvalidBalanceException.class, response.getException().getClass());
	}
	
}
