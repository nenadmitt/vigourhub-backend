package com.vigourhub.backend.service.impl;

import com.vigourhub.backend.domain.adapters.WorkoutsRepositoryAdapter;
import com.vigourhub.backend.dto.PagedResponse;
import com.vigourhub.backend.dto.workout_plans.WorkoutDto;
import com.vigourhub.backend.infrastructure.security.SecurityUtils;
import com.vigourhub.backend.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@Component
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutsRepositoryAdapter workoutsRepositoryAdapter;

    @Autowired
    public WorkoutServiceImpl(WorkoutsRepositoryAdapter workoutsRepositoryAdapter) {
        this.workoutsRepositoryAdapter = workoutsRepositoryAdapter;
    }

    @Override
    public PagedResponse<WorkoutDto> getPagedWorkouts(PageRequest request) {

        var principal = SecurityUtils.getCurrentPrincipal();
        var accountID = UUID.fromString(principal.getAccountId());

        var result = workoutsRepositoryAdapter.getPagedWorkoutsForAccount(accountID, request);
        var responseItems = new ArrayList<WorkoutDto>();
        result.get().forEach(w -> responseItems.add(WorkoutDto.fromDomain(w)));

        var pagedResponse = new PagedResponse<WorkoutDto>();
        pagedResponse.setItems(responseItems);
        pagedResponse.setTotalPages(result.getTotalPages());
        pagedResponse.setPerPage(result.getSize());
        pagedResponse.setCurrentPage(result.getNumber());

        return pagedResponse;
    }
}
