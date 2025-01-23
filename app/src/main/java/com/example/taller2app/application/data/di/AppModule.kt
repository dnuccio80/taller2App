package com.example.taller2app.application.data.di

import android.app.Application
import androidx.room.Room
import com.example.taller2app.application.data.AppDataBase
import com.example.taller2app.application.data.paymentMethod.PaymentDao
import com.example.taller2app.application.data.workDone.WorkDoneDao
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

    @Provides
    @Singleton
    fun provideWorkDoneDao(appDataBase: AppDataBase): WorkDoneDao {
        return appDataBase.workDoneDao
    }

    @Provides
    @Singleton
    fun providePaymentDao(appDataBase: AppDataBase): PaymentDao {
        return appDataBase.paymentDao
    }

}