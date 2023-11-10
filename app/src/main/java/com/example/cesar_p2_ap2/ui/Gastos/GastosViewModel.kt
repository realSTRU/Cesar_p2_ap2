package com.example.cesar_p2_ap2.ui.Gastos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cesar_p2_ap2.data.remote.dto.GastoDto
import com.example.cesar_p2_ap2.data.repository.GastoRepository
import com.example.cesar_p2_ap2.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject


data class GastoListState(
    val isLoading : Boolean = false,
    val gastos: List<GastoDto> = emptyList(),
    val error : String = ""
)

@HiltViewModel
class GastosViewModel @Inject constructor(
    private val repository: GastoRepository
): ViewModel() {
    private val _uiState =  MutableStateFlow(GastoListState())
    val uiState : StateFlow<GastoListState> = _uiState.asStateFlow()

    init{
        repository.getGastos().onEach { result ->
            when(result)
            {
                is Resource.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }
                is Resource.Success -> {
                    _uiState.update { it.copy(gastos =  result.data ?: emptyList()) }
                }
                is Resource.Error -> {
                    _uiState.update { it.copy(error = result.message ?: "Anonymous error") }
                }
            }
        }.launchIn(viewModelScope)
    }

}