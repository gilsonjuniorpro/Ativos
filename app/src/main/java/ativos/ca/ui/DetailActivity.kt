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
import ativos.ca.viewmodel.StockViewModel
import ativos.ca.viewmodel.StockViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.jarvis.ca.Mark
import kotlinx.android.synthetic.main.layout_delete_stock.*
import kotlinx.android.synthetic.main.layout_miscellaneous.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.ArrayList

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var selectStockColor: String? = null
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

        selectStockColor = "#333333"

        populateAutoCompleteStocks()
        initMiscellaneous()
        //setColorDefault(1)
        loadAvailableStock()
    }

    fun populateAutoCompleteStocks() {
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
            layoutDeleteStock.visibility = View.VISIBLE
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

    private fun setColorDefault(option: Int) {
        clearColors()
        when (option) {
            1 -> {
                selectStockColor = "#333333"
                ivColor1.setImageResource(R.drawable.ic_done)
            }
            2 -> {
                selectStockColor = "#fdbe3b"
                ivColor2.setImageResource(R.drawable.ic_done)
            }
            3 -> {
                selectStockColor = "#ff4842"
                ivColor3.setImageResource(R.drawable.ic_done)
            }
            4 -> {
                selectStockColor = "#3a52fc"
                ivColor4.setImageResource(R.drawable.ic_done)
            }
            else -> {
                selectStockColor = "#000000"
                ivColor5.setImageResource(R.drawable.ic_done)
            }
        }
    }

    private fun initMiscellaneous() {
        val bottomSheetBehavior = BottomSheetBehavior.from(layoutMiscellaneous)
        textMiscellaneous.setOnClickListener {
            if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            } else {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        ivColor1.setOnClickListener { setColorDefault(1) }
        ivColor2.setOnClickListener { setColorDefault(2) }
        ivColor3.setOnClickListener { setColorDefault(3) }
        ivColor4.setOnClickListener { setColorDefault(4) }
        ivColor5.setOnClickListener { setColorDefault(5) }

        layoutDeleteStock.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            showDeleteStockDialog()
        }
    }

    private fun clearColors() {
        ivColor1.setImageResource(0)
        ivColor2.setImageResource(0)
        ivColor3.setImageResource(0)
        ivColor4.setImageResource(0)
        ivColor5.setImageResource(0)
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
                //viewModel.delete(alreadyAvailableStock!!)
                val intent = Intent()
                intent.putExtra("isStockDeleted", true)
                setResult(RESULT_OK, intent)
                finish()
            }

            view.findViewById<TextView>(R.id.btCancelDelete).setOnClickListener {
                dialogDeleteStock!!.dismiss()
            }
        }
        dialogDeleteStock!!.show()
    }
}