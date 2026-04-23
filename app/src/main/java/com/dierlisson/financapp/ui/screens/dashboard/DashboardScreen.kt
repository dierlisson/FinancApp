package com.dierlisson.financapp.ui.screens.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dierlisson.financapp.data.local.TransacaoEntity
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(viewModel: DashboardViewModel) {
    // Observa os estados do ViewModel
    val transacoes by viewModel.transacoes.collectAsState()
    val saldo by viewModel.saldoTotal.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Resumo Financeiro") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* TODO: Navegar para Adicionar Transação */ }) {
                Text("+")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Card de Saldo
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Saldo Atual", fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = formatarMoeda(saldo),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (saldo >= 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("Últimas Transações", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))

            // Lista de Transações
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (transacoes.isEmpty()) {
                    item {
                        Text(
                            text = "Nenhuma transação registrada.",
                            modifier = Modifier.padding(16.dp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    items(transacoes) { transacao ->
                        TransacaoItem(transacao)
                    }
                }
            }
        }
    }
}

@Composable
fun TransacaoItem(transacao: TransacaoEntity) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = transacao.descricao, fontWeight = FontWeight.Bold)
                Text(text = transacao.tipo, fontSize = 12.sp)
            }
            Text(
                text = formatarMoeda(transacao.valor),
                fontWeight = FontWeight.Bold,
                color = if (transacao.tipo == "RECEITA") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
            )
        }
    }
}

// Função auxiliar para formatar em Reais (BRL)
fun formatarMoeda(valor: Double): String {
    val formatador = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    return formatador.format(valor)
}