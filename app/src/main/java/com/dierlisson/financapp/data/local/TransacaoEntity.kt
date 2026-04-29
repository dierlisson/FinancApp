package com.dierlisson.financapp.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Index
import java.util.Date

@Entity(
    tableName = "transacoes",
    foreignKeys = [
        ForeignKey(entity = ContaEntity::class, parentColumns = ["id"], childColumns = ["contaId"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = CategoriaEntity::class, parentColumns = ["id"], childColumns = ["categoriaId"], onDelete = ForeignKey.RESTRICT)
    ],
    indices = [Index("contaId"), Index("categoriaId")]
)
data class TransacaoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val contaId: Int,
    val categoriaId: Int,
    val valor: Double,
    val data: Date,
    val tipo: String, // "RECEITA" ou "DESPESA"
    val descricao: String,
    val observacao: String? = null
)
