package ru.kudesnik.mymovie.model.entities.database

import androidx.room.*

@Dao
interface HistoryDao {
    @Query("SELECT * FROM HistoryEntity")
    fun all(): List<HistoryEntity>

    @Query("SELECT * FROM HistoryEntity WHERE nameMovieEntity LIKE :movie")
    fun getDataByWord(movie: String): List<HistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: HistoryEntity)

    @Update
    fun update(entity: HistoryEntity)

    @Delete
    fun delete(entity: HistoryEntity)

    @Query("UPDATE HistoryEntity SET commentMovieEntity = :movieComment WHERE nameMovieEntity = :movieName")
    fun updateByMovieComment(movieName: String, movieComment: String)

//    @Query("UPDATE employee SET salary = :newSalary WHERE id IN (:idList)")
//    fun updateSalaryByIdList(idList: List<Long?>?, newSalary: Int): Int

    @Query("DELETE FROM HistoryEntity WHERE nameMovieEntity = :movieName")
    fun deleteByMovieName(movieName: String)
}