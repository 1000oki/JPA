package com.example.practice.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Result {
    @SerializedName("srcLangType")
    private String srcLangType;
    @SerializedName("tarLangType")
    private String tarLangType;
    @SerializedName("translatedText")
    private String translatedText;
    @SerializedName("engineType")
    private String engineType;
}
