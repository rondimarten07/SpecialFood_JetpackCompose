package com.rondi.indonesianspecialfood.data

import com.rondi.indonesianspecialfood.model.Foods
import com.rondi.indonesianspecialfood.model.FoodsData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodsRepositorySource @Inject constructor() : FoodsRepository {

    private val dummyFoods = mutableListOf<Foods>()

    init {
        if (dummyFoods.isEmpty()) {
            dummyFoods.addAll(FoodsData.dummyFoods)
        }
    }

    override fun getFoods() = flow {
        emit(dummyFoods)
    }

    override fun getFoodsById(id: Int): Flow<Foods> {
        return flowOf(dummyFoods.first { it.id == id })
    }

    override fun getFavoriteFoods(): Flow<List<Foods>> {
        return flowOf(dummyFoods.filter { it.isFavorite })
    }

    override fun searchFoods(query: String) = flow {
        val data = dummyFoods.filter {
            it.name.contains(query, ignoreCase = true)
        }
        emit(data)
    }

    override fun updateFoods(id: Int, newState: Boolean): Flow<Boolean> {
        val index = dummyFoods.indexOfFirst { it.id == id }
        val result = if (index >= 0) {
            val foods = dummyFoods[index]
            dummyFoods[index] = foods.copy(isFavorite = newState)
            true
        } else {
            false
        }
        return flowOf(result)
    }
}