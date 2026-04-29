package com.dierlisson.financapp.ui.screens.formulario

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dierlisson.financapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioScreen(
    viewModel: FormularioViewModel,
    onNavigateBack: () -> Unit
) {
    var descricao by remember { mutableStateOf("") }
    var valorStr by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("RECEITA") }
    var categoriaId by remember { mutableStateOf(1) }
    var observacao by remember { mutableStateOf("") }

    val transacaoId = viewModel.transacaoIdParaEdicao

    // Se for edição, carrega os dados
    LaunchedEffect(transacaoId) {
        if (transacaoId != null) {
            val transacao = viewModel.buscarTransacaoNoBanco(transacaoId)
            if (transacao != null) {
                descricao = transacao.descricao
                valorStr = transacao.valor.toString()
                tipo = transacao.tipo
                categoriaId = transacao.categoriaId
                observacao = transacao.observacao ?: ""
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (transacaoId == null) "Nova Transação" else "Editar Transação") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SurfaceColor,
                    titleContentColor = GreenDark
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(text = "Tipo de Transação", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { tipo = "RECEITA" },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (tipo == "RECEITA") GreenSecondary else Color.LightGray.copy(alpha = 0.3f),
                        contentColor = if (tipo == "RECEITA") Color.White else Color.Gray
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.ArrowUpward, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Receita")
                }
                Button(
                    onClick = { tipo = "DESPESA" },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (tipo == "DESPESA") RedError else Color.LightGray.copy(alpha = 0.3f),
                        contentColor = if (tipo == "DESPESA") Color.White else Color.Gray
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.ArrowDownward, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Despesa")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            FormItem(label = "Nome da Transação") {
                OutlinedTextField(
                    value = descricao,
                    onValueChange = { descricao = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Ex: Supermercado, Salário...") },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = SurfaceColor,
                        focusedContainerColor = SurfaceColor
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            FormItem(label = "Valor") {
                OutlinedTextField(
                    value = valorStr,
                    onValueChange = { valorStr = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("R$ 0,00") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = SurfaceColor,
                        focusedContainerColor = SurfaceColor
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            FormItem(label = "Observação (Opcional)") {
                OutlinedTextField(
                    value = observacao,
                    onValueChange = { observacao = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Adicione um comentário...") },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = SurfaceColor,
                        focusedContainerColor = SurfaceColor
                    )
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    val valor = valorStr.toDoubleOrNull() ?: 0.0
                    viewModel.salvarTransacao(descricao, valor, tipo, categoriaId, observacao) {
                        onNavigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(if (transacaoId == null) "Salvar Transação" else "Atualizar Transação", fontSize = 16.sp, modifier = Modifier.padding(8.dp))
            }
        }
    }
}

@Composable
fun FormItem(label: String, content: @Composable () -> Unit) {
    Column {
        Text(text = label, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))
        content()
    }
}
