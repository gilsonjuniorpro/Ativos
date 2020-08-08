package ativos.ca.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ativos.ca.R
import ativos.ca.adapter.QuotationAdapter
import ativos.ca.databinding.ActivityMainBinding
import ativos.ca.model.Results
import ativos.ca.model.Stock
import ativos.ca.repository.StockRepository
import ativos.ca.viewmodel.StockViewModel
import ativos.ca.viewmodel.StockViewModelFactory
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private var items: MutableList<Results> = ArrayList()
    private var layoutManager: LinearLayoutManager? = null
    private lateinit var binding: ActivityMainBinding

    private val REQUEST_CODE_ADD_STOCK = 1
    private val REQUEST_CODE_UPDATE_STOCK = 2

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
            //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //.setAction("Action", null).show()
            openCreateStock()
        }

        layoutManager = LinearLayoutManager(this)
        binding.recycler.layoutManager = layoutManager
        binding.recycler.setHasFixedSize(true)

        viewModel.allStocks.observe(this, Observer { stocks ->
            setItemsToObserver(stocks)
        })
    }

    private fun openCreateStock() {
        startActivityForResult(
            Intent(applicationContext, DetailActivity::class.java),
            REQUEST_CODE_ADD_STOCK
        )
    }

    private fun setItemsToObserver(items: List<Stock>){
        viewModel.getQuotations(items)

        viewModel.state.observe(this, Observer { state ->
            when(state) {
                is StockViewModel.State.Loading -> {

                }

                is StockViewModel.State.Loaded -> {
                    val adapter: QuotationAdapter? = QuotationAdapter(state.result, this::openDetail)
                    binding.recycler.adapter = adapter
                    binding.recycler.smoothScrollToPosition(0)
                }

                is StockViewModel.State.Error -> {

                }
            }
        })
    }

    private fun openDetail(results: Results?) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("isViewOrUpdate", true)
        intent.putExtra("results", results)
        //intent.putExtra("stock", stock)
        startActivityForResult(
            intent,
            REQUEST_CODE_UPDATE_STOCK
        )
    }
}