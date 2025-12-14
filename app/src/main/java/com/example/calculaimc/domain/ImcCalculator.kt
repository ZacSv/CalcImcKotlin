package com.example.calculaimc.domain

import kotlin.math.pow

object ImcCalculator {
    fun calcular(peso: Double, alturaEmCm: Double): Double {
        val alturaEmMetros = alturaEmCm / 100.0
        return if (alturaEmMetros > 0) {
            peso / alturaEmMetros.pow(2)
        } else {
            0.0
        }
    }

    fun classificar(imc: Double): String {
        return when {
            imc < 18.5 -> "Abaixo do peso"
            imc in 18.5 .. 24.9 -> "Peso Normal"
            imc in 25.0 .. 29.9 -> "Sobrepeso"
            imc in 30.0 .. 34.9 -> "Obesidade (Grau 1)"
            imc in 35.0 .. 39.9 -> "Obesidade Severa (Grau 2)"
            else -> "Obesidade Mórbida (Grau 3)"
        }
    }
}