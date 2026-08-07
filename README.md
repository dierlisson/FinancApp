# FinancApp 💰

Aplicativo Android de controle financeiro pessoal offline, desenvolvido com Kotlin, Jetpack Compose, MVVM e Clean Architecture.

![Kotlin](https://img.shields.io/badge/Kotlin-0095D5?style=for-the-badge\&logo=kotlin\&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Compose-4285F4?style=for-the-badge\&logo=jetpackcompose\&logoColor=white)
![Material 3](https://img.shields.io/badge/Material%203-757575?style=for-the-badge\&logo=materialdesign\&logoColor=white)

## 📋 Sobre o projeto

O **FinancApp** é um aplicativo de controle financeiro pessoal desenvolvido como parte de um desafio técnico proposto pela [Nova Era Tech](https://escolanovaeratech.com.br/).

O aplicativo permite registrar receitas e despesas, acompanhar o saldo disponível e visualizar relatórios financeiros organizados por categoria. Todas as informações são armazenadas localmente, permitindo que as principais funcionalidades sejam utilizadas sem conexão com a internet.

O projeto foi construído com foco em organização de código, separação de responsabilidades, escalabilidade e gerenciamento reativo do estado da interface.

---

## 📸 Demonstração

| Splash Screen                                                                                                                                       | Dashboard                                                                                                                                       | Lista de transações                                                                                                                                       | Nova transação                                                                                                                                  |
| --------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------- |
| <img max-height="640" min-height="200" alt="Splash Screen" src="https://github.com/user-attachments/assets/855a12d9-3669-4e73-ab1e-13f248041def" /> | <img max-height="640" min-height="200" alt="Dashboard" src="https://github.com/user-attachments/assets/aa88f087-c982-4f21-80ae-5afa8879704e" /> | <img max-height="640" min-height="200" alt="Lista de transações" src="https://github.com/user-attachments/assets/e4f8486a-3151-4ced-ba47-6b6d102f22b0" /> | <img width="240" min-height="200" alt="Nova transação" src="https://github.com/user-attachments/assets/6a6c8f9a-3b43-4b63-ab4c-109a6b01cb3d" /> |

---

## ✨ Funcionalidades

* **Fluxo de boas-vindas:** Splash Screen animada com identidade visual própria.
* **Dashboard reativo:** Exibição do saldo total, das receitas e das despesas, com atualização automática dos valores.
* **Gerenciamento de transações:** Cadastro, consulta, edição e exclusão de receitas e despesas.
* **Filtros por tipo:** Visualização de todas as transações, somente receitas ou somente despesas.
* **Busca em tempo real:** Pesquisa de transações por descrição.
* **Relatórios por categoria:** Agrupamento de receitas e despesas para facilitar a análise financeira.
* **Persistência offline:** Armazenamento local das informações com Room Database.
* **Exportação em JSON:** Exportação do histórico de transações para compartilhamento.
* **Identidade visual personalizada:** Tema próprio, Material Design 3 e ícone adaptativo.

---

## 🏗️ Arquitetura

O projeto utiliza o padrão de apresentação **MVVM — Model-View-ViewModel**, combinado com princípios da **Clean Architecture**.

A aplicação está organizada em camadas com responsabilidades bem definidas:

### Domain Layer

Contém os modelos e regras de negócio da aplicação, como:

* `Conta`
* `Categoria`
* `Transacao`

Essa camada permanece independente dos detalhes de interface e persistência.

### Data Layer

Responsável pelo acesso e armazenamento dos dados, incluindo:

* Room Database;
* entidades do banco de dados;
* DAOs;
* implementações dos repositórios;
* conversão entre entidades e modelos de domínio por meio de Mappers.

### UI Layer

Desenvolvida com **Jetpack Compose**, contém:

* telas;
* componentes reutilizáveis;
* ViewModels;
* estados da interface;
* navegação entre as funcionalidades.

Os dados da interface são observados por meio de **StateFlow**, permitindo atualizações reativas e previsíveis.

---

## 🛠️ Tecnologias utilizadas

| Tecnologia                           | Aplicação no projeto                                            |
| ------------------------------------ | --------------------------------------------------------------- |
| **Kotlin 2.1.0**                     | Linguagem principal utilizada no desenvolvimento do aplicativo. |
| **Jetpack Compose — BOM 2024.12.01** | Construção declarativa das telas e componentes da interface.    |
| **Material Design 3**                | Componentes visuais, tema e padrões de interface.               |
| **Room Database 2.7.0-alpha11**      | Persistência local de contas, categorias e transações.          |
| **Navigation Compose 2.8.5**         | Gerenciamento das rotas e da navegação entre telas.             |
| **Kotlin Coroutines e Flow**         | Execução assíncrona e observação reativa dos dados.             |
| **StateFlow**                        | Representação e atualização dos estados da interface.           |
| **Kotlinx Serialization 1.7.3**      | Conversão dos dados para o formato JSON durante a exportação.   |
| **KSP 2.1.0-1.0.29**                 | Processamento das anotações utilizadas pelo Room.               |
| **Gradle 9.3.1**                     | Automação de build e gerenciamento do projeto.                  |

---

## 🧠 Conceitos aplicados

* MVVM;
* Clean Architecture;
* Repository Pattern;
* separação de responsabilidades;
* gerenciamento reativo de estado;
* persistência local;
* programação assíncrona;
* mapeamento entre entidades e modelos de domínio;
* injeção manual de dependências;
* Single Source of Truth;
* TypeConverters;
* migrações de banco de dados;
* componentes reutilizáveis com Jetpack Compose;
* princípios de Clean Code e SOLID.

---

## 🚧 Desafios e aprendizados

Durante o desenvolvimento do FinancApp, alguns dos principais desafios foram:

* **Modelar os dados financeiros:** estruturar contas, categorias e transações de maneira que as entidades do banco de dados não ficassem diretamente acopladas aos modelos de domínio.
* **Manter o dashboard sincronizado:** garantir que saldo, receitas, despesas e relatórios fossem atualizados automaticamente após cada alteração realizada pelo usuário.
* **Gerenciar estados da interface:** utilizar StateFlow para representar os dados e manter a interface consistente durante as operações.
* **Implementar filtros e busca reativos:** combinar o termo pesquisado e o tipo de transação selecionado sem duplicar regras de negócio.
* **Evoluir o banco de dados:** aplicar migrações para preservar os dados existentes após alterações no esquema, como a inclusão de novos campos.
* **Exportar informações em JSON:** transformar os modelos da aplicação em dados serializáveis e preparar o conteúdo para compartilhamento.
* **Organizar as dependências:** utilizar uma implementação manual de injeção de dependências, mantendo o controle sobre a criação do banco de dados e dos repositórios.

O projeto permitiu aprofundar conhecimentos em arquitetura Android, persistência local, fluxo reativo de dados, gerenciamento de estado e construção de interfaces modernas com Jetpack Compose.

---

## 🚀 Como executar o projeto

### Pré-requisitos

* Android Studio compatível com o Android Gradle Plugin utilizado pelo projeto;
* Android SDK instalado;
* JDK configurado no Android Studio;
* emulador Android ou dispositivo físico;
* conexão com a internet para baixar as dependências do Gradle.

O aplicativo possui suporte mínimo ao **Android 7.0 — API 24**.

### Passo a passo

1. Clone o repositório:

```bash
git clone https://github.com/dierlisson/FinancApp.git
```

2. Acesse a pasta do projeto:

```bash
cd FinancApp
```

3. Abra a pasta no Android Studio.

4. Aguarde a sincronização das dependências do Gradle.

5. Selecione um emulador ou dispositivo físico com API 24 ou superior.

6. Execute o aplicativo pelo botão **Run** do Android Studio.

---

## 👤 Autor

Desenvolvido por **Dierlisson Justiniano**.

* [LinkedIn](https://www.linkedin.com/in/dierlissonjustiniano/)
* [GitHub](https://github.com/dierlisson)
