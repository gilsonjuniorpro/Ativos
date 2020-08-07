package ativos.ca.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ativos.ca.R
import ativos.ca.model.Results
import kotlinx.android.synthetic.main.item_quotation.view.*

class QuotationAdapter(
    private val items: List<Results>?,
    private val onItemClick: (Results) -> Unit
): RecyclerView.Adapter<QuotationAdapter.QuotationHolder>() {

    var context: Context? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuotationHolder {
        context = parent.context
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_quotation, parent, false)
        return QuotationHolder(layout)
    }

    override fun onBindViewHolder(holder: QuotationHolder, position: Int) {
        holder.tvName.text = items?.get(position)?.name
        holder.tvSymbol.text = items?.get(position)?.symbol
        holder.tvValueNow.text = items?.get(position)?.price.toString()
        holder.tvPaidValue.text = items?.get(position)?.price.toString()
        holder.tvBrokingValue.text = "R$10,00"
        holder.tvProfitValue.text = "10%"
        holder.tvValueFinalValue.text = "100,00"

        holder.itemView.setOnClickListener{ onItemClick(items?.get(position)!!) }
    }

    override fun getItemCount(): Int = items!!.size

    class QuotationHolder(rootView: View) : RecyclerView.ViewHolder(rootView){
        val tvName: TextView = rootView.tvName
        val tvSymbol: TextView = rootView.tvSymbol
        val tvValueNow: TextView = rootView.tvValueNow
        val tvPaidValue: TextView = rootView.tvPaidValue
        val tvBrokingValue: TextView = rootView.tvBrokingValue
        val tvProfitValue: TextView = rootView.tvProfitValue
        val tvValueFinalValue: TextView = rootView.tvValueFinalValue

    }
}