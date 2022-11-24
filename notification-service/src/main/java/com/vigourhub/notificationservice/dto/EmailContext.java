package com.vigourhub.notificationservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class EmailContext {
    private String sender;
    private String receiver;
    private String title;
    private String templateUrl;
    private Map<String,String> templateContext;
}
