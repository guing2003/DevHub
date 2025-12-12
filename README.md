# DevHub 📱

**DevHub** é um aplicativo Android desenvolvido para consumir a API pública do GitHub. O objetivo do app é permitir que os usuários visualizem perfis de desenvolvedores, exibindo informações como nome, username, biografia, foto de perfil e a lista de repositórios públicos.

O projeto está sendo construído seguindo as melhores práticas de desenvolvimento Android moderno, utilizando **Jetpack Compose** para a interface do usuário e uma arquitetura robusta baseada em **Clean Architecture** e **MVVM**.

---

## 🚀 Funcionalidades

- **Busca de Usuários**: Pesquise por desenvolvedores do GitHub.
- **Perfil do Usuário**: Exibe foto, nome, username e bio.
- **Lista de Repositórios**: Visualização dos repositórios públicos do usuário.
- **Detalhes do Repositórios**: Visualização de informações públicas do repositório.
- **Navegação Fluida**: Utilizando Navigation Compose.
- **Carregamento de Imagens**: Integração com Coil para carregar avatares.

---

## 🛠️ Tecnologias e Bibliotecas

O projeto utiliza as seguintes tecnologias:

- **Linguagem**: [Kotlin](https://kotlinlang.org/)
- **UI Toolkit**: [Jetpack Compose](https://developer.android.com/jetpack/compose)
- **Arquitetura**: Clean Architecture + MVVM (Model-View-ViewModel)
- **Injeção de Dependência**: Koin
- **Networking**: Retrofit
- **Carregamento de Imagens**: [Coil](https://coil-kt.github.io/coil/)
- **Armazenamento de dados**: DataStore(Shared Preferences) e Room Database
- **Navegação**: Navigation Compose
- **Testes**: JUnit, Espresso (Planejado)
- **CI/CD**: GitHub Actions (Pipelines de Integração Contínua)

---

## 🏗️ Arquitetura

O projeto segue os princípios da **Clean Architecture**, dividido em camadas para garantir separação de responsabilidades, testabilidade e manutenção:

1.  **Presentation (UI)**: Contém as Composables e ViewModels.
2.  **Domain**: Contém as regras de negócio, Interfaces de Repositório. É independente de frameworks.
3.  **Data**: Implementação dos Repositórios, Fontes de Dados (Data Sources - API/Local) e Mappers.

---

## 🔄 CI/CD

O projeto conta com pipelines de Integração Contínua (CI) configurados (via GitHub Actions) para garantir a qualidade do código a cada push ou Pull Request, executando:
- Build do projeto
- Verificação de Lint
- Testes Unitários

---

## 📦 Como rodar o projeto

1.  **Clone o repositório**:
    ```bash
    git clone https://github.com/seu-usuario/DevHub.git
    ```
2.  **Abra no Android Studio**:
    - Selecione `File > Open` e navegue até a pasta do projeto.
3.  **Sincronize o Gradle**:
    - Aguarde o download das dependências.
4.  **Execute o App**:
    - Conecte um dispositivo ou inicie um emulador e clique em "Run".

---

## 🤝 Contribuição

Sinta-se à vontade para abrir *issues* e enviar *pull requests*. Sugestões e feedbacks são sempre bem-vindos!

---

Desenvolvido por **Guilherme Delecrode** 🚀
