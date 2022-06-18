package compose.stockmarket.domain.model

import java.time.LocalDateTime

data class IntraDayInfoModel(
    val date: LocalDateTime,
    val close: Double
)
