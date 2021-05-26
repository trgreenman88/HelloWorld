package controllers;

import java.util.Objects;
import java.util.Scanner;

import com.revature.dao.AccountDAOImpl;
import com.revature.dao.TransactionDAOImpl;
import com.revature.dao.UserDAOImpl;
import com.revature.util.*;

import model.Account;
import model.Transactions;
import model.User;

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
		
		//String[] userInfo = login.getUserInfo(usernameEntry, passwordEntry);
		//User user = new User(Integer.parseInt(userInfo[0]), userInfo[1], userInfo[2], userInfo[3]);
		User user = login.getUserInfo(usernameEntry, passwordEntry);
		
		if (user.getStatus().equals("Customer")) {
			customerMenu(user);
		} else if (user.getStatus().equals("Employee")) {
			employeeMenu(user);
		} else {
			System.out.println("Invalid user status.");
		}
		
	}
	
	
	public static void customerMenu(User user) {
		int choice = -1;
		while (choice != 0) {
			System.out.println("");
			System.out.println("Which of the following actions would you like to perform? "
					+ "Please select a number corresponding to the action you desire."
					+ "\n0. Quit the program"
					+ "\n1. Create an account" + "\n2. Post a pending transfer"
					+ "\n3. Approve/Reject a pending transfer"
					+ "\n4. Deposit/Withdraw money from an account"
					+ "\n5. View account balance");
			choice = scan.nextInt();
			scan.nextLine();
			action(choice, user);
		}
	}
	
	public static void employeeMenu(User user) {
		int choice = -1;
		while (choice != 0) {
			System.out.println("");
			System.out.println("Which of the following actions would you like to perform? "
					+ "Please select a number corresponding to the action you desire."
					+ "\n0. Quit the program"
					+ "\n1. Create an account" + "\n2. Post a pending transfer"
					+ "\n3. Approve/Reject a pending transfer"
					+ "\n4. Deposit/Withdraw money from an account"
					+ "\n5. View account balance"
					+ "\n6. Approve/Reject an account request"
					+ "\n7. View customer accounts"
					+ "\n8. Create new User");
			choice = scan.nextInt();
			scan.nextLine();
			action(choice, user);
		}
	}
	
	
	public static void action(int choice, User user) {
		if((choice >= 6 && choice <= 8) && !user.getStatus().equals("Employee")) {
			System.out.println("You must be an employee to access this option.");
			return;
		}
		
		switch(choice) {
		case 0:
			System.out.println("You have successfully quit the program.");
			break;
		//Create an account
		case 1:
			System.out.println("Please enter the starting balance of your new account: ");
			double balance = scan.nextDouble();
			scan.nextLine();
			Account newAccount = new Account(balance);
			AccountDAOImpl accountDAO = new AccountDAOImpl();
			accountDAO.addPendingAccountDB(newAccount, user.getUserid());
			newAccount.logAccount(accountDAO.getNewestAccountID(), balance);
			break;
			
		//Create a pending transfer	
		case 2:
			System.out.println("Enter account ID of giving account: ");
			int acctid1 = scan.nextInt();
			scan.nextLine();
			Account acct1 = new Account(acctid1);
			AccountDAOImpl acctDAO1 = new AccountDAOImpl();
			
			//Check that accountID is valid
			if(!acctDAO1.checkValidID(acct1)) {
				break;
			}
			//Check that user owns this account.
			if(!acctDAO1.checkOwnership(acct1, user.getUserid())) {
				break;
			}
			
			System.out.println("Enter account ID of recieving account: ");
			int acctid2 = scan.nextInt();
			scan.nextLine();
			Account acct2 = new Account(acctid2);
			acctDAO1.checkValidID(acct2);
			
			if(acctid1 == acctid2) {
				System.out.println("You cannot transfer money from an account to itself.");
				break;
			}
			
			double amt = 0;
			//Make sure transfer amount is positive.
			while (true) {
				System.out.println("Enter transfer amount: ");
				amt = scan.nextDouble();
				scan.nextLine();
				if (amt >= 0) {
					break;
				}else {
					System.out.println("That is not a valid amount, please try again: ");
				}
			}
			Transactions trans = new Transactions(amt);
			TransactionDAOImpl transDAO = new TransactionDAOImpl();
			transDAO.pendingTransferDB(acct1, acct2, user.getUserid(), trans);
			trans.logPendingTransfer(amt, acctid1, acctid2, user.getUserid());
			break;
			
		//Accept/Reject pending transfers	
		case 3:	
			System.out.println("Enter Transaction ID to interact with: ");
			int transid = scan.nextInt();
			scan.nextLine();
			TransactionDAOImpl transDAO1 = new TransactionDAOImpl();
			AccountDAOImpl acctDAO = new AccountDAOImpl();
			Transactions trans1 = transDAO1.getTransactionDB(transid);
			if (Objects.isNull(trans1)) {
				break;
			}
			Account acct01 = new Account(trans1.getPayingAccountid());
			Account acct02 = new Account(trans1.getRecievingAccountid());
			
			if(!acctDAO.checkValidID(acct01)) {
				System.out.println("There is a problem with the paying account.");
				break;
			}
			if(!acctDAO.checkValidID(acct02)) {
				System.out.println("There is a problem with the receiving account.");
				break;
			}
			if(!acctDAO.checkApproved(acct01)) {
				System.out.println("The paying account has not yet been approved.");
				break;
			}
			if(!acctDAO.checkApproved(acct02)) {
				System.out.println("This receiving account has not yet been approved.");
				break;
			}
			if(acct01.getAccountid() == acct02.getAccountid()) {
				System.out.println("You can't transfer money to the same account.");
				break;
			}
			
			System.out.println("Which of the following actions would you like to perform? "
					+ "Please select a number corresponding to the action you desire."
					+ "\n1. Reject the transfer"
					+ "\n2. Accept the transfer");
			int answer = scan.nextInt();
			scan.nextLine();
			
			switch(answer) {
			case 1:
				if(acctDAO.checkOwnership(acct02, user.getUserid())) {
					transDAO1.deletePendingTransaction(trans1);
					//log reject transfer
					trans1.logRejectTransfer(trans1.getTransactionid());
				}else {
					System.out.println("You do not have access to this transaction.");
				}
				break;
			case 2:
				double balance01 = acctDAO.getBalanceDB(acct01);
				//Only the receiving user can accept or reject transfers
				double balance02 = acctDAO.getBalanceDB(acct02, user.getUserid());
				if (balance02 == -1) {
					break;
				}
				
				if(balance01 == -1) {
					break;
				}
				double newBalance01 = transDAO1.withdrawDB(acct01, trans1, balance01);
				//Breaks if the withdrawal resluts in a negative account balance
				if(newBalance01 == -1) {
					break;
				}
				double newBalance02 = transDAO1.depositDB(acct02, user.getUserid(), trans1, balance02);
				if(newBalance01 != -1) {
					System.out.println("You have transfered $"+trans1.getAmount()+" from account "+acct01.getAccountid()+" to "
							+ "account "+acct02.getAccountid()+"."
							+ "\n Your balance in account "+acct01.getAccountid()+" is now $"+newBalance01
							+ "\n Your balance in account "+acct02.getAccountid()+" is now $"+newBalance02);
					trans1.logTransfer(trans1.getAmount(), acct01.getAccountid(), acct02.getAccountid());
					transDAO1.deletePendingTransaction(trans1);
					//Log accept transfer
					trans1.logAcceptTransfer(trans1.getTransactionid());
				} else {
					System.out.println("Transfer failed.");
				}
				break;
			default:
				System.out.println("That is not a valid option.");
			}
			break;
			
			
		//Make a withdrawal/deposit	
		case 4:
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
					AccountDAOImpl accountDAO0 = new AccountDAOImpl();
					if(!accountDAO0.checkValidID(account0)) {
						break;
					}
					if(!accountDAO0.checkApproved(account0)) {
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
					
					Transactions transaction = new Transactions(amount);
					TransactionDAOImpl transactionDAO = new TransactionDAOImpl();
					
					double oldBalance = accountDAO0.getBalanceDB(account0, user.getUserid());
					if(oldBalance == -1) {
						break;
					}
					double newBalance = transactionDAO.depositDB(account0, user.getUserid(), transaction, oldBalance);
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
					AccountDAOImpl accountDAO1 = new AccountDAOImpl();
					if(!accountDAO1.checkValidID(account1)) {
						break;
					}
					if(!accountDAO1.checkApproved(account1)) {
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
					
					Transactions transaction2 = new Transactions(amount1);
					TransactionDAOImpl transactionDAO2 = new TransactionDAOImpl();
					
					double oldBalance1 = accountDAO1.getBalanceDB(account1, user.getUserid());
					if(oldBalance1 == -1) {
						break;
					}
					double newBalance1 = transactionDAO2.withdrawDB(account1, user.getUserid(), transaction2, oldBalance1);
					if(newBalance1 != -1) {
						System.out.println("You have withdrawn $"+amount1+" from account: "+accountid1+". "
								+ "\n Your balance is now $"+newBalance1);
						transaction2.logWithdrawl(amount1, accountid1);
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
		case 5:
			System.out.println("Please enter the Account ID of the balance which you would like to check.");
			int accountid = scan.nextInt();
			scan.nextLine();
			Account account2 = new Account(accountid);
			AccountDAOImpl accountDAO2 = new AccountDAOImpl();
			if(!accountDAO2.checkValidID(account2)) {
				break;
			}
			if(!accountDAO2.checkApproved(account2)) {
				System.out.println("This account has not yet been approved.");
				break;
			}
			double getbalance = accountDAO2.getBalanceDB(account2, user.getUserid());
			if (getbalance != -1) {
				System.out.println("The balance of account "+accountid+" is "+getbalance);
			}
			break;
			
		//Only available to employees
		//Approve/Reject account requests
		case 6:
			Account account3 = new Account();
			AccountDAOImpl accountDAO3 = new AccountDAOImpl();
			System.out.println("Enter accountid: ");
			int accountid1 = scan.nextInt();
			scan.nextLine();
			account3.setAccountid(accountid1);
			if(!accountDAO3.checkValidID(account3)) {
				break;
			}
			account3.setAccountid(accountid1);
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
						accountDAO3.approveAccountDB(account3);
						account3.logApprove(accountid1);
						break;
					case 2:
						accountDAO3.rejectAccountDB(account3);
						account3.logReject(accountid1);
						break;
					default:
						System.out.println("That is not a valid option, please try again.");
				}
			}
			break;
			
		//Get customer accounts	
		case 7:
			//Account account4 = new Account();
			AccountDAOImpl accountDAO4 = new AccountDAOImpl();
			System.out.println("Enter User ID: ");
			int userid = scan.nextInt();
			scan.nextLine();
			accountDAO4.getCustomerAccountsDB(userid);
			break;
			
		//Create a user
		case 8:
			System.out.println("Enter User's Username: ");
			String userEntry = scan.nextLine();
			System.out.println("Enter User's Password: ");
			String passEntry = scan.nextLine();
			System.out.println("Enter User's Status: ");
			//Make sure that statusEntry is either "Employee" or "Customer"
			String statusEntry = scan.nextLine();
			
			User newUser = new User(1, userEntry, passEntry, statusEntry);
			//newUser.addUserDB();
			UserDAOImpl userDAO = new UserDAOImpl();
			userDAO.addUserDB(newUser);
			newUser.logUser();
			break;
		default:
			System.out.println("That is not an option.");
		}
	}
}
