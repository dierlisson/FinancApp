# FinancApp 💰

![Kotlin](https://img.shields.io/badge/Kotlin-0095D5?&style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Compose-4285F4?&style=for-the-badge&logo=jetpackcompose&logoColor=white)
![Material 3](https://img.shields.io/badge/Material%203-757575?&style=for-the-badge&logo=materialdesign&logoColor=white)

## 📋 Sobre o Projeto

O **FinancApp** é um assistente de controle financeiro pessoal desenvolvido como parte do desafio técnico proposto pela [Nova Era Tech](https://escolanovaeratech.com.br/). 

O objetivo do aplicativo é permitir que o usuário gerencie sua saúde financeira de forma offline, registrando receitas, despesas e visualizando relatórios detalhados por categoria, tudo sob uma interface moderna baseada no Material Design 3. O projeto foca em escalabilidade através da Clean Architecture e reatividade com StateFlow.

---

## 📸 Demonstração

| Splash Screen | Dashboard (Resumo) | Lista de Transações | Nova Transação |
|---|---|---|---|
| <img  max-height="640" min-height="200" alt="splash" src="https://github.com/user-attachments/assets/855a12d9-3669-4e73-ab1e-13f248041def" /> | <img max-height="640" min-height="200" alt="resumo" src="https://github.com/user-attachments/assets/aa88f087-c982-4f21-80ae-5afa8879704e" /> | <img max-height="640" min-height="200" alt="transacoes" src="https://github.com/user-attachments/assets/e4f8486a-3151-4ced-ba47-6b6d102f22b0" /> | <img  width="240" min-height="200" alt="novatransacao" src="https://github.com/user-attachments/assets/6a6c8f9a-3b43-4b63-ab4c-109a6b01cb3d" /> |


---

## ✨ Features

- **Fluxo de Boas-vindas:** Splash Screen animada com identidade visual própria.
- **Dashboard Reativo:** Resumo visual de saldo total, receitas e despesas que se atualiza instantaneamente.
- **CRUD Completo:** Gestão total de transações (Criar, Listar, Editar e Excluir).
- **Filtros e Busca:** Filtragem rápida por tipo (Receita/Despesa) e busca em tempo real por descrição.
- **Relatórios por Categoria:** Análise detalhada de gastos e ganhos agrupados por categoria.
- **Persistência Local (Offline):** Banco de dados Room com suporte a múltiplas entidades e relacionamentos.
- **Exportação JSON:** Funcionalidade integrada para exportar o histórico de transações para compartilhamento.
- **Identidade Visual:** Ícone adaptativo (Adaptive Icon) e tema verde customizado conforme diretrizes de design.

---

## 🏗️ Arquitetura e Padrões

O projeto foi construído seguindo os princípios da **Clean Architecture** e o padrão de apresentação **MVVM (Model-View-ViewModel)**.

- **Domain Layer:** Contém os modelos de domínio puro (`Conta`, `Categoria`, `Transacao`) e a lógica de negócio, totalmente independente de bibliotecas de terceiros.
- **Data Layer:** Implementação do Room Database, DAOs e Repositories. Utiliza o padrão **Mapper** para converter entidades de banco de dados em modelos de domínio, garantindo o desacoplamento.
- **UI Layer:** Desenvolvida 100% com **Jetpack Compose**, utilizando **StateFlow** para garantir que a interface seja uma função do estado, facilitando testes e previsibilidade.

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Versão | Motivo |
|---|---|---|
| **Kotlin** | 2.1.0 | Linguagem base, permitindo código conciso e seguro (null-safety). |
| **Jetpack Compose** | Latest | Framework de UI declarativa moderno para Android. |
| **Room Database** | 2.7.0-alpha11 | Abstração sobre SQLite para persistência local robusta. |
| **Coroutines & Flow** | - | Tratamento de concorrência e fluxos de dados reativos assíncronos. |
| **Kotlinx Serialization** | 1.7.3 | Serialização eficiente de objetos para a exportação JSON. |
| **Navigation Compose** | 2.8.5 | Gerenciamento centralizado de rotas e navegação entre telas. |
| **Material Design 3** | - | Conjunto de componentes e estilos visuais modernos. |

---

## 🧠 Conceitos Demonstrados

- **SDD (Spec-Driven Development):** Desenvolvimento guiado rigorosamente pelos requisitos definidos no `AGENTS.md`.
- **Injeção de Dependência Manual:** Uso do padrão Singleton via classe `Application` para simplicidade e controle total do ciclo de vida.
- **TypeConverters:** Implementação para manipulação de tipos complexos como `Date` no banco de dados.
- **Migrations:** Gerenciamento de evolução de esquema (Schema) do banco de dados (ex: adição do campo `observacao`).
- **Adaptive Icons:** Criação de ícones que respeitam as diretrizes de design do Android (Foreground/Background layers).
- **Clean Code:** Aplicação de nomes semânticos, separação de responsabilidades e princípios SOLID.

---

## 🚀 Como Rodar Localmente

### Pré-requisitos
- Android Studio Ladybug (ou versão superior).
- JDK 11 ou superior.
- Conexão com internet para download das dependências Gradle.

### Passo a Passo
1. **Clone o repositório:**
   ```bash
   git clone https://github.com/dierlisson/FinancApp.git
   ```
2. **Abra o projeto:**
   Inicie o Android Studio e selecione a pasta do projeto clonado.
3. **Sincronize o Gradle:**
   Aguarde a finalização do processo de sincronização (Build > Sync Project with Gradle Files).
4. **Execute o App:**
   Selecione um emulador ou dispositivo físico conectado (API 24+) e clique no botão **Run** (ícone de play verde).

---

## 👤 Autor

Desenvolvido por **Dierlisson Justiniano**.

