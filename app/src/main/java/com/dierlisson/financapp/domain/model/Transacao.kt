package com.dierlisson.financapp.domain.model

import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class Transacao(
    val id: Int = 0,
    val contaId: Int,
    val categoriaId: Int,
    val valor: Double,
    @Serializable(with = DateSerializer::class)
    val data: Date,
    val tipo: String, // "RECEITA" ou "DESPESA"
    val descricao: String,
    val observacao: String? = null
)
