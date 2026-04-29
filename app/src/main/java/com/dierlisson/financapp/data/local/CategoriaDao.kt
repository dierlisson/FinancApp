package com.dierlisson.financapp.data.local

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriaDao {
    @Query("SELECT * FROM categorias WHERE tipo = :tipo ORDER BY nome ASC")
    fun getCategoriasPorTipo(tipo: String): Flow<List<CategoriaEntity>>
}