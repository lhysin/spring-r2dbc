package io.lhysin.r2dbc.dto

import java.math.BigDecimal

data class PointRes (
    val id: Long,
    val custNo: String,
    val amount: BigDecimal,
)