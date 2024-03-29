package ru.akumakeito.githubusers.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.akumakeito.githubusers.data.dao.AppDb
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideUserDao(appDb: AppDb) = appDb.usersDao()

    @Provides
    @Singleton
    fun provideAppDb(
        @ApplicationContext context: Context,
        gson: Gson
    ): AppDb = Room.databaseBuilder(
        context,
        AppDb::class.java,
        "app.db"
    )
        .fallbackToDestructiveMigration()
//        .addTypeConverter(Converters(gson))
        .build()
}