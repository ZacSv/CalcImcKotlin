package com.example.calculaimc.presentation.viewmodel

import com.example.calculaimc.domain.NivelAtividade
import com.example.calculaimc.domain.Sexo

// Servirá apenas para carregar dados
data class HomeUiState(

    //Inputs relativos ao IMC
    val peso: String = "",
    val altura: String = "",
    val resultadoImc: String = "",
    val classificacao: String = "",

    //Inputs realtivos as novas features
    val idade: String = "",
    val sexo: Sexo = Sexo.MASCULINO, // Valor padrão
    val nivelAtividade: NivelAtividade = NivelAtividade.SEDENTARIO,
    val resultadoTmb: String = "",
    val resultadoPesoIdeal: String = "",
    val resultadoCalorias: String = "",

    //Inputs de erro
    val pesoError: Boolean = false,
    val alturaError: Boolean = false,
    val idadeError: Boolean = false
)