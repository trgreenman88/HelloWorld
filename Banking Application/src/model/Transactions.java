package model;

public class Transactions {
	protected int transactionid;
	protected String date;
	protected String description;
	protected double amount;
	
	
	//Constructors
	public Transactions() {}
	
	public Transactions(int transactionid, String date, String description, double amount) {
		this.transactionid = transactionid;
		this.date = date;
		this.description = description;
		this.amount = amount;
	}

	
	//Getters and Setters
	public int getTransactionid() {
		return transactionid;
	}

	public void setTransactionid(int transactionid) {
		this.transactionid = transactionid;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
}
