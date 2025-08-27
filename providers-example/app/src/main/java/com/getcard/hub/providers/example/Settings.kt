package com.getcard.hub.providers.example

import com.getcard.hubinterface.config.PaymentProviderConfig

class Settings {
    companion object {
        /**
         * Insira aqui o seu token de autenticação obtido com o suporte da GetCard
         */
        const val AUTH_TOKEN =
            "SEU_TOKEN_DE_AUTENTICACAO"

        /**
         * Defina suas configurações aqui.
         * As configurações variam de acordo o provider utilizado.
         * Para saber quais configurações devem ser definidas, acesse a nossa
         * documentação, escolha o provider desejado e clique em
         * 'Configurações'
         * Exemplo de configurações do Scope:
         * https://doc-hubpay.tefbr.com.br/providers/scope/2.3.x/configuration
         *
         * PS: As configurações abaixo foram definidas para o Scope Provider,
         * elas são referentes ao servidor do Scope e não ao Provider em sí.
         */
        val PROVIDER_CONFIG = PaymentProviderConfig.builder()
            .setIp("SEU_IP")
            .setPort(0u) // Porta do Provider
            .setCompany("CODIGO_DA_EMPRESA")
            .setCompanyBranch("CODIGO_DA_FILIAL")
            .setTerminal("CODIGO_DO_TERMINAL/PDV")
            .build()
    }
}