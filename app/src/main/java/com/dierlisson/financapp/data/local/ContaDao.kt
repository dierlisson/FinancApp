package com.dierlisson.financapp.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ContaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(conta: ContaEntity)

    @Update
    suspend fun update(conta: ContaEntity)

    @Delete
    suspend fun delete(conta: ContaEntity)

    @Query("SELECT * FROM contas WHERE id = :id")
    suspend fun getContaById(id: Int): ContaEntity?

    @Query("SELECT * FROM contas ORDER BY nome ASC")
    fun getAllContas(): Flow<List<ContaEntity>>
}
