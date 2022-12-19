package com.vigourhub.backend.web.controllers;

import com.vigourhub.backend.dto.PagedResponse;
import com.vigourhub.backend.dto.workout_plans.WorkoutDto;
import com.vigourhub.backend.dto.workouts.WorkoutRequestDTO;
import com.vigourhub.backend.dto.workouts.WorkoutResponseDTO;
import com.vigourhub.backend.service.WorkoutService;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/workouts")
public class WorkoutsController {

    private final WorkoutService workoutService;

    @Autowired
    public WorkoutsController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @GetMapping
    public ResponseEntity<PagedResponse<WorkoutResponseDTO>> getWorkouts(@RequestParam(required = false) PageRequest request) {
        var workouts = workoutService.getPagedWorkouts(request);
        return ResponseEntity.ok(workouts);
    }

    @PostMapping
    public ResponseEntity<WorkoutResponseDTO> createWorkout(@RequestBody @Valid WorkoutRequestDTO request) {
        return ResponseEntity.ok(this.workoutService.createWorkout(request));
    }

    @PostMapping("/{workoutId}/video")
    public ResponseEntity<Void> uploadWorkoutMedia(@MultipartForm MultipartFile file) {
       return ResponseEntity.ok().build();
    }
}
