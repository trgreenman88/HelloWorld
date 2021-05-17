package controllers;

import java.util.Scanner;

import model.Account;
import model.Customer;
import model.Employees;
import model.Transactions;
import model.User;

import com.revature.util.*;

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
				+ "\n3. Deposit/Withdraw money from an account"
				+ "\n4. View account balance");
		int choice = scan.nextInt();
		scan.nextLine();
		action(choice, user);
		}
	
	public static void employeeMenu(User user) {
		System.out.println("Which of the following actions would you like to perform?"
				+ "Please select a number corresponding to the action you desire."
				+ "\n1. Create an account" + "\n2. Make a transaction"
				+ "\n3. Deposit/Withdraw money from an account"
				+ "\n4. View account balance"
				+ "\n5. Approve/Reject an account request"
				+ "\n6. Change User password"
				+ "\n7. Create new User");
		int choice = scan.nextInt();
		scan.nextLine();
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
		case 4:
			//Fix new creation of account
			Account account2 = new Account();
			System.out.println(account2.getBalance());
			break;
			
		//find out how to make these cases only available to employees
		case 5:
			//Fix new creation of account
			Account account3 = new Account();
			System.out.println("Please enter Approved or Rejected:");
			String status = scan.nextLine();
			account3.setStatus(status);
			break;
		case 6:
			System.out.println("New Username: ");
			String username = scan.nextLine();
			user.setUsername(username);
			System.out.println("New Password: ");
			String password = scan.nextLine();
			user.setPassword(password);
			break;
		case 7:
			System.out.println("Enter User's Username: ");
			String userEntry = scan.nextLine();
			System.out.println("Enter User's Password: ");
			String passEntry = scan.nextLine();
			System.out.println("Enter User's Status: ");
			//Make sure that statusEntry is either "Employee" or "Customer"
			String statusEntry = scan.nextLine();
			
			User newUser = new User(1, userEntry, passEntry, statusEntry);
			newUser.addUserDB();
			newUser.logUser();
			break;
		default:
			System.out.println("That is not an option.");
		}
	}
}
