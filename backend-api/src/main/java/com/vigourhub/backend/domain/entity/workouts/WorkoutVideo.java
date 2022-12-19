package com.vigourhub.backend.domain.entity.workouts;

import com.vigourhub.backend.domain.entity.AuditEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;


@Entity(name="workout_videos")
@Getter
@Setter
@NoArgsConstructor
public class WorkoutVideo extends AuditEntity {
    String name;
}
