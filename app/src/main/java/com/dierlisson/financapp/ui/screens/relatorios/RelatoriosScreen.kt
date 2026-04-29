package com.dierlisson.financapp.ui.screens.relatorios

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import com.dierlisson.financapp.ui.screens.dashboard.formatarMoeda
import com.dierlisson.financapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RelatoriosScreen(viewModel: RelatoriosViewModel, onNavigateBack: () -> Unit) {
    val despesas by viewModel.resumoDespesas.collectAsState()
    val receitas by viewModel.resumoReceitas.collectAsState()

    var abaSelecionada by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Relatórios por Categoria") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SurfaceColor,
                    titleContentColor = GreenDark,
                    navigationIconContentColor = GreenDark
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
                .padding(paddingValues)
        ) {
            TabRow(
                selectedTabIndex = abaSelecionada,
                containerColor = SurfaceColor,
                contentColor = GreenPrimary,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[abaSelecionada]),
                        color = GreenPrimary
                    )
                }
            ) {
                Tab(
                    selected = abaSelecionada == 0,
                    onClick = { abaSelecionada = 0 },
                    text = { Text("Despesas") }
                )
                Tab(
                    selected = abaSelecionada == 1,
                    onClick = { abaSelecionada = 1 },
                    text = { Text("Receitas") }
                )
            }

            val listaAtual = if (abaSelecionada == 0) despesas else receitas
            val corValor = if (abaSelecionada == 0) RedError else GreenPrimary

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (listaAtual.isEmpty()) {
                    item {
                        Box(Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                            Text("Nenhum dado registrado para este tipo.", color = Color.Gray)
                        }
                    }
                } else {
                    items(listaAtual, key = { it.categoriaNome }) { resumo ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = SurfaceColor)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(text = resumo.categoriaNome, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                    Text(text = "Total acumulado", fontSize = 12.sp, color = Color.Gray)
                                }
                                Text(
                                    text = formatarMoeda(resumo.total),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = corValor
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
