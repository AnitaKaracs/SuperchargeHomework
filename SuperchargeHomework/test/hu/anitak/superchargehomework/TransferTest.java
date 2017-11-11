package hu.anitak.superchargehomework;

import hu.anitak.superchargehomework.businesslogic.TransferBusinessLogic;
import hu.anitak.superchargehomework.businesslogic.exception.InvalidBalanceException;
import hu.anitak.superchargehomework.businesslogic.exception.InvalidCustomerException;
import hu.anitak.superchargehomework.businesslogic.exception.InvalidTransferBeneficiaryException;
import hu.anitak.superchargehomework.businesslogic.object.OperationResult;
import hu.anitak.superchargehomework.businesslogic.object.TransferRequest;
import hu.anitak.superchargehomework.businesslogic.object.TransferResponse;
import hu.anitak.superchargehomework.model.Account;
import hu.anitak.superchargehomework.model.Customer;

import org.junit.Assert;
import org.junit.Test;

public class TransferTest {
	private TransferBusinessLogic businessLogic = new TransferBusinessLogic();
	
	@Test
	public void testSuccessfulTransfer() {
		Double defaultBalanceOfTestAccount = 300000d;
		Customer testCustomer = new Customer();
		Account testAccount = new Account(testCustomer, defaultBalanceOfTestAccount);
		
		Double defaultBalanceOfBeneficiaryAccount = 500000d;
		Customer beneficiaryCustomer = new Customer();
		Account beneficiaryAccount = new Account(beneficiaryCustomer, defaultBalanceOfBeneficiaryAccount);
		
		Double transferAmount = 100000d;
		TransferRequest transferRequest = new TransferRequest();
		transferRequest.setTransferAmount(transferAmount);
		transferRequest.setAccount(testAccount);
		transferRequest.setCustomer(testCustomer);
		transferRequest.setAccountToTransfer(beneficiaryAccount);
		
		Double expectedBalanceOfTestAccountAfterTransfer = defaultBalanceOfTestAccount - transferAmount;
		Double expectedBalanceOfBeneficiaryAccountAfterTransfer = defaultBalanceOfBeneficiaryAccount + transferAmount;
		
		TransferResponse response = (TransferResponse) businessLogic.doOperation(transferRequest);
		Assert.assertEquals("Transfer was unsuccessful for test account.", expectedBalanceOfTestAccountAfterTransfer, testAccount.getCurrentBalance());
		Assert.assertEquals("Transfer was unsuccessful for beneficiary account.", expectedBalanceOfBeneficiaryAccountAfterTransfer, beneficiaryAccount.getCurrentBalance());
		Assert.assertEquals("Transfer failed.", OperationResult.SUCCESS, response.getOperationResult());
		Assert.assertNull("Exception during transfer: " + response.getException(), response.getException());
	}
	
	@Test
	public void testFailedTransferWrongCustomer() {
		Double defaultBalanceOfTestAccount = 300000d;
		Customer testCustomer = new Customer();
		Account testAccount = new Account(testCustomer, defaultBalanceOfTestAccount);
		
		Double defaultBalanceOfBeneficiaryAccount = 500000d;
		Customer beneficiaryCustomer = new Customer();
		Account beneficiaryAccount = new Account(beneficiaryCustomer, defaultBalanceOfBeneficiaryAccount);
		
		Double transferAmount = 100000d;
		Customer otherCustomer = new Customer();
		TransferRequest transferRequest = new TransferRequest();
		transferRequest.setTransferAmount(transferAmount);
		transferRequest.setAccount(testAccount);
		transferRequest.setCustomer(otherCustomer);
		transferRequest.setAccountToTransfer(beneficiaryAccount);
		
		TransferResponse response = (TransferResponse) businessLogic.doOperation(transferRequest);
		Assert.assertNotEquals("Customer is not wrong but should be.", otherCustomer, testAccount.getCustomer());
		Assert.assertEquals("Account balance of test account should be the original because of wrong customer.", 
				defaultBalanceOfTestAccount, testAccount.getCurrentBalance());
		Assert.assertEquals("Account balance of beneficiary account should be the original because of wrong customer.", 
				defaultBalanceOfBeneficiaryAccount, beneficiaryAccount.getCurrentBalance());
		Assert.assertEquals("Transfer was not failed, but customer should be wrong.", OperationResult.FAILED, response.getOperationResult());
		Assert.assertNotNull("Exception was not thrown, but customer should be wrong.", response.getException());
		Assert.assertEquals("Exception type is wrong.", InvalidCustomerException.class, response.getException().getClass());
	}
	
	@Test
	public void testFailedTransferWrongBeneficiaryAccount() {
		Double defaultBalanceOfTestAccount = 300000d;
		Customer testCustomer = new Customer();
		Account testAccount = new Account(testCustomer, defaultBalanceOfTestAccount);
		
		Double transferAmount = 100000d;
		TransferRequest transferRequest = new TransferRequest();
		transferRequest.setTransferAmount(transferAmount);
		transferRequest.setAccount(testAccount);
		transferRequest.setCustomer(testCustomer);
		transferRequest.setAccountToTransfer(null);
		
		TransferResponse response = (TransferResponse) businessLogic.doOperation(transferRequest);
		Assert.assertEquals("Account balance of test account should be the original because of wrong beneficiary account.", 
				defaultBalanceOfTestAccount, testAccount.getCurrentBalance());
		Assert.assertEquals("Transfer was not failed, but balance should be wrong.", OperationResult.FAILED, response.getOperationResult());
		Assert.assertNotNull("Exception was not thrown, but balance should be wrong.", response.getException());
		Assert.assertEquals("Exception type is wrong.", InvalidTransferBeneficiaryException.class, response.getException().getClass());
	}
	
	@Test
	public void testFailedTransferWrongBalance() {
		Double defaultBalanceOfTestAccount = 100000d;
		Customer testCustomer = new Customer();
		Account testAccount = new Account(testCustomer, defaultBalanceOfTestAccount);
		
		Double defaultBalanceOfBeneficiaryAccount = 500000d;
		Customer beneficiaryCustomer = new Customer();
		Account beneficiaryAccount = new Account(beneficiaryCustomer, defaultBalanceOfBeneficiaryAccount);
		
		Double transferAmount = 300000d;
		TransferRequest transferRequest = new TransferRequest();
		transferRequest.setTransferAmount(transferAmount);
		transferRequest.setAccount(testAccount);
		transferRequest.setCustomer(testCustomer);
		transferRequest.setAccountToTransfer(beneficiaryAccount);
		
		TransferResponse response = (TransferResponse) businessLogic.doOperation(transferRequest);
		Assert.assertEquals("Account balance of test account should be the original because of wrong balance.", 
				defaultBalanceOfTestAccount, testAccount.getCurrentBalance());
		Assert.assertEquals("Account balance of beneficiary account should be the original because of wrong balance.", 
				defaultBalanceOfBeneficiaryAccount, beneficiaryAccount.getCurrentBalance());
		Assert.assertEquals("Transfer was not failed, but balance should be wrong.", OperationResult.FAILED, response.getOperationResult());
		Assert.assertNotNull("Exception was not thrown, but balance should be wrong.", response.getException());
		Assert.assertEquals("Exception type is wrong.", InvalidBalanceException.class, response.getException().getClass());
	}
	
}
