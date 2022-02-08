package ru.kudesnik.mymovie.model.entities.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryEntity(
//    @PrimaryKey(autoGenerate = true)
    @PrimaryKey(autoGenerate = false) val idEntity: Int,
    val nameMovieEntity: String,
    val durationMovieEntity: Int,
    val posterMovieEntity: String,
    val ratingMovieEntity: Float,
    val commentMovieEntity: String
)
