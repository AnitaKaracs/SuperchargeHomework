package hu.anitak.superchargehomework.businesslogic;

import hu.anitak.superchargehomework.businesslogic.object.OperationRequest;
import hu.anitak.superchargehomework.businesslogic.object.OperationResponse;

public interface IBankingBusinessLogic {
	public OperationResponse doOperation(OperationRequest operationRequest);
}
