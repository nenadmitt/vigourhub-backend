package com.vigourhub.backend.domain.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vigourhub.backend.domain.models.workouts.WorkoutMetadata;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class WorkoutMetadataConverter implements AttributeConverter<WorkoutMetadata,String> {
    private final ObjectMapper mapper;

    public WorkoutMetadataConverter() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public String convertToDatabaseColumn(WorkoutMetadata workoutMetadata) {

        String value;
        try {
            value = mapper.writeValueAsString(workoutMetadata);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return value;
    }

    @Override
    public WorkoutMetadata convertToEntityAttribute(String s) {

        WorkoutMetadata metadata;

        try {
            metadata = mapper.readValue(s, WorkoutMetadata.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return metadata;
    }
}
