package com.dierlisson.financapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Categoria(
    val id: Int = 0,
    val nome: String,
    val tipo: String, // "RECEITA" ou "DESPESA"
    val icone: Int
)
