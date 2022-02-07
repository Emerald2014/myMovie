package ru.kudesnik.mymovie.model.entities.database

import androidx.room.*

@Dao
interface FavouriteDao {
    @Query("SELECT * FROM FavouriteEntity")
    fun all(): List<FavouriteEntity>

    @Query("SELECT * FROM FavouriteEntity WHERE nameMovieEntity LIKE :movie")
    fun getDataByWord(movie: String): List<FavouriteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: FavouriteEntity)

    @Update
    fun update(entity: FavouriteEntity)

    @Delete
    fun delete(entity: FavouriteEntity)

    @Query("DELETE FROM FavouriteEntity WHERE nameMovieEntity = :movieName")
    fun deleteByMovieName(movieName: String)
}