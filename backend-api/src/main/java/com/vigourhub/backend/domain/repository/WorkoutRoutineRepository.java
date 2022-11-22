package com.vigourhub.backend.domain.repository;

import com.vigourhub.backend.domain.models.workouts.Workout;
import com.vigourhub.backend.domain.models.workouts.WorkoutRoutine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkoutRoutineRepository extends JpaRepository<WorkoutRoutine, UUID> {
}
