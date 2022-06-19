package compose.stockmarket.presentation.company_info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import compose.stockmarket.domain.repository.StockRepository
import compose.stockmarket.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyInfoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: StockRepository
) : ViewModel() {

    var state by mutableStateOf(CompanyInfoState())

    init {
        viewModelScope.launch {
            val symbol = savedStateHandle.get<String>("symbol") ?: return@launch
            println("Company info symbol $symbol")
            state = state.copy(isLoading = true)
            val companyInfoResult = async { repository.getCompanyInfo(symbol = symbol) }
            val intraDayInfoResult = async { repository.getCompanyIntraDayInfo(symbol = symbol) }

            state = when (val result = companyInfoResult.await()) {
                is Resource.Loading ->
                    state.copy(
                        isLoading = result.isLoading,
                        error = null
                    )
                is Resource.Success -> {
                    println("Company info ${result.data}")
                    state.copy(
                        isLoading = false,
                        company = result.data,
                        error = null
                    )
                }
                is Resource.Error ->
                    state.copy(
                        isLoading = false,
                        company = null,
                        error = result.message
                    )
            }

            state = when (val result = intraDayInfoResult.await()) {
                is Resource.Loading ->
                    state.copy(
                        isLoading = result.isLoading,
                        error = null
                    )
                is Resource.Success ->
                    state.copy(
                        isLoading = false,
                        intraDayInfo = result.data ?: emptyList(),
                        error = null
                    )
                is Resource.Error ->
                    state.copy(
                        isLoading = false,
                        company = null,
                        error = result.message
                    )
            }
        }
    }
}
