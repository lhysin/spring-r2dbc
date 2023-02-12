package io.lhysin.r2dbc.dto

import java.math.BigDecimal

data class CreatePointForm (
    val custNo: String,
    val amount: BigDecimal,

)