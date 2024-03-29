package ru.akumakeito.githubusers.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.akumakeito.githubusers.network.GithubUsersApiService
import javax.inject.Singleton

const val BASE_URL = "https://api.github.com/"

@InstallIn(SingletonComponent::class)
@Module
interface ApiModule {
    @Provides
    @Singleton
    fun providesGson(): Gson = Gson()

//    @Provides
//    @Singleton
//    fun provideConverters(gson: Gson): Converters = Converters(gson)


    @Provides
    @Singleton
    fun providesOkHttp() = OkHttpClient.Builder()
        .build()


    @Provides
    fun provideProductApiService(gson: Gson): GithubUsersApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(GithubUsersApiService::class.java)
}