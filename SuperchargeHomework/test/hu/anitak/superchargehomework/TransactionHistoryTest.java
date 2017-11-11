package hu.anitak.superchargehomework;

import hu.anitak.superchargehomework.businesslogic.DepositBusinessLogic;
import hu.anitak.superchargehomework.businesslogic.TransactionHistoryBusinessLogic;
import hu.anitak.superchargehomework.businesslogic.TransferBusinessLogic;
import hu.anitak.superchargehomework.businesslogic.WithdrawalBusinessLogic;
import hu.anitak.superchargehomework.businesslogic.exception.InvalidCustomerException;
import hu.anitak.superchargehomework.businesslogic.object.DepositRequest;
import hu.anitak.superchargehomework.businesslogic.object.OperationResult;
import hu.anitak.superchargehomework.businesslogic.object.TransactionHistoryRequest;
import hu.anitak.superchargehomework.businesslogic.object.TransactionHistoryResponse;
import hu.anitak.superchargehomework.businesslogic.object.TransferRequest;
import hu.anitak.superchargehomework.businesslogic.object.WithdrawalRequest;
import hu.anitak.superchargehomework.model.Account;
import hu.anitak.superchargehomework.model.Customer;

import org.junit.Assert;
import org.junit.Test;

public class TransactionHistoryTest {
	private TransactionHistoryBusinessLogic transactionHistoryBusinessLogic = new TransactionHistoryBusinessLogic();
	private DepositBusinessLogic depositBusinessLogic = new DepositBusinessLogic();
	private WithdrawalBusinessLogic withdrawalBusinessLogic = new WithdrawalBusinessLogic();
	private TransferBusinessLogic transferBusinessLogic = new TransferBusinessLogic();
	
	@Test
	public void testSuccessfulTransactionHistoryWithoutFilter() {
		Double defaultBalance = 3000d;
		Customer testCustomer = new Customer();
		Account testAccount = new Account(testCustomer, defaultBalance);
		
		generateTestTransactionHistoryItems(testAccount);
		
		TransactionHistoryRequest transactionHistoryRequest = new TransactionHistoryRequest();
		transactionHistoryRequest.setCustomer(testCustomer);
		transactionHistoryRequest.setAccount(testAccount);
		
		int expectedTransactionHistoryItemNumber = 4;
		
		TransactionHistoryResponse response = (TransactionHistoryResponse) transactionHistoryBusinessLogic.doOperation(transactionHistoryRequest);
		Assert.assertEquals("Selecting transaction history failed.", OperationResult.SUCCESS, response.getOperationResult());
		Assert.assertNull("Exception during selecting transaction history: " + response.getException(), response.getException());
		Assert.assertNotNull("Transaction history list is empty.", response.getTransactionHistories());
		Assert.assertEquals("Transaction history item number is wrong.", expectedTransactionHistoryItemNumber, response.getTransactionHistories().size());
	}
	
	@Test
	public void testFailedTransactionHistoryWithoutFilterWrongCustomer() {
		Double defaultBalance = 3000d;
		Customer testCustomer = new Customer();
		Customer otherCustomer = new Customer();
		Account testAccount = new Account(testCustomer, defaultBalance);
		
		generateTestTransactionHistoryItems(testAccount);
		
		TransactionHistoryRequest transactionHistoryRequest = new TransactionHistoryRequest();
		transactionHistoryRequest.setCustomer(otherCustomer);
		transactionHistoryRequest.setAccount(testAccount);
		
		TransactionHistoryResponse response = (TransactionHistoryResponse) transactionHistoryBusinessLogic.doOperation(transactionHistoryRequest);
		Assert.assertNull("Transaction history list should be empty.", response.getTransactionHistories());
		Assert.assertEquals("Deposit was not failed, but customer should be wrong.", OperationResult.FAILED, response.getOperationResult());
		Assert.assertNotNull("Exception was not thrown, but customer should be wrong.", response.getException());
		Assert.assertEquals("Exception type is wrong.", InvalidCustomerException.class, response.getException().getClass());
	}
	
	private void generateTestTransactionHistoryItems(Account testAccount) {
		Double depositAmount = 1000d;
		DepositRequest depositRequest = new DepositRequest();
		depositRequest.setDepositAmount(depositAmount);
		depositRequest.setAccount(testAccount);
		depositRequest.setCustomer(testAccount.getCustomer());
		depositBusinessLogic.doOperation(depositRequest);
		
		depositAmount = 500d;
		depositRequest = new DepositRequest();
		depositRequest.setDepositAmount(depositAmount);
		depositRequest.setAccount(testAccount);
		depositRequest.setCustomer(testAccount.getCustomer());
		depositBusinessLogic.doOperation(depositRequest);
		
		Double withdrawalAmount = 2000d;
		WithdrawalRequest withdrawalRequest = new WithdrawalRequest();
		withdrawalRequest.setWithdrawalAmount(withdrawalAmount);
		withdrawalRequest.setAccount(testAccount);
		withdrawalRequest.setCustomer(testAccount.getCustomer());
		withdrawalBusinessLogic.doOperation(withdrawalRequest);
		
		
		Double defaultBalanceOfBeneficiaryAccount = 500000d;
		Customer beneficiaryCustomer = new Customer();
		Account beneficiaryAccount = new Account(beneficiaryCustomer, defaultBalanceOfBeneficiaryAccount);
		
		Double transferAmount = 1000d;
		TransferRequest transferRequest = new TransferRequest();
		transferRequest.setTransferAmount(transferAmount);
		transferRequest.setAccount(testAccount);
		transferRequest.setCustomer(testAccount.getCustomer());
		transferRequest.setAccountToTransfer(beneficiaryAccount);
		transferBusinessLogic.doOperation(transferRequest);
	}
}
