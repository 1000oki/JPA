package com.example.practice.model.song;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="song")
@IdClass(SongId.class)
public class SongEntity implements Serializable {
    @Id
    @Column(name = "song_name")
    private String songName;

    @Id
    @Column(name = "verse")
    private int verse;

    @Id
    @Column(name = "verse_row")
    private int verseRow;

    @Column(name = "lyrics")
    private String lyrics;
}
