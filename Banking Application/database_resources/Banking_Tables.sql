--Milestone #1
create database Banking_Application;

create table Users(
	UserID smallserial,
	Username varchar(100),
	Password varchar(100),
	constraint Users_pk primary key (UserID)
);

create table Account(
	AccountID bigserial,
	UserID smallserial,
	Balance decimal,
	constraint Account_pk primary key (AccountID),
	constraint Account_fk foreign key (UserID) references Users(UserID)
);

create table Pending_Accounts(
	AccountID bigserial,
	UserID smallserial,
	Status varchar(100), /*Approved, Rejected, or Pending*/
	constraint Pending_Accounts_pk primary key (AccountID),
	constraint Pending_Accounts_fk foreign key (UserID) references Users(UserID)
);

create table Transactions(
	TransactionID bigserial,
	AccountID bigserial,
	UserID smallserial,
	Date TimeStamp,
	Description varchar(200),
	Amount decimal, /*+ for getting money, - for giving money*/
	constraint Transactions_pk primary key (TransactionID),
	constraint Transactions_fk foreign key (AccountID) references Account(AccountID)
);
