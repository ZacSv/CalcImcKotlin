package com.example.calculaimc.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.calculaimc.domain.NivelAtividade
import com.example.calculaimc.domain.Sexo

// Componente simples para selecionar Sexo
@Composable
fun SexoSelector(
    selectedSexo: Sexo,
    onSexoSelected: (Sexo) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Sexo:", fontWeight = FontWeight.Bold)
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = selectedSexo == Sexo.MASCULINO,
                onClick = { onSexoSelected(Sexo.MASCULINO) }
            )
            Text("Masc")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = selectedSexo == Sexo.FEMININO,
                onClick = { onSexoSelected(Sexo.FEMININO) }
            )
            Text("Fem")
        }
    }
}

// Componente para Atividade (Você pode melhorar usando um Dropdown depois)
@Composable
fun AtividadeSelector(
    selectedAtividade: NivelAtividade,
    onAtividadeSelected: (NivelAtividade) -> Unit
) {
    Column {
        Text("Nível de Atividade:", fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 8.dp))

        // Vamos usar botões simples ou Chips para facilitar por enquanto
        // No futuro, podemos mudar para ExposedDropdownMenuBox
        NivelAtividade.values().forEach { atividade ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onAtividadeSelected(atividade) }
                    .padding(4.dp)
            ) {
                RadioButton(
                    selected = (atividade == selectedAtividade),
                    onClick = { onAtividadeSelected(atividade) }
                )
                Text(
                    text = atividade.descricao,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}