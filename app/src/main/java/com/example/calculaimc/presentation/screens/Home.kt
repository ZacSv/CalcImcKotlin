package com.example.calculaimc.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculaimc.domain.NivelAtividade
import com.example.calculaimc.domain.Sexo
import com.example.calculaimc.presentation.viewmodel.HomeUiState
import com.example.calculaimc.presentation.viewmodel.HomeViewModel
import com.example.calculaimc.ui.theme.Blue
import com.example.calculaimc.ui.theme.Red
import com.example.calculaimc.ui.theme.White

// 1. A FUNÇÃO HOME "INTELIGENTE" (Atualizada)
@Composable
fun Home(viewModel: HomeViewModel = viewModel()) {
    val state by viewModel.uiState.collectAsState()

    HomeContent(
        state = state,
        onAlturaChanged = { viewModel.onAlturaChanged(it) },
        onPesoChanged = { viewModel.onPesoChanged(it) },
        // NOVAS CONEXÕES AQUI:
        onIdadeChanged = { viewModel.onIdadeChanged(it) },
        onSexoChanged = { viewModel.onSexoChanged(it) },
        onAtividadeChanged = { viewModel.onAtividadeChanged(it) },
        onCalcular = { viewModel.calcular() }
    )
}

// 2. A FUNÇÃO DE CONTEÚDO (Visual)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    state: HomeUiState,
    onAlturaChanged: (String) -> Unit,
    onPesoChanged: (String) -> Unit,
    // Novos Parâmetros Visuais
    onIdadeChanged: (String) -> Unit,
    onSexoChanged: (Sexo) -> Unit,
    onAtividadeChanged: (NivelAtividade) -> Unit,
    onCalcular: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Calculadora de Saúde") }, // Atualizei o título
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Blue,
                    titleContentColor = White
                )
            )
        }
    ) { paddingValues: PaddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(color = White)
                .verticalScroll(rememberScrollState()) // Scroll é essencial agora!
        ) {
            // --- GRUPO 1: PESO E ALTURA ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = state.altura,
                    onValueChange = onAlturaChanged,
                    modifier = Modifier
                        .weight(1f)
                        .padding(20.dp, 10.dp, 10.dp, 0.dp),
                    label = { Text("Altura (cm)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = White,
                        unfocusedContainerColor = White,
                        focusedLabelColor = Blue,
                        focusedIndicatorColor = Blue,
                        cursorColor = Blue,
                        errorContainerColor = White,
                    ),
                    isError = state.alturaError,
                    supportingText = if (state.alturaError) {
                        { Text("Obrigatório", color = Color.Red) }
                    } else null
                )

                OutlinedTextField(
                    value = state.peso,
                    onValueChange = onPesoChanged,
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp, 10.dp, 20.dp, 0.dp),
                    label = { Text("Peso (kg)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = White,
                        unfocusedContainerColor = White,
                        focusedLabelColor = Blue,
                        focusedIndicatorColor = Blue,
                        cursorColor = Blue,
                        errorContainerColor = White,
                    ),
                    isError = state.pesoError,
                    supportingText = if (state.pesoError) {
                        { Text("Obrigatório", color = Color.Red) }
                    } else null
                )
            }



            /* GEMINI PRO - START
            Prompt:
            Here's the code for the measurement screen. Currently, it only has the Weight and Height inputs.
            Do the manual work for me: add the Age field (numeric) right below and, subsequently, a Gender selector (Male/Female).
            You can use a simple Row RadioButton to handle the gender. Keep the same OutlinedTextField and spacing pattern I used in the inputs above. Just generate the updated Composable.
            */

            /*Isac Silva Vieira - INÍCIO
            Razão: Por questões de aderência, escrevo os prompts em inglês, mas como o projeto está em "português"
            fiz a alteração dos comentários e nome das variáveis
            */
            // --- GRUPO 2: IDADE ---
            OutlinedTextField(
                value = state.idade,
                onValueChange = onIdadeChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 10.dp, 20.dp, 0.dp),
                label = { Text("Idade (anos)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = White,
                    unfocusedContainerColor = White,
                    focusedLabelColor = Blue,
                    focusedIndicatorColor = Blue,
                    cursorColor = Blue,
                    errorContainerColor = White,
                ),
                isError = state.idadeError,
                supportingText = if (state.idadeError) {
                    { Text("Obrigatório", color = Color.Red) }
                } else null
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- GRUPO 3: SELETORES ---
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                SexoSelector(
                    selectedSexo = state.sexo,
                    onSexoSelected = onSexoChanged
                )

                Spacer(modifier = Modifier.height(16.dp))

                AtividadeSelector(
                    selectedAtividade = state.nivelAtividade,
                    onAtividadeSelected = onAtividadeChanged
                )
            }

            /* GEMINI PRO - END */
            /* Isac Silva Vieira - END */
            // --- BOTÃO CALCULAR ---
            Button(
                onClick = {
                    keyboardController?.hide()
                    onCalcular()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Blue),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp) // Diminuí um pouco a altura para caber tudo
                    .padding(vertical = 30.dp, horizontal = 50.dp)
            ) {
                Text(
                    text = "CALCULAR TUDO",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = White
                )
            }


            /* GEMINI PRO - START
                Prompt:
                    Now, create the UI for displaying the results.
                    Create a Card that only renders if the state.resultadoImc is not empty. You can use a light blue background (0xFFE3F2FD) to highlight it.
                    Inside, list the fields: BMI, Classification, Ideal Weight, BMR, and Daily Calories. Use some spacing and dividers to organize them.
                    Note: To avoid a lot of repeated Rows cluttering the code, extract the logic for displaying 'Label + Value' to an auxiliary component called ResultRow (using SpaceBetween) and use it inside the Card.
               */

            /*Isac Silva Vieira - INÍCIO
            Razão: Por questões de aderência, escrevo os prompts em inglês, mas como o projeto está em "português"
            fiz a alteração dos comentários e nome das variáveis
            */


            // --- RESULTADOS (CARD) ---
            if (state.resultadoImc.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)), // Azul bem clarinho
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Resultados:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Blue
                        )
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Blue)

                        ResultRow("IMC:", state.resultadoImc)
                        Text(text = state.classificacao, fontWeight = FontWeight.Bold, color = Blue)

                        Spacer(modifier = Modifier.height(8.dp))
                        ResultRow("Peso Ideal:", state.resultadoPesoIdeal)
                        ResultRow("Taxa Metabólica:", state.resultadoTmb)

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Calorias Diárias:", fontWeight = FontWeight.Bold)
                        Text(
                            text = state.resultadoCalorias,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Blue
                        )
                    }
                }
            }
            // Espaço extra no final para o scroll não cortar nada
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}

// --- COMPONENTES AUXILIARES ---

@Composable
fun ResultRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label)
        Text(text = value, fontWeight = FontWeight.Bold)
    }
}

                /* GEMINI PRO - END */
                /* Isac Silva Vieira - END */

// --- PREVIEW ---
@Preview
@Composable
private fun HomePreview() {
    val fakeState = HomeUiState(
        altura = "180",
        peso = "80",
        idade = "25",
        resultadoImc = "IMC: 24.69",
        classificacao = "TESTE",
        resultadoTmb = "TMB: 1800 kcal",
        resultadoPesoIdeal = "Peso Ideal: 75 kg",
        resultadoCalorias = "Necessidade: 2500 kcal/dia"
    )

    HomeContent(
        state = fakeState,
        onAlturaChanged = {},
        onPesoChanged = {},
        onIdadeChanged = {},
        onSexoChanged = {},
        onAtividadeChanged = {},
        onCalcular = {}
    )
}