package com.example.cesar_p2_ap2.ui.Gastos

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cesar_p2_ap2.data.remote.dto.GastoDto
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GastosScreen (
   gastos : List<GastoDto>
){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Gastos App", color = MaterialTheme.colorScheme.primary) },
            )
        },
        content = {
            Column {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 70.dp)
                ) {
                    items(gastos) { gastos ->
                        Divider()
                        RowItemForAnGasto(gasto = gastos)
                    }
                }
                //RowItemForAnGasto(gasto = GastoDto(idGasto = 1, concepto = "factura pasada", itbis = 1.00, monto = 10000.00, fecha = "10/10/2010", ncf = "009", suplidor = "Claro Dominicana" ))
            }
        }
    )
}


@Composable
fun MainScreen(
    viewModel : GastosViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    uiState.gastos?.let { gastos ->
        GastosScreen(gastos = gastos)
    }

}
@Composable
fun Register()
{
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(bottom = 10.dp, top=20.dp)
    ){
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
            ,modifier = Modifier
                .size(width = 350.dp, height = 315.dp)
                .padding(bottom = 5.dp),

            )
        {

        }
    }

}
@Composable
fun RowItemForAnGasto(gasto : GastoDto)
{
    Surface(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.onTertiary,
        shadowElevation = 5.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Text(text="ID: "+gasto.idGasto.toString())
                Text(text="fecha:"+ formatearFecha(gasto.fecha))
            }
            Row {
                Text(text = "Claro Dominiciana", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
            }
            Row{
                Text(text ="Concepto:"+gasto.concepto, maxLines = 2, overflow = TextOverflow.Ellipsis)
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column{
                    Text(text = "ncf:" + gasto.ncf)
                    Text(text = "Itbis:" + gasto.itbis)

                }
                Column() {
                    Text(text = "Monto:" + gasto.monto, color = MaterialTheme.colorScheme.error)
                }
            }
        }

    }
}
fun formatearFecha(fechaOriginal: String): String {
    val formatoOriginal = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
    val fechaParseada = LocalDateTime.parse(fechaOriginal, formatoOriginal)
    val formatoDeseado = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return fechaParseada.format(formatoDeseado)
}

fun main() {
    val fechaOriginal = "2023-11-08T23:40:08.747"
    val fechaFormateada = formatearFecha(fechaOriginal)
    println(fechaFormateada) // Imprime: 2023-11-08
}