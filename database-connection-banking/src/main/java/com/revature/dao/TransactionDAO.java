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
	
	public boolean createTransaction(Transactions transaction);
	public Transactions[] getAllTransactionsForAccount(Account account);
	public Transactions[] getAllTransactionsForUser(User user);
	public Transactions[] getAllTransactionsDate(String date);
	public Transactions[] getAllTransactionsLEQAmount(double amount);
	public Transactions[] getAllTransactionsGEQAmount(double amount);
}
