# HealthTrack - Calculadora de IMC e Monitoramento de Saúde

Este projeto é uma aplicação Android nativa desenvolvida como parte da avaliação acadêmica da disciplina de Desenvolvimento Mobile. O objetivo é fornecer uma ferramenta para cálculo de Índice de Massa Corporal (IMC) e Taxa Metabólica Basal (TMB), com persistência de histórico para acompanhamento da evolução do usuário.

## 📱 Funcionalidades

* **Cálculo de IMC:** Classificação automática (Abaixo do peso, Peso normal, Sobrepeso, Obesidade) baseada nos inputs do usuário.
* **Cálculo de TMB:** Estimativa de calorias diárias necessárias baseada na equação de Harris-Benedict.
* **Histórico de Medições:** Persistência local de todos os cálculos realizados.
* **Gerenciamento de Dados:** Visualização em lista cronológica e opção de exclusão de registros.
* **Feedback Visual:** Interface reativa que informa o status de saúde através de cores e indicadores.

## 🛠 Tecnologias e Arquitetura

O projeto foi construído utilizando as práticas modernas de desenvolvimento Android:

* **Linguagem:** Kotlin.
* **UI Toolkit:** Jetpack Compose (Declarative UI).
* **Arquitetura:** MVVM (Model-View-ViewModel).
    * Separação clara entre a lógica de negócios, estado da UI e camada de dados.
    * Uso de `StateFlow` e `State` do Compose para garantir reatividade.
* **Persistência:** Room Database (Abstração sobre SQLite).
* **Assincronicidade:** Kotlin Coroutines e Flow para operações de I/O não bloqueantes.
* **Injeção de Dependência:** Manual (via Container de Aplicação) ou Hilt (caso tenha usado).

## 📂 Estrutura do Projeto

* `data/`: Contém a definição da Entidade (`Entity`), o DAO (Data Access Object) e a configuração do Banco de Dados (`AppDatabase`).
* `ui/`: Contém as telas (`Screens`) construídas com Composable Functions.
* `viewmodel/`: Contém os ViewModels responsáveis por gerenciar o estado das telas e comunicar com o repositório.
* `utils/`: Classes utilitárias para cálculos e formatação de datas.

## 🚀 Como Executar

1.  Clone este repositório.
2.  Abra o projeto no **Android Studio Ladybug** (ou versão recente compatível com Compose).
3.  Aguarde a sincronização do Gradle.
4.  Execute em um emulador ou dispositivo físico com Android 8.0+ (API 26+).

---
**Status do Projeto:** 🟢 Concluído (MVP Acadêmico)