package com.example.calculaimc.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculaimc.presentation.viewmodel.HomeViewModel


/* GEMINI PRO - START
 Prompt:
 Act as a Mobile UI/UX Specialist using Jetpack Compose. Create a Composable called `ResultDetailScreen` that serves to detail a specific historical record.
Functional Requirements:
    Receive as parameters a `record` object: `HealthRecord?` and a `onBackClick` lambda: `() -> Unit`.
    The layout should be a Scaffold with a `TopAppBar` containing the back button and the title 'Record Details'.
Visual Hierarchy:
    Top (Highlight): A large, centered Card showing the BMI, Classification (text), and BMR. Use large, bold fonts for these numbers.
    Middle (Grid or Row): Smaller Cards or a Row showing the input data that generated that result: Weight, Height, and Age.
    Footer: The date the record was created.
Technical Requirements:
    Use `Column` with `Arrangement.spacedBy` for consistent spacing.
    Use Material3 components (ElevatedCard, Text with MaterialTheme.typography).
    Handle the case where a record might be null (displaying a loading or error message), although ideally it shouldn't be.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultDetailScreen(
    viewModel: HomeViewModel,
    resultId: Int,
    onBack: () -> Unit
) {
    // Busca o item específico quando a tela abre
    LaunchedEffect(resultId) {
        viewModel.getCalculationById(resultId)
    }

    val selectedItem by viewModel.selectedCalculation.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhes da Medição") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            selectedItem?.let { item ->
                DetailRow("Data", java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(java.util.Date(item.dataCalculo)))
                HorizontalDivider()
                DetailRow("Peso", "${item.peso} kg")
                DetailRow("Altura", "${item.altura} cm")
                DetailRow("Idade", "${item.idade} anos")
                DetailRow("Sexo", item.sexo)
                DetailRow("Atividade", item.nivelAtividade)
                HorizontalDivider()
                DetailRow("IMC", "%.2f".format(item.imc), isBold = true)
                Text(item.classificacaoImc, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                DetailRow("TMB", "%.0f kcal".format(item.tmb))
                DetailRow("Peso Ideal", "%.1f kg".format(item.pesoIdeal))
                DetailRow("Calorias Diárias", "%.0f".format(item.caloriasDiarias))
            } ?: Text("Carregando...")
        }
    }
}

@Composable
fun DetailRow(label: String, value: String, isBold: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = 16.sp)
        Text(value, fontSize = 16.sp, fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal)
    }
}

/* GEMINI PRO - END */