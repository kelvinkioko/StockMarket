package compose.stockmarket.data.mapper

import compose.stockmarket.data.remote.dto.IntraDayInfoDto
import compose.stockmarket.domain.model.IntraDayInfoModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun IntraDayInfoDto.toIntraDayInfoModel(): IntraDayInfoModel {
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val localDateTime = LocalDateTime.parse(timestamp, formatter)
    return IntraDayInfoModel(
        date = localDateTime,
        close = close
    )
}
