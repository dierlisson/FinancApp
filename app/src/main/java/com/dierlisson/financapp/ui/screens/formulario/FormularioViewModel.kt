package com.dierlisson.financapp.ui.screens.formulario

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dierlisson.financapp.data.repository.TransacaoRepository
import com.dierlisson.financapp.domain.model.Categoria
import com.dierlisson.financapp.domain.model.Transacao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date

class FormularioViewModel(private val repository: TransacaoRepository) : ViewModel() {

    var transacaoIdParaEdicao by mutableStateOf<Int?>(null)
        private set

    val categoriasDespesa: StateFlow<List<Categoria>> = repository.getCategorias("DESPESA")
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val categoriasReceita: StateFlow<List<Categoria>> = repository.getCategorias("RECEITA")
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun carregarTransacao(id: Int) {
        transacaoIdParaEdicao = id
    }

    suspend fun buscarTransacaoNoBanco(id: Int): Transacao? {
        return repository.getTransacaoById(id)
    }

    fun salvarTransacao(
        descricao: String,
        valor: Double,
        tipo: String,
        categoriaId: Int,
        observacao: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            val transacao = Transacao(
                id = transacaoIdParaEdicao ?: 0,
                contaId = 1, // Padrão
                categoriaId = categoriaId,
                valor = valor,
                data = Date(),
                tipo = tipo,
                descricao = descricao,
                observacao = observacao
            )

            try {
                if (transacaoIdParaEdicao == null) {
                    repository.inserirTransacao(transacao)
                } else {
                    repository.atualizarTransacao(transacao)
                }
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

class FormularioViewModelFactory(private val repository: TransacaoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FormularioViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FormularioViewModel(repository) as T
        }
        throw IllegalArgumentException("ViewModel desconhecido")
    }
}
