package com.dierlisson.financapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categorias")
data class CategoriaEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nome: String,
    val tipo: String, // "RECEITA" ou "DESPESA"
    val icone: Int // ID do resource do ícone
)
