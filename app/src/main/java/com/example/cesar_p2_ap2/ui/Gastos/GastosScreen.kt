package com.example.cesar_p2_ap2.ui.Gastos

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.SaveAs
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
    ElevatedCard(

        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        elevation = CardDefaults.elevatedCardElevation()

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        )
        {
            val parseDate = LocalDateTime.parse(gasto.fecha, DateTimeFormatter.ISO_DATE_TIME)
            val formatedDate = parseDate.format(DateTimeFormatter.ISO_DATE)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            )
            {
                Text(text="ID:${gasto.idGasto}"
                    ,style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(3f)
                )
                Spacer(modifier = Modifier.width(46.dp))
                Text(text="Fecha: $formatedDate",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(4f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        "${gasto.suplidor}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )


                    Text(
                        "${gasto.concepto}",
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )


            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("NCF: ${gasto.ncf}", style = MaterialTheme.typography.bodyMedium)
                    Text(
                        "ITBIS: ${gasto.itbis}", style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    "$${gasto.monto}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error
                )
            }
            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                ElevatedButton(
                    onClick = { },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 4.dp)
                ) {
                    Text("Modificar")
                }
                OutlinedButton(
                    onClick = { },
                    modifier = Modifier
                        .weight(1.1f)
                        .padding(start = 4.dp),
                    //colors = ButtonDefaults.buttonColors(Color.Red)
                ) {
                    Icon(imageVector = Icons.Default.DeleteOutline, contentDescription = "Delete")
                    Text("Eliminar")
                }
            }
        }

    }
}
