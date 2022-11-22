package com.example.newsappdemo.di

import com.example.newsappdemo.data.repository.NewsRepositoryImpl
import com.example.newsappdemo.data.remote.NewsApiService
import com.example.newsappdemo.domain.repository.NewsRepository
import com.example.newsappdemo.domain.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNewsApi(): NewsApiService {
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit.create(NewsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(api: NewsApiService): NewsRepository {
        return NewsRepositoryImpl(api)
    }
}