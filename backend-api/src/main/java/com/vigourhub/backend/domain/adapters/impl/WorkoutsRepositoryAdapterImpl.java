package com.vigourhub.backend.domain.adapters.impl;

import com.vigourhub.backend.domain.adapters.WorkoutsRepositoryAdapter;
import com.vigourhub.backend.domain.entity.workouts.Workout;
import com.vigourhub.backend.domain.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WorkoutsRepositoryAdapterImpl implements WorkoutsRepositoryAdapter {

    private final WorkoutRepository repository;

    @Autowired
    public WorkoutsRepositoryAdapterImpl(WorkoutRepository repository) {
        this.repository = repository;
    }

    @Override
    public void insert(Workout workout) {
        this.repository.save(workout);
    }

    @Override
    public Page<Workout> getPagedWorkoutsForAccount(UUID accountID, PageRequest request) {
        return repository.findAllByAccountIdOrIsSystemTrue(accountID, request);
    }
}
