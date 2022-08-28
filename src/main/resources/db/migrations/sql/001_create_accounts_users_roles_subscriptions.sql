create table if not exists subscriptions (
  id uuid primary key,
  name varchar(55) not null,
  price float not null,
  max_users int not null
);

insert into subscriptions (id, name, price, max_users) values
( '39c13f16-2207-4a56-8bca-38bf8e7408c6', 'Basic', 9.99, 5),
( '1dd1be0c-6ebb-4d9c-8f7f-c0d3dcf51362', 'Standard', 24.99, 15),
( '55dbe27f-946c-4b07-a5aa-1a11ce08c81c', 'Pro', 49.99, 35),
( '3c3547d7-2611-4128-84ce-8250e3723d89', 'Custom', 0.00, 999);


create table if not exists accounts (
  id uuid primary key,
  name varchar(55) not null,
  subscription_id uuid null,
  created_at timestamp not null default now(),
  updated_at timestamp null,
  is_trial boolean default false,
  trial_started timestamp not null default now(),
  foreign key(subscription_id) references subscriptions(id)
);

create table if not exists roles (
  id uuid primary key,
  name varchar(55) not null
);

create table if not exists users (
  id uuid primary key,
  username varchar(255) not null,
  password varchar(255) not null,
  first_name varchar(255) not null,
  last_name varchar(255) not null,
  created_at timestamp not null default now(),
  updated_at timestamp null,
  account_id uuid not null,
  foreign key(account_id) references accounts(id) on delete cascade
);

create table if not exists user_roles (
   user_id uuid not null,
   role_id uuid not null,
   foreign key(user_id) references users(id) on delete cascade,
   foreign key(role_id) references roles(id) on delete cascade
);

insert into roles (id, name) values
('3c3547d7-2611-4128-84ce-8250e3723d89','Instructor'),
('cbd387ce-d3ba-4d7b-a2ea-d98902874586','Client'),
('735eaf44-7cae-47a2-9f7c-2cd7649f92bf','SuperAdmin');
