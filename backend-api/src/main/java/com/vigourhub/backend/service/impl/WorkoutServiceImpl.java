package com.vigourhub.backend.service.impl;

import com.vigourhub.backend.domain.adapters.WorkoutsRepositoryAdapter;
import com.vigourhub.backend.domain.entity.account.Account;
import com.vigourhub.backend.domain.entity.enums.WorkoutType;
import com.vigourhub.backend.domain.entity.workouts.Workout;
import com.vigourhub.backend.dto.PagedResponse;
import com.vigourhub.backend.dto.workouts.WorkoutRequestDTO;
import com.vigourhub.backend.dto.workouts.WorkoutResponseDTO;
import com.vigourhub.backend.security.SecurityUtils;
import com.vigourhub.backend.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@Component
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutsRepositoryAdapter adapter;

    @Autowired
    public WorkoutServiceImpl(WorkoutsRepositoryAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public PagedResponse<WorkoutResponseDTO> getPagedWorkouts(PageRequest request) {

        var principal = SecurityUtils.getCurrentPrincipal();
        var accountID = UUID.fromString(principal.getAccountId());

        var result = adapter.getPagedWorkoutsForAccount(accountID, request);
        var responseItems = new ArrayList<WorkoutResponseDTO>();
        result.get().forEach(w -> responseItems.add(WorkoutResponseDTO.fromDomain(w)));

        var pagedResponse = new PagedResponse<WorkoutResponseDTO>();
        pagedResponse.setItems(responseItems);
        pagedResponse.setTotalPages(result.getTotalPages());
        pagedResponse.setPerPage(result.getSize());
        pagedResponse.setCurrentPage(result.getNumber());

        return pagedResponse;
    }

    @Override
    public WorkoutResponseDTO createWorkout(WorkoutRequestDTO request) {

        var currentUser = SecurityUtils.getCurrentPrincipal();

        var workout = new Workout();
        workout.setId(UUID.randomUUID());
        workout.setType(WorkoutType.Abs);
        workout.setName(request.getName());
        workout.setSystem(false);

        var account = new Account();
        account.setId(UUID.fromString(currentUser.getAccountId()));

        workout.setAccount(account);
        adapter.insert(workout);

        return WorkoutResponseDTO.fromDomain(workout);
    }
}
