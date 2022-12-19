package com.vigourhub.backend.domain.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vigourhub.backend.domain.entity.workout_plans.WorkingSet;
import com.vigourhub.backend.dto.workout_plans.WorkingSetDTO;
import com.vigourhub.backend.dto.workout_plans.WorkoutExecutionDTO;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.List;

@Converter
public class WorkingSetConverter implements AttributeConverter<List<WorkingSet>,String> {
    private final ObjectMapper mapper;

    public WorkingSetConverter() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public String convertToDatabaseColumn(List<WorkingSet> workoutMetadata) {

        String value;
        try {
            value = mapper.writeValueAsString(workoutMetadata);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return value;
    }

    @Override
    public List<WorkingSet> convertToEntityAttribute(String s) {

        WorkingSet[] executions;

        try {
            executions = mapper.readValue(s, WorkingSet[].class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return List.of(executions);
    }
}
