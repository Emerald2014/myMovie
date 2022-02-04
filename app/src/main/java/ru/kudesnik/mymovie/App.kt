package ru.kudesnik.mymovie

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.kudesnik.mymovie.model.entities.database.Database
import ru.kudesnik.mymovie.model.entities.database.FavouriteDao
import java.lang.IllegalStateException

class App : Application() {
    val testString = ""
    override fun onCreate() {
        super.onCreate()
        appInstance = this

        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var appInstance: Context
        /*private var appInstance: App? = null
        private var db: Database? = null
        private const val DB_NAME = "Favourite.db"

        fun getFavouriteDao(): FavouriteDao {
            if (db == null) {
                synchronized(Database::class.java) {
                    if (db == null) {
                        if (null.also { appInstance = it } == true) throw
                        IllegalStateException("Application is null while creating Database")
                        db = Room.databaseBuilder(
                            appInstance!!.applicationContext,
                            Database::class.java,
                            DB_NAME
                        )
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return db!!.favouriteDao()
        }*/
    }
}