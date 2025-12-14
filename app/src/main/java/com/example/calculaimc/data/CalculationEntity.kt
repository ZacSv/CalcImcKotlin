package com.example.calculaimc.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "calculation_history")
data class CalculationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // Dados de Entrada
    val peso: Double,
    val altura: Double,
    val idade: Int,
    val sexo: String,           // Salvaremos "MASCULINO" ou "FEMININO"
    val nivelAtividade: String, // Salvaremos "SEDENTARIO", etc.

    // Resultados
    val imc: Double,
    val classificacaoImc: String,
    val tmb: Double,
    val pesoIdeal: Double,
    val caloriasDiarias: Double,

    // Data (Timestamp)
    val dataCalculo: Long = System.currentTimeMillis()
)