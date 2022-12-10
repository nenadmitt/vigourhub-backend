//package com.vigourhub.backend.domain.models.meals;
//
//import com.vigourhub.backend.domain.models.AuditEntity;
//import lombok.AccessLevel;
//import lombok.Data;
//import lombok.experimental.FieldDefaults;
//
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import java.util.UUID;
//
//@Entity(name = "foods_with_metadata")
//@Data
//@FieldDefaults(level = AccessLevel.PRIVATE)
//public class FoodWithMetadata extends AuditEntity {
//
//    @Id
//    UUID id;
//
//    @ManyToOne
//    @JoinColumn(name = "food_id")
//    Food food;
//
//    @ManyToOne
//    @JoinColumn(name = "meal_plan_id")
//    MealPlan mealPlan;
//
//    float amount;
//    String measurementSystem;
//}
//
