package ativos.ca.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ativos.ca.R
import ativos.ca.adapter.QuotationAdapter
import ativos.ca.controller.QuotationController
import ativos.ca.databinding.ActivityMainBinding
import ativos.ca.model.Results
import ativos.ca.model.Stock
import ativos.ca.repository.StockRepository
import ativos.ca.viewmodel.StockViewModel
import ativos.ca.viewmodel.StockViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private var items: MutableList<Results> = ArrayList()
    private var layoutManager: LinearLayoutManager? = null
    private lateinit var binding: ActivityMainBinding

    private val viewModel: StockViewModel by lazy {
        ViewModelProvider(
            this,
            StockViewModelFactory(
                StockRepository(this)
            )
        ).get(StockViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        layoutManager = LinearLayoutManager(this)
        binding.recycler.layoutManager = layoutManager
        binding.recycler.setHasFixedSize(true)

        viewModel.allStocks.observe(this, Observer { stocks ->
            setItemsToObserver(stocks)
        })
    }

    private fun setItemsToObserver(items: List<Stock>){
        viewModel.getQuotations(items)

        viewModel.state.observe(this, Observer { state ->
            when(state) {
                is StockViewModel.State.Loading -> {

                }

                is StockViewModel.State.Loaded -> {
                    val adapter: QuotationAdapter? = QuotationAdapter(state.items, this::openDetail)
                    binding.recycler.adapter = adapter
                    binding.recycler.smoothScrollToPosition(0)
                }

                is StockViewModel.State.Error -> {

                }
            }
        })
    }

    private fun openDetail(results: Results?) {

    }
}