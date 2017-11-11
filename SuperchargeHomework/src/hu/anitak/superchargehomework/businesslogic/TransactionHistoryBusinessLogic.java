package hu.anitak.superchargehomework.businesslogic;

import hu.anitak.superchargehomework.businesslogic.exception.InvalidCustomerException;
import hu.anitak.superchargehomework.businesslogic.object.OperationRequest;
import hu.anitak.superchargehomework.businesslogic.object.OperationResponse;
import hu.anitak.superchargehomework.businesslogic.object.OperationResult;
import hu.anitak.superchargehomework.businesslogic.object.TransactionHistoryRequest;
import hu.anitak.superchargehomework.businesslogic.object.TransactionHistoryResponse;
import hu.anitak.superchargehomework.model.Account;
import hu.anitak.superchargehomework.model.TransactionHistory;
import hu.anitak.superchargehomework.model.TransactionType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionHistoryBusinessLogic extends DefaultBusinessLogic implements IBankingBusinessLogic {
	public OperationResponse doOperation(OperationRequest operationRequest) {
		TransactionHistoryRequest transactionHistoryRequest = (TransactionHistoryRequest) operationRequest;
		TransactionHistoryResponse response = new TransactionHistoryResponse();
		
		try {
			checkValidCustomer(transactionHistoryRequest);
			
			List<TransactionHistory> filteredTransactionHistoriesOfAccount = getFilterTransactionHistories(transactionHistoryRequest);
			
			response.setTransactionHistories(filteredTransactionHistoriesOfAccount);
			response.setOperationResult(OperationResult.SUCCESS);
		} catch (InvalidCustomerException e) {
			response.setOperationResult(OperationResult.FAILED);
			response.setException(e);
		}
		
		return response;
	}
	
	public static void printTransactionHistory(List<TransactionHistory> transactionHistories) {
		//TODO implement
		throw new UnsupportedOperationException("Function not yet implemented.");
	}
	
	private static List<TransactionHistory> getFilterTransactionHistories(TransactionHistoryRequest transactionHistoryRequest) {
		Account account = transactionHistoryRequest.getAccount();
		List<TransactionHistory> transactionHistoriesOfAccount = account.getTransactionHistories();
		
		if(transactionHistoryRequest.isFilterNotApplied()) {
			return transactionHistoriesOfAccount;
		}
		
		List<TransactionHistory> filteredTransactionHistories = new ArrayList<TransactionHistory>(); 
		
		for(TransactionHistory transactionHistoryItem : transactionHistoriesOfAccount) {
			if(filterByTransactionDate(transactionHistoryRequest.getTransactionDateFilter(), transactionHistoryItem) &&
			   filterByTransactionType(transactionHistoryRequest.getTransactionTypeFilter(), transactionHistoryItem)) {
				filteredTransactionHistories.add(transactionHistoryItem);
			}
		}
		
		return filteredTransactionHistories;
	}
	
	private static boolean filterByTransactionDate(Date transactionDateFilter, TransactionHistory transactionHistoryItem) {
		if(transactionDateFilter == null) {
			return true;
		}
		
		SimpleDateFormat dateFormatPattern = new SimpleDateFormat("yyyyMMdd");
		String transactionHistoryTransactionDate = dateFormatPattern.format(transactionHistoryItem.getTransactionDate());
		String filterTransactionDate = dateFormatPattern.format(transactionDateFilter);
		return transactionHistoryTransactionDate.equals(filterTransactionDate);
	}
	
	private static boolean filterByTransactionType(TransactionType transactionTypeFilter, TransactionHistory transactionHistoryItem) {
		if(transactionTypeFilter == null) {
			return true;
		}
		
		return transactionHistoryItem.getTransactionType().equals(transactionTypeFilter);
	}
}
