package com.dierlisson.financapp.data.mapper

import com.dierlisson.financapp.data.local.CategoriaEntity
import com.dierlisson.financapp.data.local.ContaEntity
import com.dierlisson.financapp.data.local.TransacaoEntity
import com.dierlisson.financapp.data.local.ResumoCategoria as ResumoCategoriaEntity
import com.dierlisson.financapp.domain.model.Categoria
import com.dierlisson.financapp.domain.model.Conta
import com.dierlisson.financapp.domain.model.Transacao
import com.dierlisson.financapp.domain.model.ResumoCategoria as ResumoCategoriaDomain

fun ContaEntity.toDomain() = Conta(
    id = id,
    nome = nome,
    saldoAtual = saldoAtual
)

fun Conta.toEntity() = ContaEntity(
    id = id,
    nome = nome,
    saldoAtual = saldoAtual
)

fun CategoriaEntity.toDomain() = Categoria(
    id = id,
    nome = nome,
    tipo = tipo,
    icone = icone
)

fun Categoria.toEntity() = CategoriaEntity(
    id = id,
    nome = nome,
    tipo = tipo,
    icone = icone
)

fun TransacaoEntity.toDomain() = Transacao(
    id = id,
    contaId = contaId,
    categoriaId = categoriaId,
    valor = valor,
    data = data,
    tipo = tipo,
    descricao = descricao,
    observacao = observacao
)

fun Transacao.toEntity() = TransacaoEntity(
    id = id,
    contaId = contaId,
    categoriaId = categoriaId,
    valor = valor,
    data = data,
    tipo = tipo,
    descricao = descricao,
    observacao = observacao
)

fun ResumoCategoriaEntity.toDomain() = ResumoCategoriaDomain(
    categoriaNome = categoriaNome,
    total = total
)
