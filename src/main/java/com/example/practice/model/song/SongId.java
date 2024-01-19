package com.example.practice.model.song;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SongId implements Serializable {
    private String songName;
    private int verse;
    private int verseRow;
}

