package com.example.calculaimc.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculaimc.data.CalculationEntity
import com.example.calculaimc.presentation.viewmodel.HomeViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


/* GEMINI PRO - START
 Prompt:
 Act as an Android Jetpack Compose Specialist. Create a Composable called HistoryScreen for a health app.
Data Context: There is a data class HealthRecord(id: Int, weight: Double, height: Double, bmi: Double, date: Long).
Functional Requirements:
Receive the following parameters:
    state: A list of HealthRecord (can be empty).
    onDeleteClick: A lambda (HealthRecord) -> Unit.
    onDetailClick: A lambda (Int) -> Unit (passing the ID).
    If the list is empty, display a friendly message and an icon in the center of the screen.
    If there is data, display a LazyColumn.
    Each item in the list should be a Card (Material3) containing:
    The formatted date (e.g., dd/MM/yyyy) highlighted.
    The BMI and Weight values.
    A trash can button/icon to delete the item. Use conditional colors for the BMI value in the Card (e.g., green if it's between 18.5 and 24.9, red otherwise).
Technical Requirements:
    Use Material 3.
    The layout should be elegant and with adequate padding.
    The code should only be for the UI (Stateless), do not include ViewModels or Rooms, only Composables
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    viewModel: HomeViewModel,
    onBack: () -> Unit,
    onItemClick: (Int) -> Unit
) {

    val historyList by viewModel.historyList.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Histórico") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Com o import 'androidx.compose.foundation.lazy.items',
            // 'item' será corretamente identificado como 'CalculationEntity'
            items(historyList) { item ->
                HistoryItemCard(item = item, onClick = { onItemClick(item.id) })
            }
        }
    }
}

@Composable
fun HistoryItemCard(item: CalculationEntity, onClick: () -> Unit) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "IMC: %.2f".format(item.imc),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = dateFormat.format(Date(item.dataCalculo)),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Text(text = item.classificacaoImc, color = Color.Gray)
        }
    }
}

/** GEMINI PRO - END */