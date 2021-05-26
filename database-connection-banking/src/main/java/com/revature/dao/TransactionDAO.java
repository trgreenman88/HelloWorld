package com.revature.dao;

import model.Account;
import model.Transactions;
import model.User;

//Implement this
public interface TransactionDAO {
	/*
	 * createTransaction
	 * getAllTransactionsForAccount
	 * getAllTransactionsForUser
	 * getAllTransactionsDate
	 * getAllTransactionsLEQAmount
	 * getAllTransactionsGEQAmount*/
	
	//public boolean createTransaction(Transactions transaction);
	//public Transactions[] getAllTransactionsForAccount(Account account);
	//public Transactions[] getAllTransactionsForUser(User user);
	//public Transactions[] getAllTransactionsDate(String date);
	//public Transactions[] getAllTransactionsLEQAmount(double amount);
	//public Transactions[] getAllTransactionsGEQAmount(double amount);
	
	public double withdrawDB(Account account, int userid, Transactions transaction, double oldBalance);
	public double depositDB(Account account, int userid, Transactions transaction, double oldBalance);
	public double depositDB(Account account, Transactions transaction, double oldBalance);
	public double[] transferDB(Account account1, Account account2, int userid, Transactions transaction, double balance1, double balance2);
	
	//public boolean addDepositDB();
	//public boolean addWithdrawDB();
	//public boolean addTransferDB();
}
