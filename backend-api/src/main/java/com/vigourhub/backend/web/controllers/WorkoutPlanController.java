package com.vigourhub.backend.web.controllers;

import com.vigourhub.backend.dto.IdResponseDto;
import com.vigourhub.backend.dto.workout_plans.RoutineRequestDTO;
import com.vigourhub.backend.dto.workout_plans.RoutineWorkoutRequestDTO;
import com.vigourhub.backend.dto.workout_plans.WorkoutPlanResponseDTO;
import com.vigourhub.backend.dto.workout_plans.WorkoutPlanRequestDTO;
import com.vigourhub.backend.infrastructure.exceptions.NotFoundException;
import com.vigourhub.backend.service.WorkoutPlanService;
import com.vigourhub.backend.web.WebConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<IdResponseDto> createWorkoutPlan(@RequestBody @Valid WorkoutPlanRequestDTO request) {
        return ResponseEntity.ok(this.workoutPlanService.createWorkoutPlan(request));
    }

    @PostMapping("/{workoutPlanId}/routines")
    public ResponseEntity<IdResponseDto> addRoutine(@PathVariable UUID workoutPlanId, @RequestBody @Valid RoutineRequestDTO requestDTO) throws Exception {
        return ResponseEntity.ok(this.workoutPlanService.createWorkoutRoutine(workoutPlanId, requestDTO));
    }

    @PostMapping("/{workoutPlanId}/routines/{routineId}/workouts")
    @PreAuthorize("hasRole('ROLE_Instructor')")
    public ResponseEntity<IdResponseDto> addWorkout(@PathVariable UUID workoutPlanId, @PathVariable UUID routineId,@Valid @RequestBody RoutineWorkoutRequestDTO requestDTO) throws Exception {
        return ResponseEntity.ok(this.workoutPlanService.addRoutineWorkout(workoutPlanId, routineId , requestDTO));
    }

    @GetMapping("/{workoutPlanId}")
    public ResponseEntity<WorkoutPlanResponseDTO> getWorkoutPlan(@PathVariable UUID workoutPlanId) throws NotFoundException {
        return ResponseEntity.ok(this.workoutPlanService.getFullWorkoutPlan(workoutPlanId));
    }
}
