package ativos.ca.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ativos.ca.repository.StockRepository
import java.lang.IllegalArgumentException

class StockViewModelFactory(
    val repository: StockRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(StockViewModel::class.java)){
            return StockViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}