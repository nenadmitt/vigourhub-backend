package com.vigourhub.notificationservice.consumer;

import com.fasterxml.jackson.annotation.JsonAnyGetter;

import java.io.Serializable;

public class TestDto implements Serializable {

    private String id;
    private String name;

    public TestDto(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public TestDto() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TestDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
