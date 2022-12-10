//package com.vigourhub.backend.domain.models.meals;
//
//import com.vigourhub.backend.domain.models.AuditEntity;
//import lombok.AccessLevel;
//import lombok.experimental.FieldDefaults;
//
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.OneToMany;
//import java.util.List;
//import java.util.UUID;
//
//@Entity(name = "meals")
//@FieldDefaults(level = AccessLevel.PRIVATE)
//public class Meal extends AuditEntity {
//
//    @Id
//    private UUID id;
//
//    @OneToMany
//    List<FoodWithMetadata> ingridients;
//
//}
