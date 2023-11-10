package com.example.cesar_p2_ap2.ui.Gastos


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cesar_p2_ap2.data.remote.dto.SuplidorDto
import com.example.cesar_p2_ap2.data.repository.SuplidorRepository
import com.example.cesar_p2_ap2.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

data class SuplidoresListState(
    val isLoading: Boolean = false,
    val suplidores: List<SuplidorDto> = emptyList(),
    val error: String = ""
)

@HiltViewModel
class SuplidoresViewModel @Inject constructor(
    private val  repository: SuplidorRepository

) : ViewModel() {
    private val _uiStateSuplidores = MutableStateFlow(SuplidoresListState())
    val stateSuplidores: StateFlow<SuplidoresListState> = _uiStateSuplidores.asStateFlow()
    init {
        load()
    }
    fun load(){

        repository.getSuplidores().onEach{ result ->
            when (result) {
                is Resource.Loading -> {
                    _uiStateSuplidores.value = SuplidoresListState(isLoading = true)
                }
                is Resource.Success -> {
                    _uiStateSuplidores.value = SuplidoresListState(suplidores = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _uiStateSuplidores.value = SuplidoresListState(error = result.message ?: "Anonymous Error")
                }

                else -> {}
            }
        }.launchIn(viewModelScope)


    }


}