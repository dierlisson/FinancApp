package com.dierlisson.financapp.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TransacaoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transacao: TransacaoEntity)

    @Query("SELECT * FROM transacoes WHERE id = :id")
    suspend fun getTransacaoById(id: Int): TransacaoEntity?

    @Update
    suspend fun update(transacao: TransacaoEntity)

    @Delete
    suspend fun delete(transacao: TransacaoEntity)

    @Query("SELECT * FROM transacoes ORDER BY data DESC")
    fun getAllTransacoes(): Flow<List<TransacaoEntity>>

    @Query("SELECT * FROM transacoes WHERE tipo = :tipo ORDER BY data DESC")
    fun getTransacoesPorTipo(tipo: String): Flow<List<TransacaoEntity>>

    // Consulta para o relatório: Total agrupado por categoria
    @Query("""
        SELECT c.nome as categoriaNome, SUM(t.valor) as total 
        FROM transacoes t 
        INNER JOIN categorias c ON t.categoriaId = c.id 
        WHERE t.tipo = :tipo 
        GROUP BY c.id
    """)
    fun getResumoPorCategoria(tipo: String): Flow<List<ResumoCategoria>>
}

// Data class auxiliar para o retorno do relatório
data class ResumoCategoria(
    val categoriaNome: String,
    val total: Double
)