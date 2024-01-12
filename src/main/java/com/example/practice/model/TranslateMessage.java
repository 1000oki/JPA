package com.example.practice.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class TranslateMessage {
    @SerializedName("@type")
    private String type;
    @SerializedName("@service")
    private String service;
    @SerializedName("@version")
    private String version;
    @SerializedName("result")
    private Result result;
}
