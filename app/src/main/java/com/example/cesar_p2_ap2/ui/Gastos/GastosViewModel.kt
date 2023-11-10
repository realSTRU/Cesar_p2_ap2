package com.example.cesar_p2_ap2.ui.Gastos

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cesar_p2_ap2.data.remote.dto.GastoDto
import com.example.cesar_p2_ap2.data.repository.GastoRepository
import com.example.cesar_p2_ap2.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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

    //After realese the consult for register statements.
    //variables to UI modeling and do actions.
    var id by mutableStateOf(0)
    var fecha by mutableStateOf("")
    var concepto by mutableStateOf("")
    var suplidor by mutableStateOf("")
    var idSuplidor  by mutableStateOf("")
    var ncf by mutableStateOf("")
    var monto by mutableStateOf("")
    var itbis by mutableStateOf("")

    //for validations variables.
    var conceptoError by mutableStateOf(false)
    var ncfError by mutableStateOf(false)
    var itbisError by mutableStateOf(false)
    var montoError by mutableStateOf(false)
    var fechaError by mutableStateOf(false)
    var idSuplidorError by mutableStateOf(false)

    //When Statements changes and for set values.
    fun onConceptoChange(valor:String){
        concepto= valor
        conceptoError= valor.isNullOrBlank()
    }
    fun onFechaChange(valor:String){
        fecha=valor
        fechaError= valor.isNullOrBlank()
    }
    fun onIdForSuplidorChange(valor:String){
        idSuplidor= valor
        idSuplidorError= valor.isNullOrBlank()
    }
    fun onNcfChange(valor:String){
        ncf = valor
        ncfError= valor.isNullOrBlank()
    }
    fun onItbisChange(valor:String){
        itbis= valor
        itbisError= valor.isNullOrBlank()
    }
    fun onMontoChange(valor:String){
        monto = valor
        montoError= valor.isNullOrBlank()
    }



    private val _isMessageShown = MutableSharedFlow<Boolean>()
    val isMessageShownFlow = _isMessageShown.asSharedFlow()
    var message by mutableStateOf("")

    fun loadGastos(){
        repository.getGastos().onEach { result ->
            when(result)
            {
                is Resource.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }
                is Resource.Success -> {
                    _uiState.update { it.copy(gastos =  result.data ?: emptyList(), isLoading = false) }
                }
                is Resource.Error -> {
                    _uiState.update { it.copy(error = result.message ?: "Anonymous error") }
                }
            }
        }.launchIn(viewModelScope)
    }

    init{
        loadGastos()
    }

    fun validate() : Boolean{
        onFechaChange(fecha)
        onIdForSuplidorChange(idSuplidor)
        onConceptoChange(concepto)
        onNcfChange(ncf)
        onItbisChange(itbis)
        onMontoChange(monto)
        return fechaError || idSuplidorError || conceptoError || ncfError ||  itbisError || montoError

    }

    fun clean()
    {
        id=0
        fecha =""
        idSuplidor =""
        suplidor=""
        concepto =""
        ncf =""
        itbis =""
        monto =""


    }

    //General Operations
    fun saveGasto() {
        viewModelScope.launch {

            if (!validate()) {
                val gasto = GastoDto(
                    idGasto = id,
                    fecha=fecha,
                    idSuplidor= idSuplidor.toIntOrNull() ?:0,
                    suplidor="",
                    concepto=concepto,
                    ncf = ncf,
                    itbis = itbis.toDouble(),
                    monto = monto.toDouble()
                )

                if (id!=0) repository.putGasto(id = id, gasto = gasto)   else repository.postGasto(gasto)

                message="guardado con exito!"
                clean()
                loadGastos()

            } else {
                message="error"
            }
        }

    }
    fun deleteGastos(id: Int){
        viewModelScope.launch {
            repository.deleteGasto(id)

        }
        loadGastos()
    }
    fun updateTo( gasto:GastoDto){
        id= gasto.idGasto!!
        fecha = gasto.fecha.toString()
        idSuplidor =gasto.idSuplidor.toString()
        suplidor=gasto.suplidor.toString()
        concepto =gasto.concepto
        ncf =gasto.ncf.toString()
        itbis= gasto.itbis.toString()
        monto =gasto.monto.toString()


    }

}