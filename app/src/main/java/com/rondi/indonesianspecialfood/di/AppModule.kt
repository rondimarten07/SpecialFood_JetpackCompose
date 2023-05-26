package com.rondi.indonesianspecialfood.di

import com.rondi.indonesianspecialfood.data.FoodsRepository
import com.rondi.indonesianspecialfood.data.FoodsRepositorySource
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
    fun provideFoodsRepository(foodsRepositorySource: FoodsRepositorySource): FoodsRepository {
        return foodsRepositorySource
    }
}
