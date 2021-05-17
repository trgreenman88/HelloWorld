package com.revature.dao;

import model.Account;
//Implement this
public interface AccountDAO {
	/*
	 * createAccount
	 * getAccountBalance
	 * getAccountUser
	 * deleteAccount*/
	
	public boolean createAccount(Account account);
	public int getAccountBalance(Account account);
	public String getAccountUser(Account account);
	public boolean deleteAccount(Account account);
}
