package com.example.practice.model;

import lombok.Data;

import java.util.Map;

@Data
public class PostRequestModel {
    private String apiUrl;
    private Map<String, String> requestHeaders;
    private String text;
    private String beforeLanguage;
    private String afterLanguage;
}
