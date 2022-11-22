create type workout_type as ENUM ('Core', 'Legs', 'Biceps', 'Triceps', 'Back', 'Chest', 'Shoulders', 'Abs');

create table if not exists workouts (
   id uuid primary key not null,
   name varchar(255) not null unique,
   account_id uuid null,
   is_system boolean default false,
   created_at timestamp not null default now(),
   updated_at timestamp null,
   type workout_type not null,
   foreign key(account_id) references accounts(id) on delete cascade
);

insert into workouts (id, name, is_system, type) values
('21958202-6d1a-494a-9d7e-07d7f3f79f52', 'Squats', true, 'Legs'),
('5aec02a1-e9f4-4b05-aa2d-7a35e9f6f5ec', 'Bench Press', true, 'Chest'),
('ee797847-3d99-44e6-b36d-db569e9e2715', 'Deadlifts', true, 'Core');

create table if not exists workout_plans (
  id uuid primary key,
  name varchar(255) not null,
  created_at timestamp not null default now(),
  updated_at timestamp null,
  created_by uuid not null,
  assigned_to uuid null,
  completed boolean default false,
  foreign key(created_by) references users(id) on delete cascade,
  foreign key(assigned_to) references users(id)
);

create table if not exists workout_routines (
  id uuid primary key,
  workout_plan_id uuid not null,
  name varchar(255) not null,
  foreign key(workout_plan_id) references workout_plans(id) on delete cascade
);

create table if not exists routine_workouts (
  id uuid primary key,
  routine_id uuid not null,
  workout_id uuid not null,
  metadata jsonb,
  foreign key(routine_id) references workout_routines(id) on delete cascade,
  foreign key(workout_id) references workouts(id) on delete cascade
);