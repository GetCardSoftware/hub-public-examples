# Hub GetCard Pay Example

[![Kotlin](https://img.shields.io/badge/language-Kotlin-blue?logo=kotlin)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-purple?logo=jetpackcompose)](https://developer.android.com/jetpack/compose)
[![minSdk](https://img.shields.io/badge/minSdk-24-brightgreen)](https://developer.android.com/about/versions/6.0)
[![compileSdk](https://img.shields.io/badge/compileSdk-35-blueviolet)](https://developer.android.com/about/versions/14/get)

Este projeto é um exemplo completo, desenvolvido com **Kotlin** e **Jetpack Compose**, para
facilitar a integração com o aplicativo **GetCard Pay** por meio de **Intents**.

## 📦 Sobre o projeto

Este exemplo demonstra como realizar um pagamento e estornar a última transação utilizando o *
*GetCard Pay**, sem a necessidade de comunicação direta com servidores ou configurações de rede.
Toda a integração é feita por meio de **Intents**, de forma simples e direta.

É ideal para desenvolvedores que desejam integrar rapidamente o GetCard Pay em seus próprios
aplicativos.

## ✅ Pré-requisitos

- Android Studio
- SDK Android 23+
- **ScopeProvider** suporta:
    - `minSdk 24 (Android 7)`
- O app **GetCard Pay** instalado e **já configurado** no dispositivo

## 🚀 Como usar

1. Clone o repositório:

```bash
git clone git@github.com:GetCardSoftware/hub-public-examples.git
```

> O repositório contém múltiplos exemplos. Localize e abra a pasta deste exemplo no Android Studio.

2. Abra o projeto no Android Studio.

3. Execute o projeto em um dispositivo Android que tenha o **GetCard Pay instalado e configurado**.

4. Utilize os botões da tela inicial:

   * **"Iniciar Pagamento"**: envia uma requisição para o GetCard Pay iniciar uma transação.
   * **"Estornar Última Transação"**: envia uma requisição de estorno da transação anterior (caso exista).

## 📚 Leitura recomendada

Recomendamos explorar o código-fonte deste exemplo para entender como realizar a integração com o GetCard Pay via `Intent` de forma segura e reutilizável.

## 📖 Bibliotecas utilizadas

Este exemplo **não utiliza bibliotecas de provider** diretamente, apenas a API pública de integração via `Intent` com o GetCard Pay.

---

## 🛠 Suporte

Se tiver dúvidas, entre em contato com nossa equipe técnica ou consulte
a [documentação oficial](https://doc-hubpay.tefbr.com.br/).