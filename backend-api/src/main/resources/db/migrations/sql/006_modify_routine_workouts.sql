alter table routine_workouts
add column workings_sets jsonb;

alter table routine_workouts
drop column metadata;

alter table routine_workouts
add column execution_type varchar(255) not null;