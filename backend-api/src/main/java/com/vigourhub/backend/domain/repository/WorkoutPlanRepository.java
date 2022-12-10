package com.vigourhub.backend.domain.repository;

import com.vigourhub.backend.domain.models.workout_plans.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, UUID> {
}
