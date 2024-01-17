package com.example.practice.model.song;

import lombok.Data;

import java.util.List;

@Data
public class SongModel {
    private String songName;
    private List<LyricsModel> verse;
}
