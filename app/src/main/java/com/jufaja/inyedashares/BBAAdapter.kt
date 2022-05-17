package com.jufaja.inyedashares

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jufaja.inyedashares.models.DataPost
import kotlinx.android.synthetic.main.item_bba_datablock_fir.view.*

class BBAAdapter (val context: Context, val postz: List<DataPost>) :
    RecyclerView.Adapter<BBAAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_bba_datablock_fir, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount() = postz.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(postz[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(postz: DataPost) {
            itemView.tvdatebba.text = DateUtils.formatDateTime(context, postz.abdate, 4)
            itemView.tvvaluebba.text = postz.astotalfunda
            itemView.tvcoursebba.text = postz.afpartyvaluea
            //itemView.tvmountbba.text = postz.acpartyfunda
            //
            itemView.tvprofitdbba.text = postz.atmultigrowa
            if (postz.atmultigrowa < 0.0.toString())
                itemView.tvprofitdbba.setTextColor(context.resources.getColor(R.color.color_below_zero))
            if (postz.atmultigrowa > 0.0.toString())
                itemView.tvprofitdbba.setTextColor(context.resources.getColor(R.color.color_above_zero))
            //
            itemView.tvprofitbba.text = postz.autotalgrowa
            if (postz.autotalgrowa < 0.0.toString())
                itemView.tvprofitbba.setTextColor(context.resources.getColor(R.color.color_below_zero))
            if (postz.autotalgrowa > 0.0.toString())
                itemView.tvprofitbba.setTextColor(context.resources.getColor(R.color.color_above_zero))
            //
            itemView.tvdailypercbba.text = postz.avmultiperca
            if (postz.avmultiperca < 0.0.toString())
                itemView.tvdailypercbba.setTextColor(context.resources.getColor(R.color.color_below_zero))
            if (postz.avmultiperca > 0.0.toString())
                itemView.tvdailypercbba.setTextColor(context.resources.getColor(R.color.color_above_zero))
            //
            itemView.tvtotalpercbba.text = postz.awtotalperca
            if (postz.awtotalperca < 0.0.toString())
                itemView.tvtotalpercbba.setTextColor(context.resources.getColor(R.color.color_below_zero))
            if (postz.awtotalperca > 0.0.toString())
                itemView.tvtotalpercbba.setTextColor(context.resources.getColor(R.color.color_above_zero))
        }
    }
}