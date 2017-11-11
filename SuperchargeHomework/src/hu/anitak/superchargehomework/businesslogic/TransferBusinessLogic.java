package hu.anitak.superchargehomework.businesslogic;

import hu.anitak.superchargehomework.businesslogic.exception.InvalidBalanceException;
import hu.anitak.superchargehomework.businesslogic.exception.InvalidTransferBeneficiaryException;
import hu.anitak.superchargehomework.businesslogic.object.OperationRequest;
import hu.anitak.superchargehomework.businesslogic.object.OperationResponse;
import hu.anitak.superchargehomework.businesslogic.object.OperationResult;
import hu.anitak.superchargehomework.businesslogic.object.TransferRequest;
import hu.anitak.superchargehomework.businesslogic.object.TransferResponse;
import hu.anitak.superchargehomework.model.Account;

public class TransferBusinessLogic extends DefaultBusinessLogic implements IBankingBusinessLogic {
	public OperationResponse doOperation(OperationRequest operationRequest) {
		TransferRequest transferRequest = (TransferRequest) operationRequest;
		TransferResponse response = new TransferResponse();
		
		try {
			checkValidCustomer(transferRequest);
			checkValidBalance(transferRequest);
			checkValidTransferAccount(transferRequest);
			
			Double transferAmount = transferRequest.getTransferAmount();
			Account account = transferRequest.getAccount(); 
			account.extractFromCurrentBalance(transferAmount);
			
			Account transferBeneficiary = transferRequest.getAccountToTransfer();
			transferBeneficiary.addToCurrentBalance(transferAmount);
			
			response.setOperationResult(OperationResult.SUCCESS);
		} catch (Exception e) {
			response.setOperationResult(OperationResult.FAILED);
			response.setException(e);
		}
		
		return response;
	}
	
	private static void checkValidBalance(TransferRequest transferRequest) throws InvalidBalanceException {
		if(transferRequest.getAccount().getCurrentBalance() - transferRequest.getTransferAmount() < 0) {
			throw new InvalidBalanceException();
		}
	}
	
	private static void checkValidTransferAccount(TransferRequest transferRequest) throws InvalidTransferBeneficiaryException {
		if(transferRequest.getAccountToTransfer() == null) {// + other format checks, currently null check
			throw new InvalidTransferBeneficiaryException();
		}
	}
}
