package com.example.practice.model;

import lombok.Data;

import java.util.Map;

@Data
public class SendMessageRequestModel {
    private String text;
    private String beforeLanguage;
    private String afterLanguage;
}
