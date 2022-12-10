package com.vigourhub.backend.domain.repository;

import com.vigourhub.backend.domain.models.workout_plans.RoutineWorkout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoutineWorkoutRepository extends JpaRepository<RoutineWorkout, UUID> {
}
