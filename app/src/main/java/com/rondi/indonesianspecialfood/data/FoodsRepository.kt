package com.rondi.indonesianspecialfood.data

import com.rondi.indonesianspecialfood.model.Foods
import kotlinx.coroutines.flow.Flow

interface FoodsRepository {
    fun getFoods(): Flow<List<Foods>>

    fun getFoodsById(id: Int): Flow<Foods>

    fun getFavoriteFoods(): Flow<List<Foods>>

    fun searchFoods(query: String): Flow<List<Foods>>

    fun updateFoods(id: Int, newState: Boolean): Flow<Boolean>
}
