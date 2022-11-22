create table if not exists client_invitations (
   id uuid primary key not null,
   username varchar(255) not null unique,
   account_id uuid not null,
   created_at timestamp not null default now(),
   foreign key(account_id) references accounts(id) on delete cascade
)