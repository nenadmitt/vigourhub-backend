//package com.vigourhub.backend.domain.models.meals;
//
//import com.vigourhub.backend.domain.models.AuditEntity;
//import lombok.*;
//import lombok.experimental.FieldDefaults;
//import org.hibernate.Hibernate;
//
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import java.util.Objects;
//import java.util.UUID;
//
//@Data
//@Entity(name = "foods")
//@FieldDefaults(level = AccessLevel.PRIVATE)
//public class Food extends AuditEntity {
//
//    @Id
//    UUID id;
//    String name;
//    float fatsPer100g;
//    float proteinsPer100g;
//    float carbohydratesPer100g;
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
//        Food food = (Food) o;
//        return id != null && Objects.equals(id, food.id);
//    }
//}
