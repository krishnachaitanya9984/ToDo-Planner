package com.krinyny.todoplanner.di

import android.content.Context
import androidx.room.Room
import com.krinyny.todoplanner.data.ToDoDatabase
import com.krinyny.todoplanner.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) : ToDoDatabase {
        return Room.databaseBuilder(
            context,
            ToDoDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideDAO(database: ToDoDatabase) = database.toDoDao()
}