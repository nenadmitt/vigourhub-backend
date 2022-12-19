create table if not exists workout_videos (
   id uuid not null primary key,
   file_name varchar(255) not null,
   workout_id uuid not null,
   foreign key (workout_id) references workouts(id) on delete cascade
);