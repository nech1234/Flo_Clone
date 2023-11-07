package com.example.myapplication

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SongTable")
data class Song(
    val title: String = "",
    val singer: String = "",
    var second: Int = 0,
    var playTime: Int = 0,
    var isPlaying: Boolean = false,
    var music: String = "",
    var coverIgm: Int? = null,
    var islike: Boolean = false
) {


    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
