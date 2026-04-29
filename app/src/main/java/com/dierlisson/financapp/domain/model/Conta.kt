package com.dierlisson.financapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Conta(
    val id: Int = 0,
    val nome: String,
    val saldoAtual: Double
)
