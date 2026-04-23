package com.dierlisson.financapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.dierlisson.financapp.ui.navigation.AppNavigation
import com.dierlisson.financapp.ui.theme.FinancAppTheme // Verifique se o nome do seu Theme está correto aqui

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Pega a instância do Repositório que criamos no Application
        val app = application as FinancAppApplication
        val repository = app.repository

        setContent {
            FinancAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Inicia a navegação passando o repositório
                    AppNavigation(repository = repository)
                }
            }
        }
    }
}