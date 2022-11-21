create table if not exists client_invitations (
   id uuid primary key not null,
   username string not null unique,
   account_id uuid not null,
   foreign key(account_id) references accounts(id) on delete cascade
   created_at timestamp default now()
)