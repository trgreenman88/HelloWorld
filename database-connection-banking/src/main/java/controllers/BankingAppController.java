package controllers;

import java.util.Scanner;
import com.revature.util.*;

import model.Account;
import model.Customer;
import model.Employees;
import model.Transactions;
import model.User;

import com.revature.util.*;

public class BankingAppController {
	private static Scanner scan = new Scanner(System.in);
	
	private static AuthenticationService login = new AuthenticationService();
	
	public static void init() {
		String usernameEntry = "";
		String passwordEntry = "";
		while(true) {
			System.out.println("Enter Username: ");
			usernameEntry = scan.nextLine();
			System.out.println("Enter Password: ");
			passwordEntry = scan.nextLine();
		
			if (login.loginUserDB(usernameEntry, passwordEntry) == true) {
				System.out.println("login successful");
				break;
			} else {
				System.out.println("Login failed, please try again.");
			}
		}
		
		String[] userInfo = login.getUserInfo(usernameEntry, passwordEntry);
		User user = new User(Integer.parseInt(userInfo[0]), userInfo[1], userInfo[2], userInfo[3]);
		
		if (user.getStatus().equals("Customer")) {
			customerMenu(user);
		} else if (user.getStatus().equals("Employee")) {
			employeeMenu(user);
		} else {
			System.out.println("Invalid user status.");
		}
		
	}
	
	
	public static void customerMenu(User user) {
		System.out.println("Which of the following actions would you like to perform? "
				+ "Please select a number corresponding to the action you desire."
				+ "\n1. Create an account" + "\n2. Make a transfer"
				+ "\n3. Deposit/Withdraw money from an account"
				+ "\n4. View account balance");
		int choice = scan.nextInt();
		scan.nextLine();
		action(choice, user);
		}
	
