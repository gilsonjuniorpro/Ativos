package ativos.ca.ui

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import ativos.ca.R
import ativos.ca.databinding.ActivityDetailBinding
import ativos.ca.model.Results
import ativos.ca.model.Stock
import ativos.ca.repository.StockRepository
import ativos.ca.util.Utils
import ativos.ca.viewmodel.StockViewModel
import ativos.ca.viewmodel.StockViewModelFactory
import com.jarvis.ca.Mark
import kotlinx.android.synthetic.main.layout_delete_stock.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var dialogDeleteStock: AlertDialog? = null
    private var results: Results? = null
    private var isViewOrUpdate = false

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

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        binding.ivBack.setOnClickListener { back() }
        binding.ivSave.setOnClickListener { saveStock() }
        binding.ivDelete.setOnClickListener { showDeleteStockDialog() }

        populateAutoCompleteStocks()
        loadAvailableStock()
    }

    private fun populateAutoCompleteStocks() {
        CoroutineScope(Dispatchers.IO).launch {
            val stocksAdapter = ArrayAdapter.createFromResource(
                baseContext,
                R.array.stocks_list,
                android.R.layout.simple_list_item_1
            )

            withContext (Dispatchers.Main) {
                binding.etSymbolTitle.setAdapter(stocksAdapter)
            }
        }
    }

    private fun loadAvailableStock() {
        results = intent.getParcelableExtra("results")
        isViewOrUpdate = intent.getBooleanExtra("isViewOrUpdate", false)

        if (results != null) {
            binding.etSymbolTitle.setText(results!!.symbol)
            binding.etStockName.setText(results!!.name)
            binding.etPaid.setText(results!!.paid.toString())
            binding.etBroking.setText(results!!.broking.toString())
            binding.etProfit.setText(results!!.profit.toString())
            binding.etAmount.setText(results!!.amount.toString())
            binding.ivDelete.visibility = View.VISIBLE
        }
    }

    private fun saveStock() {
        if (binding.etSymbolTitle.text.isEmpty()) {
            Mark.showAlertError(this, getString(R.string.message_symbol_title_empty))
            binding.etSymbolTitle.requestFocus()
            return
        } else if (binding.etStockName.text.isEmpty()) {
            Mark.showAlertError(this, getString(R.string.message_nome_empty))
            binding.etStockName.requestFocus()
            return
        } else if (binding.etPaid.text.isEmpty()) {
            Mark.showAlertError(this, getString(R.string.message_value_paid_empty))
            binding.etPaid.requestFocus()
            return
        } else if (binding.etBroking.text.isEmpty()) {
            Mark.showAlertError(this, getString(R.string.message_broking_empty))
            binding.etBroking.requestFocus()
            return
        } else if (binding.etProfit.text.isEmpty()) {
            Mark.showAlertError(this, getString(R.string.message_profit_empty))
            binding.etProfit.requestFocus()
            return
        } else if (binding.etAmount.text.isEmpty()) {
            Mark.showAlertError(this, getString(R.string.message_profit_empty))
            binding.etAmount.requestFocus()
            return
        }

        val stock = Stock()
        stock.apply {
            symbol = binding.etSymbolTitle.text.toString()
            name = binding.etStockName.text.toString()
            paid = if(!binding.etPaid.text.isNullOrBlank()){
                binding.etPaid.text.toString().toDouble()
            }else{
                0.0
            }
            broking = if(!binding.etBroking.text.isNullOrBlank()){
                binding.etBroking.text.toString().toDouble()
            }else{
                0.0
            }
            profit = if(!binding.etProfit.text.isNullOrBlank()){
                binding.etProfit.text.toString().toInt()
            }else{
                0
            }
            amount = if(!binding.etAmount.text.isNullOrBlank()){
                binding.etAmount.text.toString().toInt()
            }else{
                0
            }
        }

        if (isViewOrUpdate) {
            stock.apply {
                id = results!!.id
            }
            viewModel.update(stock)
        } else {
            viewModel.save(stock)
        }

        val it = Intent()
        setResult(Activity.RESULT_OK, it)
        finish()
    }

    private fun back() {
        onBackPressed()
    }

    private fun showDeleteStockDialog() {
        if (dialogDeleteStock == null) {
            val builder = AlertDialog.Builder(this)
            val view = LayoutInflater.from(this).inflate(
                R.layout.layout_delete_stock,
                layoutDeleteContainer
            )
            builder.setView(view)

            dialogDeleteStock = builder.create()
            if (dialogDeleteStock!!.window != null) {
                dialogDeleteStock!!.window!!.setBackgroundDrawable(ColorDrawable(0))
            }

            view.findViewById<TextView>(R.id.btDeleteStock).setOnClickListener {
                if(results != null) {
                    viewModel.delete(Utils.resultsToStock(results!!))
                    val intent = Intent()
                    intent.putExtra("isStockDeleted", true)
                    setResult(RESULT_OK, intent)
                    finish()
                }else{
                    Mark.showAlertError(this, getString(R.string.message_delete_error))
                }
            }

            view.findViewById<TextView>(R.id.btCancelDelete).setOnClickListener {
                dialogDeleteStock!!.dismiss()
            }
        }
        dialogDeleteStock!!.show()
    }
}