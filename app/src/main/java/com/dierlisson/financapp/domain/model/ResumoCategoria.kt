package com.dierlisson.financapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ResumoCategoria(
    val categoriaNome: String,
    val total: Double
)
