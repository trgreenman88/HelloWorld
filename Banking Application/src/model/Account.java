package model;

public class Account {
	protected int accountid;
	protected double balance = 0;
	protected String status = "Pending";
	
	
	//Constructors
	public Account() {}
	
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
