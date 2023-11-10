package com.example.cesar_p2_ap2.ui.Gastos

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.material.icons.filled.SaveAs
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cesar_p2_ap2.data.remote.dto.GastoDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GastosScreen (
    viewModel : GastosViewModel = hiltViewModel()
   //gastos : List<GastoDto>
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.isMessageShownFlow.collectLatest {
            if (it) {
                snackbarHostState.showSnackbar(
                    message = viewModel.message,
                    duration = SnackbarDuration.Short
                )
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Gastos App", color = MaterialTheme.colorScheme.primary) },
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                Row (){
                    Text(text = "Gastos List",color= MaterialTheme.colorScheme.primary)
                    Icon(imageVector = Icons.Filled.PlaylistAdd, contentDescription = "List")
                }
                Divider()
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                ) {

                    when {
                        uiState.isLoading -> {
                            item {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                    }
                    uiState.gastos?.let {
                        items(uiState.gastos){gasto->
                            RowItemForAnGasto(gasto,viewModel)
                        }
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
        //GastosScreen(gastos = gastos)
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
fun RowItemForAnGasto(
    gasto : GastoDto,
    viewModel : GastosViewModel = hiltViewModel()
)
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


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun showDatePicker(
    context: Context,
    modifier: Modifier,
    gastosViewModel: GastosViewModel = hiltViewModel(),
) {

    val year: Int
    val month: Int
    val day: Int

    val calendar = Calendar.getInstance()
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()


    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            gastosViewModel.onFechaChange( "$dayOfMonth/$month/$year")
        }, year, month, day
    )
    OutlinedTextField(
        value = gastosViewModel.fecha,
        onValueChange = { },
        readOnly = true,
        modifier = modifier,
        isError = gastosViewModel.fechaError,
        leadingIcon = { IconButton(onClick = {
            datePickerDialog.show()
        }) {
            Icon(imageVector = Icons.Filled.DateRange, contentDescription ="date" )
        }
        },
        label = { Text("Ingrese Fecha") },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next)
    )

}