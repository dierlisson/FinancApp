package com.dierlisson.financapp.data.repository

import com.dierlisson.financapp.data.local.*
import com.dierlisson.financapp.data.mapper.toDomain
import com.dierlisson.financapp.data.mapper.toEntity
import com.dierlisson.financapp.domain.model.Categoria
import com.dierlisson.financapp.domain.model.Conta
import com.dierlisson.financapp.domain.model.ResumoCategoria
import com.dierlisson.financapp.domain.model.Transacao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class TransacaoRepository(
    private val transacaoDao: TransacaoDao,
    private val categoriaDao: CategoriaDao,
    private val contaDao: ContaDao
) {
    // TRANSAÇÕES
    val todasTransacoes: Flow<List<Transacao>> = transacaoDao.getAllTransacoes().map { entities ->
        entities.map { it.toDomain() }
    }

    fun getTransacoesPorTipo(tipo: String): Flow<List<Transacao>> = transacaoDao.getTransacoesPorTipo(tipo).map { entities ->
        entities.map { it.toDomain() }
    }

    fun getResumoPorCategoria(tipo: String): Flow<List<ResumoCategoria>> = transacaoDao.getResumoPorCategoria(tipo).map { entities ->
        entities.map { it.toDomain() }
    }

    suspend fun inserirTransacao(transacao: Transacao) = transacaoDao.insert(transacao.toEntity())

    suspend fun deletarTransacao(transacao: Transacao) = transacaoDao.delete(transacao.toEntity())

    suspend fun getTransacaoById(id: Int): Transacao? {
        return transacaoDao.getTransacaoById(id)?.toDomain()
    }

    suspend fun atualizarTransacao(transacao: Transacao) {
        transacaoDao.update(transacao.toEntity())
    }

    // CATEGORIAS
    fun getCategorias(tipo: String): Flow<List<Categoria>> = categoriaDao.getCategoriasPorTipo(tipo).map { entities ->
        entities.map { it.toDomain() }
    }

    // CONTAS
    val todasContas: Flow<List<Conta>> = contaDao.getAllContas().map { entities ->
        entities.map { it.toDomain() }
    }

    suspend fun getContaById(id: Int): Conta? = contaDao.getContaById(id)?.toDomain()

    suspend fun inserirConta(conta: Conta) = contaDao.insert(conta.toEntity())

    suspend fun atualizarConta(conta: Conta) = contaDao.update(conta.toEntity())

    // EXPORTAÇÃO
    fun exportarDadosJson(transacoes: List<Transacao>): String {
        return Json.encodeToString(transacoes)
    }
}
