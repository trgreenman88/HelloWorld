--Corrections to Milestone #1
ALTER TABLE users ADD COLUMN status VARCHAR(10);

alter table account drop column userid;
alter table account add column userid integer
references users(userid) on delete cascade
unique not null;

alter table pending_accounts drop column userid;
alter table pending_accounts add column userid integer
references users(userid) on delete cascade
unique not null;

alter table transactions drop column accountid;
alter table transactions add column accountid integer
references account(accountid) on delete cascade
unique not null;