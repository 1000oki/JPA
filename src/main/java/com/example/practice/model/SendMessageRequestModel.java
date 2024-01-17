package com.example.practice.model;

import lombok.Data;

@Data
public class SendMessageRequestModel {
    private String text;
    private String beforeLanguage;
    private String afterLanguage;
    private String topic;
}
