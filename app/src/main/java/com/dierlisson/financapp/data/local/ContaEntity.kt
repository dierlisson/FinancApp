package com.dierlisson.financapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contas")
data class ContaEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nome: String,
    val saldoAtual: Double
)
