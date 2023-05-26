package com.rondi.indonesianspecialfood.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rondi.indonesianspecialfood.data.FoodsRepository
import com.rondi.indonesianspecialfood.model.Foods
import com.rondi.indonesianspecialfood.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val foodsRepository: FoodsRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<Foods>> = MutableStateFlow(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getFoodsById(id: Int) = viewModelScope.launch {
        foodsRepository.getFoodsById(id)
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }

    fun updateFoods(id: Int, newState: Boolean) = viewModelScope.launch {
        foodsRepository.updateFoods(id, !newState)
            .collect { isUpdated ->
                if (isUpdated) getFoodsById(id)
            }
    }
}