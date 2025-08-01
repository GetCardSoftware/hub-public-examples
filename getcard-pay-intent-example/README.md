# Hub Providers Example

[![Kotlin](https://img.shields.io/badge/language-Kotlin-blue?logo=kotlin)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-purple?logo=jetpackcompose)](https://developer.android.com/jetpack/compose)
[![minSdk](https://img.shields.io/badge/minSdk-24-brightgreen)](https://developer.android.com/about/versions/14/get)
[![compileSdk](https://img.shields.io/badge/compileSdk-35-blueviolet)](https://developer.android.com/about/versions/14/get)


Este projeto é um exemplo completo, desenvolvido com **Kotlin** e **Jetpack Compose**, para facilitar a integração com as bibliotecas da **GetCard Providers**.

## 📦 Sobre o projeto

Este exemplo demonstra como iniciar pagamentos e estornar transações utilizando nossas bibliotecas. É uma ótima referência para desenvolvedores que desejam entender a integração passo a passo com nossos serviços.

## ✅ Pré-requisitos

- Android Studio
- SDK Android 23+
- Token de autenticação válido
- Configurações do servidor Scope (caso use o `ScopeProvider`)
- **ScopeProvider** suporta:
  - `minSdk 24 (Android 7)`

## 🚀 Como usar

1. Clone o repositório:

```bash
git clone git@github.com:GetCardSoftware/hub-public-examples.git
```

> O repositório contém múltiplos exemplos. Localize e abra a pasta deste exemplo no Android Studio.

2. Abra o projeto no Android Studio.

3. Configure o token de autenticação:

   No arquivo `Settings.kt`, defina o token recebido:

   ```kotlin
   const val AUTH_TOKEN = "SEU_TOKEN_DE_AUTENTICACAO"
   ```

4. Configure o servidor Scope (se aplicável):

   Ainda no `Settings.kt`, adicione as credenciais do seu ambiente:

   ```kotlin
   val PROVIDER_CONFIG = PaymentProviderConfig.builder()
       .setIp("SEU_IP")
       .setPort(0u) // Porta do Provider
       .setCompany("CODIGO_DA_EMPRESA")
       .setCompanyBranch("CODIGO_DA_FILIAL")
       .setTerminal("CODIGO_DO_TERMINAL/PDV")
       .build()
   ```

5. Execute o projeto e explore a integração!

   Use os botões "Iniciar Pagamento" e "Estornar Última Transação" para simular transações reais.

## 📚 Leitura recomendada

Analise o código fonte deste exemplo para compreender como as bibliotecas foram estruturadas e integradas. Isso irá te ajudar a replicar essa arquitetura na sua aplicação.

## 📖 Bibliotecas utilizadas

* [ScopeProvider](https://doc-hubpay.tefbr.com.br/providers/scope/2.3.x/intro)

---

## 🛠 Suporte

Se tiver dúvidas, entre em contato com nossa equipe técnica ou consulte a [documentação oficial](https://doc-hubpay.tefbr.com.br/).
