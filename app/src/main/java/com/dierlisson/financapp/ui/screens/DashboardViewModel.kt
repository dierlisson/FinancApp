package com.dierlisson.financapp.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dierlisson.financapp.data.local.TransacaoEntity
import com.dierlisson.financapp.data.repository.TransacaoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DashboardViewModel(private val repository: TransacaoRepository) : ViewModel() {

    // Transforma o Flow do Room em um StateFlow que o Compose consegue ler facilmente
    val transacoes: StateFlow<List<TransacaoEntity>> = repository.todasTransacoes
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Calcula o saldo total baseado nas transações
    val saldoTotal: StateFlow<Double> = repository.todasTransacoes.map { lista ->
        lista.sumOf { if (it.tipo == "RECEITA") it.valor else -it.valor }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0.0
    )
}

// Factory necessária para passar o Repository no construtor do ViewModel
class DashboardViewModelFactory(private val repository: TransacaoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DashboardViewModel(repository) as T
        }
        throw IllegalArgumentException("ViewModel desconhecido")
    }
}