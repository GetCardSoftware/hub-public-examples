package com.getcard.hub.providers.example

import com.getcard.hubinterface.config.PaymentProviderConfig

class Settings {
    companion object {
        /**
         * Insira aqui o seu token de autenticação obtido com o suporte da GetCard
         */
        const val AUTH_TOKEN =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhdXRoLWFwaSIsInN1YiI6ImdldGNhcmRldiIsInRva2VuX3BheWxvYWQiOiJ7XCJjbnBqXCI6bnVsbCxcInRlcm1pbmFsSWRcIjpudWxsLFwicm9sZVwiOlwiQ09NUEFOWVwifSJ9.8O08SfVKmkVvOuAEF29vC7d6rOdJ636hv8T710XCJTA"

        /**
         * Defina suas configurações aqui.
         * As configurações variam de acordo o provider utilizado.
         * Para saber quais configurações devem ser definidas, acesse a nossa
         * documentação, escolha o provider desejado e clique em
         * 'Configurações'
         * Exemplo de configurações do Scope:
         * https://doc-hubpay.tefbr.com.br/providers/scope/2.3.x/configuration
         */
        val PROVIDER_CONFIG = PaymentProviderConfig.builder()
            .setIp("177.72.161.156")
            .setPort(2046u)
            .setCompany("1283")
            .setCompanyBranch("0001")
            .setTerminal("003")
            .build()
    }
}