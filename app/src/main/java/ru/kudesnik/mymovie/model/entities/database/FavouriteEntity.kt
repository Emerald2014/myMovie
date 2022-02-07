package ru.kudesnik.mymovie.model.entities.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavouriteEntity(
    @PrimaryKey(autoGenerate = true) val idEntity: Int,
    val nameMovieEntity: String,
    val durationMovieEntity: Int,
    val posterMovieEntity: String,
    val ratingMovieEntity: Float
)
