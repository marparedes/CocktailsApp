package com.marparedes.cocktailsapp.di

import com.marparedes.cocktailsapp.data.remote.api.CocktailService
import com.marparedes.cocktailsapp.data.respository.CocktailRepositoryImpl
import com.marparedes.cocktailsapp.domain.repository.CocktailRepository
import com.marparedes.cocktailsapp.common.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit() = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CocktailService::class.java)

    @Singleton
    @Provides
    fun provideRepository(cocktailService: CocktailService): CocktailRepository = CocktailRepositoryImpl(cocktailService)
}