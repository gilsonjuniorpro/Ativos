package ativos.ca.viewmodel

import androidx.lifecycle.*
import ativos.ca.controller.QuotationController
import ativos.ca.model.Quotation
import ativos.ca.model.Results
import ativos.ca.model.Stock
import ativos.ca.repository.StockRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class StockViewModel(
    private val repository: StockRepository
): ViewModel() {
    private val _stocks = MutableLiveData<List<Stock>>()
    val stocks: LiveData<List<Stock>> = _stocks

    private val _stock = MutableLiveData<Stock>()
    val stock: LiveData<Stock> = _stock

    private val _quotations = MutableLiveData<List<Quotation>>()
    val quotations: LiveData<List<Quotation>> = _quotations

    var list: MutableList<Results> = ArrayList()

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    val allStocks = repository.getAllStocks().asLiveData()

    fun save(stock: Stock){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repository.save(stock)
            }
        }
    }

    fun update(stock: Stock){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repository.update(stock)
            }
        }
    }

    fun delete(stock: Stock){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repository.delete(stock)
            }
        }
    }

    fun getStock(symbol: String){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _stock.postValue(repository.getStock(symbol))
            }
        }
    }

    fun getQuotations(items: List<Stock>) {
        //if (_state.value != null) return

        viewModelScope.launch {
            withContext(Dispatchers.IO){
                items.forEach{
                    var res = QuotationController.listAllQuotations(it.symbol)
                    if (res != null && res.symbol != null) {
                        res.paid = it.paid
                        res.broking = it.broking
                        res.profit = it.profit
                        res.amount = it.amount
                        setResult(res)
                    }else{
                        res = Results(
                            symbol = it.symbol,
                            name = it.name,
                            paid = it.paid,
                            broking = it.broking,
                            profit = it.profit,
                            amount = it.amount
                        )
                        setResult(res)
                    }
                }
            }
        }
    }

    private suspend fun setResult(res: Results){
        list.add(res)
        withContext(Dispatchers.Main){
            if(list == null){
                _state.value = State.Error(Exception("Error loading"), false)
            }else{
                _state.value = State.Loaded(list)
            }
        }
    }

    sealed class State {
        object Loading : State()
        data class Loaded(val result: List<Results>?) : State()
        data class Error(val e: Throwable, var hasConsumed: Boolean) : State()
    }
}