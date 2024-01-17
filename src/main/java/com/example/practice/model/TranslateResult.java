package com.example.practice.model;

import com.example.practice.model.TranslateMessage;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class TranslateResult {
    @SerializedName("message")
    private TranslateMessage message;
}
