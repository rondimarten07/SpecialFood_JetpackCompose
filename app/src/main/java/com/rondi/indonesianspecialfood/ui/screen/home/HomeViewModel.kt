package com.rondi.indonesianspecialfood.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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
class HomeViewModel @Inject constructor(
    private val foodsRepository: FoodsRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<Foods>>> = MutableStateFlow(UiState.Loading)
    val uiState get() = _uiState.asStateFlow()

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) = viewModelScope.launch {
        _query.value = newQuery
        foodsRepository.searchFoods(_query.value)
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }

    fun updateFoods(id: Int, newState: Boolean) = viewModelScope.launch {
        foodsRepository.updateFoods(id, newState)
            .collect { isUpdated ->
                if (isUpdated) search(_query.value)
            }
    }
}