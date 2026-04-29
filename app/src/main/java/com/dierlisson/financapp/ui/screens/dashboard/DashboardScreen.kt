package com.dierlisson.financapp.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dierlisson.financapp.domain.model.Transacao
import com.dierlisson.financapp.ui.theme.*
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    onNavigateToAdd: (Int?) -> Unit,
    onNavigateToReports: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = SurfaceColor) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.Dashboard, contentDescription = "Resumo") },
                    label = { Text("Resumo") }
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Transações") },
                    label = { Text("Transações") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { onNavigateToAdd(null) },
                    icon = { 
                        Icon(
                            Icons.Default.AddCircle, 
                            contentDescription = "Nova",
                            tint = GreenPrimary,
                            modifier = Modifier.size(32.dp)
                        ) 
                    },
                    label = { Text("Nova") }
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedTab) {
                0 -> ResumoContent(viewModel, onNavigateToReports)
                1 -> TransacoesListContent(viewModel, onNavigateToAdd)
            }
        }
    }
}

@Composable
fun ResumoContent(viewModel: DashboardViewModel, onNavigateToReports: () -> Unit) {
    val saldo by viewModel.saldoTotal.collectAsState()
    val receitas by viewModel.totalReceitas.collectAsState()
    val despesas by viewModel.totalDespesas.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Resumo",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = GreenDark
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {
                    viewModel.exportarDados { json ->
                        println("DADOS EXPORTADOS: $json")
                    }
                }) {
                    Icon(Icons.Default.Share, contentDescription = "Exportar", tint = GreenDark)
                }
                IconButton(onClick = onNavigateToReports) {
                    Icon(Icons.Default.Notifications, contentDescription = null, tint = GreenDark)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Card de Saldo Atual
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = GreenPrimary)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Saldo Atual", color = Color.White, fontSize = 16.sp)
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(Icons.Default.Visibility, contentDescription = null, tint = Color.White)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = formatarMoeda(saldo),
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "+12,5% este mês", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Cards de Receitas e Despesas
        SummaryCard(
            title = "RECEITAS",
            value = receitas,
            icon = Icons.Default.ArrowDownward,
            iconColor = GreenSecondary,
            percentage = "+8,2% vs mês anterior"
        )

        Spacer(modifier = Modifier.height(12.dp))

        SummaryCard(
            title = "DESPESAS",
            value = despesas,
            icon = Icons.Default.ArrowUpward,
            iconColor = RedError,
            percentage = "+5,1% vs mês anterior"
        )
        
        Spacer(modifier = Modifier.weight(1f))
        
        Button(
            onClick = onNavigateToReports,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Ver Relatórios Detalhados")
        }
    }
}

@Composable
fun SummaryCard(title: String, value: Double, icon: ImageVector, iconColor: Color, percentage: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceColor)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(iconColor.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = title, fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                Text(text = formatarMoeda(value), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = percentage, fontSize = 10.sp, color = iconColor)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransacoesListContent(viewModel: DashboardViewModel, onNavigateToEdit: (Int) -> Unit) {
    val transacoes by viewModel.transacoes.collectAsState()
    val filtroAtual by viewModel.filtroAtual.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Transações",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = GreenDark
            )
            IconButton(onClick = { /* TODO: Sort menu */ }) {
                Icon(Icons.Default.FilterList, contentDescription = null, tint = GreenDark)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.setSearchQuery(it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Buscar transações...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = SurfaceColor,
                focusedContainerColor = SurfaceColor,
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = GreenPrimary
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(
                selected = filtroAtual == TipoFiltro.TODAS,
                onClick = { viewModel.setFiltro(TipoFiltro.TODAS) },
                label = { Text("Todas") }
            )
            FilterChip(
                selected = filtroAtual == TipoFiltro.RECEITA,
                onClick = { viewModel.setFiltro(TipoFiltro.RECEITA) },
                label = { Text("Receitas") }
            )
            FilterChip(
                selected = filtroAtual == TipoFiltro.DESPESA,
                onClick = { viewModel.setFiltro(TipoFiltro.DESPESA) },
                label = { Text("Despesas") }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(transacoes) { transacao ->
                TransacaoItem(
                    transacao = transacao,
                    onEdit = { onNavigateToEdit(transacao.id) },
                    onDelete = { viewModel.deletarTransacao(transacao) }
                )
            }
        }
    }
}

@Composable
fun TransacaoItem(transacao: Transacao, onEdit: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onEdit() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceColor)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(
                        if (transacao.tipo == "RECEITA") BlueLight else RedLight
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    if (transacao.tipo == "RECEITA") Icons.Default.Work else Icons.Default.ShoppingCart,
                    contentDescription = null,
                    tint = if (transacao.tipo == "RECEITA") BlueIncome else RedError
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = transacao.descricao, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = "Categoria", fontSize = 12.sp, color = Color.Gray)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = formatarMoeda(transacao.valor),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = if (transacao.tipo == "RECEITA") GreenPrimary else RedError
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "15 Jan 2024", fontSize = 10.sp, color = Color.Gray)
                    IconButton(onClick = onDelete, modifier = Modifier.size(24.dp)) {
                        Icon(Icons.Default.Delete, contentDescription = null, tint = RedError.copy(alpha = 0.5f), modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}

fun formatarMoeda(valor: Double): String {
    val formatador = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    return formatador.format(valor)
}
