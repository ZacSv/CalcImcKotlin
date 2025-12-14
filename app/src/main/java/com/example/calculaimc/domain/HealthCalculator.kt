package com.example.calculaimc.domain

import kotlin.math.pow

enum class Sexo { MASCULINO, FEMININO }

enum class NivelAtividade(val fator: Double, val descricao: String) {
    SEDENTARIO(1.2, "Sedentário (Pouco ou nenhum exercício)"),
    LEVE(1.375, "Leve (Exercício 1-3 dias/semana)"),
    MODERADO(1.55, "Moderado (Exercício 3-5 dias/semana)"),
    INTENSO(1.725, "Intenso (Exercício 6-7 dias/semana)")
}


object HealthCalculator {

    // Fórmula de Mifflin-St Jeor (Considerada a mais precisa atualmente)
    fun calcularTMB(peso: Double, alturaCm: Double, idade: Int, sexo: Sexo): Double {
        val base = (10 * peso) + (6.25 * alturaCm) - (5 * idade)
        return if (sexo == Sexo.MASCULINO) {
            base + 5
        } else {
            base - 161
        }
    }

    // Fórmula de Devine
    fun calcularPesoIdeal(alturaCm: Double, sexo: Sexo): Double {
        // A fórmula original usa polegadas. 152.4 cm = 60 polegadas (5 pés)
        // Homens: 50kg + 2.3kg por polegada acima de 5 pés
        // Mulheres: 45.5kg + 2.3kg por polegada acima de 5 pés

        val alturaPolegadas = alturaCm / 2.54
        val polegadasAcima = alturaPolegadas - 60

        // Se for muito baixo (< 1.52m), a fórmula não se aplica bem, retornamos um base aproximado
        if (polegadasAcima <= 0) return if (sexo == Sexo.MASCULINO) 50.0 else 45.5

        val pesoBase = if (sexo == Sexo.MASCULINO) 50.0 else 45.5
        return pesoBase + (2.3 * polegadasAcima)
    }
    fun calcularNecessidadeCalorica(tmb: Double, nivelAtividade: NivelAtividade): Double {
        return tmb * nivelAtividade.fator
    }
}