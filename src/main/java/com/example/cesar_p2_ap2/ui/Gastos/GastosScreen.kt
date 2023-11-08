package com.example.cesar_p2_ap2.ui.Gastos

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cesar_p2_ap2.data.remote.dto.GastoDto
import dagger.hilt.android.lifecycle.HiltViewModel


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GastosScreen (
    viewModel : GastosViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
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
                    items(uiState.gastos) { gasto ->
                        Divider()
                        RowItemForAnGasto(gasto = gasto)
                    }
                }
            }
        }
    )
}


@Composable
fun RowItemForAnGasto(gasto : GastoDto)
{
    Surface(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.onBackground,
        shadowElevation = 5.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column() {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Text(text="ID: "+gasto.idGasto.toString())
                Text(text="fecha:"+gasto.fecha)
            }
        }
        Divider()
    }
}