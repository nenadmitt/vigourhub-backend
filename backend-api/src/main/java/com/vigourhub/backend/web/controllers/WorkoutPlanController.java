package com.vigourhub.backend.web.controllers;

import com.vigourhub.backend.dto.IdResponseDto;
import com.vigourhub.backend.dto.workout_plans.FullWorkoutPlanDto;
import com.vigourhub.backend.dto.workout_plans.WorkoutPlanRequest;
import com.vigourhub.backend.infrastructure.exceptions.NotFoundException;
import com.vigourhub.backend.service.WorkoutPlanService;
import com.vigourhub.backend.web.WebConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@RestController
@RequestMapping(WebConstants.V1 + WebConstants.WORKOUT_PLANS)
public class WorkoutPlanController {

    private final WorkoutPlanService workoutPlanService;

    @Autowired
    public WorkoutPlanController(WorkoutPlanService workoutPlanService) {
        this.workoutPlanService = workoutPlanService;
    }

    @PostMapping
    public ResponseEntity<IdResponseDto> createWorkoutPlan(@RequestBody @Valid WorkoutPlanRequest request) {
        return ResponseEntity.ok(this.workoutPlanService.createWorkoutPlan(request));
    }

    @PostMapping("/{workoutPlanId}/routines")
    public ResponseEntity<IdResponseDto> addRoutine(@PathVariable UUID workoutPlanId, @RequestParam(name="name") @NotBlank String name) throws Exception {
        return ResponseEntity.ok(this.workoutPlanService.createWorkoutRoutine(workoutPlanId, name));
    }

    @PostMapping("/{workoutPlanId}/routines/{routineId}/workouts")
    public ResponseEntity<IdResponseDto> addWorkout(@PathVariable UUID workoutPlanId, @PathVariable UUID routineId) throws Exception {
        return ResponseEntity.ok(this.workoutPlanService.addRoutineWorkout(workoutPlanId, routineId));
    }

    @GetMapping("/{workoutPlanId}")
    public ResponseEntity<FullWorkoutPlanDto> getWorkoutPlan(@PathVariable UUID workoutPlanId) throws NotFoundException {
        return ResponseEntity.ok(this.workoutPlanService.getFullWorkoutPlan(workoutPlanId));
    }
}
