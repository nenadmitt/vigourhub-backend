package com.vigourhub.backend.domain.repository;

import com.vigourhub.backend.domain.models.workouts.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkoutRepository extends JpaRepository<Workout, UUID> {
}
