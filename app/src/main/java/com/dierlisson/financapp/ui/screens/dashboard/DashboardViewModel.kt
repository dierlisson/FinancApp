package com.dierlisson.financapp.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dierlisson.financapp.data.repository.TransacaoRepository
import com.dierlisson.financapp.domain.model.Transacao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

enum class TipoFiltro { TODAS, RECEITA, DESPESA }

class DashboardViewModel(private val repository: TransacaoRepository) : ViewModel() {

    private val _filtroAtual = MutableStateFlow(TipoFiltro.TODAS)
    val filtroAtual = _filtroAtual.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val transacoes: StateFlow<List<Transacao>> = combine(_filtroAtual, _searchQuery) { filtro, query ->
        filtro to query
    }.flatMapLatest { (filtro, query) ->
        val baseFlow = when (filtro) {
            TipoFiltro.TODAS -> repository.todasTransacoes
            TipoFiltro.RECEITA -> repository.getTransacoesPorTipo("RECEITA")
            TipoFiltro.DESPESA -> repository.getTransacoesPorTipo("DESPESA")
        }
        if (query.isBlank()) baseFlow else baseFlow.map { list ->
            list.filter { it.descricao.contains(query, ignoreCase = true) }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val saldoTotal: StateFlow<Double> = repository.todasTransacoes.map { lista ->
        lista.sumOf { if (it.tipo == "RECEITA") it.valor else -it.valor }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0.0
    )

    val totalReceitas: StateFlow<Double> = repository.todasTransacoes.map { lista ->
        lista.filter { it.tipo == "RECEITA" }.sumOf { it.valor }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val totalDespesas: StateFlow<Double> = repository.todasTransacoes.map { lista ->
        lista.filter { it.tipo == "DESPESA" }.sumOf { it.valor }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    fun setFiltro(filtro: TipoFiltro) {
        _filtroAtual.value = filtro
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun deletarTransacao(transacao: Transacao) {
        viewModelScope.launch {
            repository.deletarTransacao(transacao)
        }
    }
    
    fun exportarDados(onResult: (String) -> Unit) {
        viewModelScope.launch {
            val transacoesList = transacoes.value
            val json = repository.exportarDadosJson(transacoesList)
            onResult(json)
        }
    }
}

class DashboardViewModelFactory(private val repository: TransacaoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DashboardViewModel(repository) as T
        }
        throw IllegalArgumentException("ViewModel desconhecido")
    }
}
