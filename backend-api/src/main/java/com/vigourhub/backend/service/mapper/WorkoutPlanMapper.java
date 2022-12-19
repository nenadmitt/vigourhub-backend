package com.vigourhub.backend.service.mapper;


import com.vigourhub.backend.domain.entity.workout_plans.RoutineWorkout;
import com.vigourhub.backend.domain.entity.workout_plans.WorkingSet;
import com.vigourhub.backend.domain.entity.workout_plans.WorkoutPlan;
import com.vigourhub.backend.dto.workout_plans.RoutineWorkoutRequestDTO;
import com.vigourhub.backend.dto.workout_plans.RoutineWorkoutResponseDTO;
import com.vigourhub.backend.dto.workout_plans.WorkingSetDTO;
import com.vigourhub.backend.dto.workout_plans.WorkoutPlanResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WorkoutPlanMapper {

    WorkoutPlanResponseDTO toPlanResponse(WorkoutPlan workoutPlan);
    RoutineWorkoutResponseDTO domainToResponse(RoutineWorkout routineWorkout);

    RoutineWorkout createDTOtoDomain(RoutineWorkoutRequestDTO requestDTO);

    WorkingSet toWorkingSet(WorkingSetDTO dto);

    WorkingSetDTO toWorkingSetDTO(WorkingSet workingSet);

}
