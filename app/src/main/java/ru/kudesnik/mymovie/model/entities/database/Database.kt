package ru.kudesnik.mymovie.model.entities.database

import androidx.room.Room
import androidx.room.RoomDatabase
import ru.kudesnik.mymovie.App

@androidx.room.Database(
    entities = [
        FavouriteEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract class Database : RoomDatabase() {
    abstract fun favouriteDao(): FavouriteDao

    companion object {
        private const val DB_NAME = "add_database.db"

        val db: Database by lazy {
            Room.databaseBuilder(
                App.appInstance,
                Database::class.java,
                DB_NAME
            ).build()
        }
    }
}