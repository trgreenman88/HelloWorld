package com.revature.dao;

import model.Account;

public interface AccountDAO {
	/*
	 * createAccount
	 * getAccountBalance
	 * getAccountUser
	 * deleteAccount*/
	
	//public boolean createAccount(Account account);
	//public int getAccountBalance(Account account);
	//public String getAccountUser(Account account);
	//public boolean deleteAccount(Account account);
	
	public boolean addPendingAccountDB(Account account, int userid);
	public double getBalanceDB(Account account, int userid);
	public double getBalanceDB(Account account);
	public boolean approveAccountDB(Account account);
	public boolean rejectAccountDB(Account account);
	public void getCustomerAccountsDB(int userid);
	public int getNewestAccountID();
	public boolean checkApproved(Account account);
	public boolean checkValidID(Account account);
	public boolean checkOwnership(Account account, int userid);
}
