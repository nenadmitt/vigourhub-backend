package com.vigourhub.backend.domain.repository;

import com.vigourhub.backend.domain.models.workouts.Workout;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkoutRepository extends JpaRepository<Workout, UUID> {

    Page<Workout> findAllByAccountIdOrIsSystemTrue(UUID accountId, Pageable pageable);
}
