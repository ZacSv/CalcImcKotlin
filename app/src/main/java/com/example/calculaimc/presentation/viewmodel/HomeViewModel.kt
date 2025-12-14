package com.example.calculaimc.presentation.viewmodel
import android.util.Log
import com.example.calculaimc.domain.HealthCalculator
import androidx.lifecycle.ViewModel
import com.example.calculaimc.domain.ImcCalculator
import com.example.calculaimc.domain.NivelAtividade
import com.example.calculaimc.domain.Sexo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import androidx.lifecycle.viewModelScope
import com.example.calculaimc.data.CalculationDao
import com.example.calculaimc.data.CalculationEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class HomeViewModel(private val dao: CalculationDao) : ViewModel() {

    //Expor a lista do banco como um Flow para a UI
    val historyList: Flow<List<CalculationEntity>> = dao.getAll()
    //Estado para guardar o item selecionado
    private val _selectedCalculation = MutableStateFlow<CalculationEntity?>(null)
    val selectedCalculation: StateFlow<CalculationEntity?> = _selectedCalculation.asStateFlow()

    /* GEMINI PRO - START
    Prompt:
    Perform a sanity check on this ViewModel snippet. The build is failing on these lines.
    Snippet:
        private val _uiState = MutableStateFlow(HomeUiState())
        val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow().
    Check for syntax errors, missing imports, or type inconsistencies within the variable. Correct the code while maintaining encapsulation.
    */
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    /* GEMINI PRO - END */

    // Função para atualizar o peso
    fun onPesoChanged(novoPeso: String) {
        if (novoPeso.length <= 7 && novoPeso.all { it.isDigit() }) {
            _uiState.update {
                it.copy(
                    peso = novoPeso,
                    pesoError = false //Se o usuário digitou algo, limpo o erro "vermelho".
                )
            }
        }
    }
    // Função para atualizar a altura
    fun onAlturaChanged(novaAltura: String) {
        if (novaAltura.length <= 3 && novaAltura.all { it.isDigit() }) {
            _uiState.update {
                it.copy(
                    altura = novaAltura,
                    alturaError = false
                )
            }
        }
    }
    // Função chamada pelo Botão Calcular
    fun calcular() {
        val peso = _uiState.value.peso
        val altura = _uiState.value.altura
        val state = _uiState.value
        val idade = state.idade

        //Validações de entrada
        val isPesoInvalid = peso.isBlank()
        val isAlturaInvalid = altura.isBlank()
        val isIdadeInvalid = idade.isBlank()
        if (isPesoInvalid || isAlturaInvalid || isIdadeInvalid) {
            _uiState.update {
                it.copy(
                    pesoError = isPesoInvalid,
                    alturaError = isAlturaInvalid,
                    idadeError = isIdadeInvalid
                )
            }
            return
        }
        //CONVERSÃO E CÁLCULO
        // Convertendo String para Double de forma segura e substituindo vírgula por ponto.
        val pesoDouble = peso.replace(",", ".").toDoubleOrNull() ?: 0.0
        val alturaDouble = altura.toDoubleOrNull() ?: 0.0
        val idadeInt = idade.toIntOrNull() ?: 0

        if (pesoDouble <= 0 || alturaDouble <= 0 || idadeInt <= 0) return

        // Chamando a camada de Domínio
        val imcCalculado = ImcCalculator.calcular(pesoDouble, alturaDouble)
        val classificacaoTexto = ImcCalculator.classificar(imcCalculado)

        // 2. NOVOS CÁLCULOS
        val tmb = HealthCalculator.calcularTMB(pesoDouble, alturaDouble, idadeInt, state.sexo)
        val pesoIdeal = HealthCalculator.calcularPesoIdeal(alturaDouble, state.sexo)
        val calorias = HealthCalculator.calcularNecessidadeCalorica(tmb, state.nivelAtividade)



        //ATUALIZAÇÃO DO RESULTADO NA TELA
        _uiState.update {
            it.copy(
                // Formatando para mostrar apenas 2 casas decimais
                resultadoImc = "IMC: %.2f".format(imcCalculado),
                classificacao = classificacaoTexto,
                resultadoTmb = "TMB: %.0f kcal".format(tmb),
                resultadoPesoIdeal = "Peso Ideal: %.1f kg".format(pesoIdeal),
                resultadoCalorias = "Necessidade: %.0f kcal/dia".format(calorias),
                //Remove erros da tela
                pesoError = false,
                alturaError = false,
                idadeError = false
            )
        }

        viewModelScope.launch {
            val entity = CalculationEntity(
                peso = pesoDouble,
                altura = alturaDouble,
                idade = idadeInt,
                sexo = state.sexo.name, // Convertendo Enum para String
                nivelAtividade = state.nivelAtividade.name,
                imc = imcCalculado,
                classificacaoImc = classificacaoTexto,
                tmb = tmb,
                pesoIdeal = pesoIdeal,
                caloriasDiarias = calorias
            )
            dao.insert(entity)
            Log.d("CalculaIMC", "Salvo no banco com sucesso!")
        }

    }
    // Função para buscar item pelo ID
    fun getCalculationById(id: Int) {
        viewModelScope.launch {
            dao.getById(id).collect { entity ->
                _selectedCalculation.value = entity
            }
        }
    }

    //Atualiza o valor da idade
    fun onIdadeChanged(novaIdade: String) {
        if (novaIdade.length <= 3 && novaIdade.all { char -> char.isDigit() }) {
            _uiState.update { it.copy(idade = novaIdade, idadeError = false) }
        }
    }
    //Atualiza o valor do sexo
    fun onSexoChanged(novoSexo: Sexo) {
        _uiState.update { it.copy(sexo = novoSexo) }
    }
    //Atualiza o valor da atividade
    fun onAtividadeChanged(novaAtividade: NivelAtividade) {
        _uiState.update { it.copy(nivelAtividade = novaAtividade) }
    }

}