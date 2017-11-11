package hu.anitak.superchargehomework.businesslogic;

import hu.anitak.superchargehomework.businesslogic.exception.InvalidBalanceException;
import hu.anitak.superchargehomework.businesslogic.object.OperationRequest;
import hu.anitak.superchargehomework.businesslogic.object.OperationResponse;
import hu.anitak.superchargehomework.businesslogic.object.OperationResult;
import hu.anitak.superchargehomework.businesslogic.object.WithdrawalRequest;
import hu.anitak.superchargehomework.businesslogic.object.WithdrawalResponse;
import hu.anitak.superchargehomework.model.Account;

public class WithdrawalBusinessLogic extends DefaultBusinessLogic implements IBankingBusinessLogic {
	public OperationResponse doOperation(OperationRequest operationRequest) {
		WithdrawalRequest withdrawalRequest = (WithdrawalRequest) operationRequest;
		WithdrawalResponse response = new WithdrawalResponse();
		
		try {
			checkValidCustomer(withdrawalRequest);
			checkValidBalance(withdrawalRequest);
			
			Double amountToExtract = withdrawalRequest.getWithdrawalAmount();
			Account account = withdrawalRequest.getAccount(); 
			account.extractFromCurrentBalance(amountToExtract);
			
			response.setOperationResult(OperationResult.SUCCESS);
		} catch (Exception e) {
			response.setOperationResult(OperationResult.FAILED);
			response.setException(e);
		}
		
		return response;
	}
	
	private static void checkValidBalance(WithdrawalRequest withdrawalRequest) throws InvalidBalanceException {
		if(withdrawalRequest.getAccount().getCurrentBalance() - withdrawalRequest.getWithdrawalAmount() < 0) {
			throw new InvalidBalanceException();
		}
	}
}
