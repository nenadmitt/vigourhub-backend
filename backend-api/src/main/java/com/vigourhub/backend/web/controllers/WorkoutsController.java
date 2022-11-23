package com.vigourhub.backend.web.controllers;

import com.vigourhub.backend.dto.PagedResponse;
import com.vigourhub.backend.dto.workout_plans.WorkoutDto;
import com.vigourhub.backend.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/workouts")
public class WorkoutsController {

    private final WorkoutService workoutService;

    @Autowired
    public WorkoutsController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @GetMapping
    public ResponseEntity<PagedResponse<WorkoutDto>> getWorkouts(@RequestParam(required = false) PageRequest request) {
        PagedResponse<WorkoutDto> workouts = workoutService.getPagedWorkouts(request);
        return ResponseEntity.ok(workouts);
    }
}
