package controllers;

import java.util.Scanner;

import model.Account;
import model.Customer;
import model.Employees;
import model.Transactions;
import model.User;

public class BankingAppController {
	private static Scanner scan = new Scanner(System.in);
	
	public static void init() {
		User user = new User(1, "Trent", "1234", "Employee");
		logIn(user.getUsername(), user.getPassword());
		
		if (user.getStatus() == "Customer") {
			customerMenu(user);
		} else if (user.getStatus() == "Employee") {
			employeeMenu(user);
		} else {
			System.out.println("Invalid user status.");
		}
		
	}


	public static void logIn(String username, String password) {
		String usernameEntry = "";
		String passwordEntry = "";
		while ((!usernameEntry.equals(username)) && (!passwordEntry.equals(password))) {
			System.out.println("Enter Username: ");
			usernameEntry = scan.nextLine();
			System.out.println("Enter Password: ");
			passwordEntry = scan.nextLine();
			}
	}
	
	
	public static void customerMenu(User user) {
		System.out.println("Which of the following actions would you like to perform?"
				+ "Please select a number corresponding to the action you desire."
				+ "\n1. Create an account" + "\n2. Make a transaction"
				+ "\n3. Deposit/Withdraw money from an account");
		int choice = scan.nextInt();
		action(choice, user);
		}
	
	public static void employeeMenu(User user) {
		System.out.println("Which of the following actions would you like to perform?"
				+ "Please select a number corresponding to the action you desire."
				+ "\n1. Create an account" + "\n2. Make a transaction"
				+ "\n3. Deposit/Withdraw money from an account"
				+ "\n4. Approve/Reject an account request"
				+ "\n5. Change User password");
		int choice = scan.nextInt();
		action(choice, user);
	}
	
	//Find out the problem with static references to non-static methods
	public static void action(int choice, User user) {
		switch(choice) {
		case 1:
			Account newAccount = new Account();
			break;
		case 2:
			Transactions newTransactions = new Transactions();
			break;
		case 3:
			//Fix new creation of account
			Account account1 = new Account();
			System.out.println("Enter deposit/withdraw amount:");
			double deposit = scan.nextDouble();
			account1.setBalance(account1.getBalance() + deposit);
			break;
			
		//find out how to make these cases only available to employees
		case 4:
			//Fix new creation of account
			Account account2 = new Account();
			
			System.out.println("Please enter Approved or Rejected:");
			String status = scan.next();
			account2.setStatus(status);
			break;
		case 5:
			System.out.println("New Username: ");
			String username = scan.next();
			user.setUsername(username);
			System.out.println("New Password: ");
			String password = scan.next();
			user.setPassword(password);
			break;
		default:
			System.out.println("That is not an option.");
		}
	}
}
