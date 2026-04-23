package com.dierlisson.financapp.data.repository

import com.dierlisson.financapp.data.local.TransacaoDao
import com.dierlisson.financapp.data.local.TransacaoEntity
import com.dierlisson.financapp.data.local.ResumoCategoria
import kotlinx.coroutines.flow.Flow

class TransacaoRepository(private val transacaoDao: TransacaoDao) {

    val todasTransacoes: Flow<List<TransacaoEntity>> = transacaoDao.getAllTransacoes()

    fun getTransacoesPorTipo(tipo: String): Flow<List<TransacaoEntity>> {
        return transacaoDao.getTransacoesPorTipo(tipo)
    }

    fun getResumoPorCategoria(tipo: String): Flow<List<ResumoCategoria>> {
        return transacaoDao.getResumoPorCategoria(tipo)
    }

    suspend fun inserirTransacao(transacao: TransacaoEntity) {
        transacaoDao.insert(transacao)
    }

    suspend fun deletarTransacao(transacao: TransacaoEntity) {
        transacaoDao.delete(transacao)
    }
}