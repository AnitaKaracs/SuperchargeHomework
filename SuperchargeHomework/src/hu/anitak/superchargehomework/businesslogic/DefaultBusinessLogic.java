package hu.anitak.superchargehomework.businesslogic;

import hu.anitak.superchargehomework.businesslogic.exception.InvalidCustomerException;
import hu.anitak.superchargehomework.businesslogic.object.OperationRequest;

public class DefaultBusinessLogic {
	public static void checkValidCustomer(OperationRequest operationRequest) throws InvalidCustomerException {
		if(operationRequest.getCustomer() == null || !operationRequest.getCustomer().equals(operationRequest.getAccount().getCustomer())) {
			throw new InvalidCustomerException();
		}
	}
	
}
