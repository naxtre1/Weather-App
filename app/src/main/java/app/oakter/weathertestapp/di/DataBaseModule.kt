package app.oakter.weathertestapp.di

import android.app.Application
import androidx.room.Room
import app.oakter.weathertestapp.data.local.room.WeatherDB
import app.oakter.weathertestapp.data.local.room.dao.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Provides
    @Singleton
    fun providePokemonDB(application: Application?): WeatherDB {
        return Room.databaseBuilder(application!!, WeatherDB::class.java, "Favorite Database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun providePokeDao(weatherDB: WeatherDB): WeatherDao {
        return weatherDB.weatherDao()
    }
}