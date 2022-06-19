package compose.stockmarket.presentation.company_info

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun CompanyInformationScreen(
    symbol: String,
    companyInfoViewModel: CompanyInfoViewModel = hiltViewModel()
) {
    val state = companyInfoViewModel.state
}