	public static void employeeMenu(User user) {
		System.out.println("Which of the following actions would you like to perform? "
				+ "Please select a number corresponding to the action you desire."
				+ "\n1. Create an account" + "\n2. Make a transfer"
				+ "\n3. Deposit/Withdraw money from an account"
				+ "\n4. View account balance"
				+ "\n5. Approve/Reject an account request"
				+ "\n6. View customer accounts"
				+ "\n7. Create new User");
		int choice = scan.nextInt();
		scan.nextLine();
		action(choice, user);
	}
	
	
	public static void action(int choice, User user) {
		if((choice >= 5 && choice <= 7) && !user.getStatus().equals("Employee")) {
			System.out.println("You must be an employee to access this option.");
			return;
		}
		
		switch(choice) {
		//Create an account
		case 1:
			System.out.println("Please enter the starting balance of your new account: ");
			double balance = scan.nextDouble();
			scan.nextLine();
			Account newAccount = new Account(balance);
			newAccount.addPendingAccountDB(user.getUserid());
			newAccount.logAccount(newAccount.getNewestAccountID(), balance);
			break;
			
		//Transfer between accounts	
		case 2:
			System.out.println("Enter Account ID to transfer from: ");
			int accountid01 = scan.nextInt();
			scan.nextLine();
			Account account01 = new Account(accountid01);
			if(!account01.checkApproved(accountid01)) {
				System.out.println("This account has not yet been approved.");
				break;
			}
			System.out.println("Enter Account ID to transfer to: ");
			int accountid02 = scan.nextInt();
			scan.nextLine();
			Account account02 = new Account(accountid02);
			if(!account01.checkApproved(accountid02)) {
				System.out.println("This account has not yet been approved.");
				break;
			}
			double amount01 = -1;
			while(true) {
				System.out.println("Enter transfer amount:");
				amount01 = scan.nextDouble();
				scan.nextLine();
				if(amount01 >= 0) {
					break;
				}else {
					System.out.println("That is not a valid amount, try again: ");
				}
			}
			Transactions transaction1 = new Transactions();
			double balance01 = account01.getBalanceDB(accountid01, user.getUserid());
			double balance02 = account02.getBalanceDB(accountid02);
			if(balance01 == -1) {
				break;
			}
			double newBalance01 = transaction1.withdrawDB(accountid01, user.getUserid(), amount01, balance01);
			double newBalance02 = transaction1.depositDB(accountid02, amount01, balance02);
			if(newBalance01 != -1) {
				System.out.println("You have transfered $"+amount01+" from account "+accountid01+" to "
						+ "account "+accountid02+"."
						+ "\n Your balance in account "+accountid01+" is now $"+newBalance01
						+ "\n Your balance in account "+accountid02+" is now $"+newBalance02);
				transaction1.logTransfer(amount01, accountid01, accountid02);
			} else {
				System.out.println("Transfer failed.");
			}
			break;
			
		//Make a withdrawal/deposit	
		case 3:
			Transactions transaction = new Transactions();
			
			System.out.println("Which of the following actions would you like to perform? "
					+ "Please select a number corresponding to the action you desire."
					+ "\n1. Deposit money into account."
					+ "\n2. Withdraw money from account.");
			
			int choice1 = 0;
			while (choice1 != 1 && choice1 != 2) {
				choice1 = scan.nextInt();
				scan.nextLine();
				switch(choice1) {
				case 1:
					System.out.println("Enter Account ID: ");
					int accountid = scan.nextInt();
					scan.nextLine();
					Account account0 = new Account(accountid);
					if(!account0.checkApproved(accountid)) {
						System.out.println("This account has not yet been approved.");
						break;
					}
					double amount = -1;
					while (true) {
						System.out.println("Enter deposit amount: ");
						amount = scan.nextDouble();
						scan.nextLine();
						if(amount >= 0) {
							break;
						}
						System.out.println("That is not a valid amount, please try again.");
					}
					
					double oldBalance = account0.getBalanceDB(accountid, user.getUserid());
					if(oldBalance == -1) {
						break;
					}
					double newBalance = transaction.depositDB(accountid, user.getUserid(), amount, oldBalance);
					if(newBalance != -1) {
						System.out.println("You have deposited $"+amount+" into account: "+accountid+". "
								+ "\n Your balance is now $"+newBalance);
						transaction.logDeposit(amount, accountid);
					} else {
						System.out.println("Deposit failed.");
					}
					break;
				case 2:
					System.out.println("Enter Account ID: ");
					int accountid1 = scan.nextInt();
					scan.nextLine();
					Account account1 = new Account(accountid1);
					if(!account1.checkApproved(accountid1)) {
						System.out.println("This account has not yet been approved.");
						break;
					}
					double amount1 = -1;
					while (true) {
						System.out.println("Enter withdrawl amount: ");
						amount1 = scan.nextDouble();
						scan.nextLine();
						if(amount1 >= 0) {
							break;
						}
						System.out.println("That is not a valid amount, please try again.");
					}
					
					double oldBalance1 = account1.getBalanceDB(accountid1, user.getUserid());
					if(oldBalance1 == -1) {
						break;
					}
					double newBalance1 = transaction.withdrawDB(accountid1, user.getUserid(), amount1, oldBalance1);
					if(newBalance1 != -1) {
						System.out.println("You have withdrawn $"+amount1+" from account: "+accountid1+". "
								+ "\n Your balance is now $"+newBalance1);
						transaction.logWithdrawl(amount1, accountid1);
					} else {
						System.out.println("Withdraw failed.");
					}
					break;
				default:
					System.out.println("That is not a valid option, please try again.");
				}
			}
			break;
			
		//View account balance	
		case 4:
			System.out.println("Please enter the Account ID of the balance which you would like to check.");
			int accountid = scan.nextInt();
			scan.nextLine();
			Account account2 = new Account(accountid);
			if(!account2.checkApproved(accountid)) {
				System.out.println("This account has not yet been approved.");
				break;
			}
			double getbalance = account2.getBalanceDB(accountid, user.getUserid());
			if (getbalance != -1) {
				System.out.println("The balance of account "+accountid+" is "+getbalance);
			}
			break;
			
		//Only available to employees
		//Approve/Reject account requests
		case 5:
			Account account3 = new Account();
			System.out.println("Enter accountid: ");
			int accountid1 = scan.nextInt();
			scan.nextLine();
			System.out.println("Which of the following actions would you like to perform? "
					+ "Please select a number corresponding to the action you desire."
					+ "\n1. Approve account."
					+ "\n2. Reject account.");
			
			int choice2 = 0;
			while(choice2 != 1 && choice2 != 2) {
				choice2 = scan.nextInt();
				scan.nextLine();
				switch(choice2) {
					case 1:
						account3.approveAccountDB(accountid1);
						account3.logApprove(accountid1);
						break;
					case 2:
						account3.rejectAccountDB(accountid1);
						account3.logReject(accountid1);
						break;
					default:
						System.out.println("That is not a valid option, please try again.");
				}
			}
			break;
			
		//Get customer accounts	
		case 6:
			Account account4 = new Account();
			System.out.println("Enter User ID: ");
			int userid = scan.nextInt();
			scan.nextLine();
			account4.getCustomerAccountsDB(userid);
			break;
			
		//Create a user
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
