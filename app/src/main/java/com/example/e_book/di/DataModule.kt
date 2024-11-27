package com.example.e_book.di

import android.app.Application
import androidx.room.Room
import com.example.e_book.DATABASE_NAME
import com.example.e_book.data.database.BookMarkDatabase
import com.example.e_book.data.repo.Repo
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideFirebaseRealTimeDatabase(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }

    @Provides
    @Singleton
    fun provideRepo(firebaseDatabase: FirebaseDatabase,database: BookMarkDatabase) :Repo {
        return Repo(firebaseDatabase = firebaseDatabase,database)
    }

    @Provides
    @Singleton
    fun provideBookMarkDatabase(application: Application): BookMarkDatabase {
        return Room
            .databaseBuilder(application,BookMarkDatabase::class.java, DATABASE_NAME).fallbackToDestructiveMigration()
            .build()
    }
}