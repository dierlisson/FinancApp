package com.dierlisson.financapp

import android.app.Application
import com.dierlisson.financapp.data.local.AppDatabase
import com.dierlisson.financapp.data.repository.TransacaoRepository

class FinancAppApplication : Application() {
    // Instancia o banco de dados de forma "lazy" (só quando for usado a primeira vez)
    val database by lazy { AppDatabase.getDatabase(this) }

    // Instancia o repositório passando o DAO
    val repository by lazy {
        TransacaoRepository(
            transacaoDao = database.transacaoDao(),
            categoriaDao = database.categoriaDao(),
            contaDao = database.contaDao()
        )
    }
}