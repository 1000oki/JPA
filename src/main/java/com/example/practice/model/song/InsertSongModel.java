package com.example.practice.model.song;

import lombok.Data;

@Data
public class InsertSongModel {
    private String songName;
    private int verse;
    private int verseRow;
    private String lyrics;
}
