package com.example.taller2app.application.data.di

import android.app.Application
import androidx.room.Room
import com.example.taller2app.application.data.AppDataBase
import com.example.taller2app.application.data.workList.WorkDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDataBase(app: Application): AppDataBase {
        return Room.databaseBuilder(
            app,
            AppDataBase::class.java,
            "app_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideWorkDao(appDataBase: AppDataBase): WorkDao {
        return appDataBase.workDao
    }

}