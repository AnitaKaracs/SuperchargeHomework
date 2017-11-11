package hu.anitak.superchargehomework.businesslogic;

import hu.anitak.superchargehomework.businesslogic.exception.InvalidCustomerException;
import hu.anitak.superchargehomework.businesslogic.object.DepositRequest;
import hu.anitak.superchargehomework.businesslogic.object.DepositResponse;
import hu.anitak.superchargehomework.businesslogic.object.OperationRequest;
import hu.anitak.superchargehomework.businesslogic.object.OperationResponse;
import hu.anitak.superchargehomework.businesslogic.object.OperationResult;
import hu.anitak.superchargehomework.model.Account;

public class DepositBusinessLogic extends DefaultBusinessLogic implements IBankingBusinessLogic {
	public OperationResponse doOperation(OperationRequest operationRequest) {
		DepositRequest depositRequest = (DepositRequest) operationRequest;
		DepositResponse response = new DepositResponse();
		
		try {
			checkValidCustomer(depositRequest);
			
			Double amountToAdd = depositRequest.getDepositAmount();
			Account account = depositRequest.getAccount(); 
			account.addToCurrentBalance(amountToAdd);
			
			response.setOperationResult(OperationResult.SUCCESS);
		} catch (InvalidCustomerException e) {
			response.setOperationResult(OperationResult.FAILED);
			response.setException(e);
		}
		
		return response;
	}
}
