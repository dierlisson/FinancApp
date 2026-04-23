package com.dierlisson.financapp.ui.screens.formulario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dierlisson.financapp.data.local.TransacaoEntity
import com.dierlisson.financapp.data.repository.TransacaoRepository
import kotlinx.coroutines.launch
import java.util.Date

class FormularioViewModel(private val repository: TransacaoRepository) : ViewModel() {

    fun salvarTransacao(
        descricao: String,
        valor: Double,
        tipo: String,
        observacao: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            val novaTransacao = TransacaoEntity(
                contaId = 1, // Temporário: Precisaremos criar uma Conta padrão depois
                categoriaId = 1, // Temporário: Precisaremos criar Categorias padrão
                valor = valor,
                data = Date(),
                tipo = tipo,
                descricao = descricao,
                observacao = observacao
            )

            // Para não dar crash agora pelas Foreign Keys, vamos encapsular num try-catch temporário
            try {
                repository.inserirTransacao(novaTransacao)
                onSuccess()
            } catch (e: Exception) {
                // Aqui depois vamos tratar o erro de "Conta/Categoria não encontrada"
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