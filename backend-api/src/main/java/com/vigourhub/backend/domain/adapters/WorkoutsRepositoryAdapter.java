package com.vigourhub.backend.domain.adapters;

import com.vigourhub.backend.domain.entity.workouts.Workout;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface WorkoutsRepositoryAdapter {

    void insert(Workout workout);

    Page<Workout> getPagedWorkoutsForAccount(UUID accountID, PageRequest request);

}
