package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Account {
	protected int accountid;
	protected double balance = 0;
	protected boolean approved = false;
	
	
	//Constructors
	public Account() {}
	
	public Account(double balance) {
		this.balance = balance;
	}
	
	public Account(int accountid) {
		this.accountid = accountid;
	}
	
	public Account(int accountid, double balance) {
		this.accountid = accountid;
		this.balance = balance;
	}

	
	//Getters and Setters
	public int getAccountid() {
		return accountid;
	}

	public void setAccountid(int accountid) {
		this.accountid = accountid;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public boolean getApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	//Logging the creation of an account
	private static final Logger LOG = LogManager.getLogger(User.class);
	
	public void logAccount(int accountid, double balance) {
		LOG.info("Account "+accountid+" has been created with starting balance "+balance);
	}
	
	public void logApprove(int accountid) {
		LOG.info("Account "+accountid+" has been approved.");
	}
	
	public void logReject(int accountid) {
		LOG.info("Account "+accountid+" has been rejected.");
	}
}
