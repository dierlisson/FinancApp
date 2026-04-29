package com.dierlisson.financapp.ui.screens.relatorios

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dierlisson.financapp.data.repository.TransacaoRepository
import com.dierlisson.financapp.domain.model.ResumoCategoria
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class RelatoriosViewModel(private val repository: TransacaoRepository) : ViewModel() {

    val resumoDespesas: StateFlow<List<ResumoCategoria>> = repository.getResumoPorCategoria("DESPESA")
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val resumoReceitas: StateFlow<List<ResumoCategoria>> = repository.getResumoPorCategoria("RECEITA")
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}

class RelatoriosViewModelFactory(private val repository: TransacaoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RelatoriosViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RelatoriosViewModel(repository) as T
        }
        throw IllegalArgumentException("ViewModel desconhecido")
    }
}
