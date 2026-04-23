package com.dierlisson.financapp.ui.screens.formulario

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioScreen(
    viewModel: FormularioViewModel,
    onNavigateBack: () -> Unit
) {
    var descricao by remember { mutableStateOf("") }
    var valor by remember { mutableStateOf("") }
    var observacao by remember { mutableStateOf("") }
    var isReceita by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nova Transação") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = descricao,
                onValueChange = { descricao = it },
                label = { Text("Descrição") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = valor,
                onValueChange = { valor = it },
                label = { Text("Valor (R$)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = observacao,
                onValueChange = { observacao = it },
                label = { Text("Observação (Opcional)") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                Text("Despesa")
                Switch(
                    checked = isReceita,
                    onCheckedChange = { isReceita = it },
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Text("Receita")
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    val valorDouble = valor.replace(",", ".").toDoubleOrNull() ?: 0.0
                    val tipo = if (isReceita) "RECEITA" else "DESPESA"

                    viewModel.salvarTransacao(
                        descricao = descricao,
                        valor = valorDouble,
                        tipo = tipo,
                        observacao = observacao,
                        onSuccess = { onNavigateBack() }
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = descricao.isNotBlank() && valor.isNotBlank()
            ) {
                Text("Salvar Transação")
            }
        }
    }
}