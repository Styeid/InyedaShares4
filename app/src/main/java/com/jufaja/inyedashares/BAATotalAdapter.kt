package com.jufaja.inyedashares

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jufaja.inyedashares.models.DataPost
import kotlinx.android.synthetic.main.item_baa_datablock_tot.view.*

class BAATotalAdapter (val context: Context, val postz: List<DataPost>) :
    RecyclerView.Adapter<BAATotalAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_baa_datablock_tot, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount() = postz.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(postz[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(postz: DataPost) {
            itemView.tvdatebaa.text = DateUtils.formatDateTime(context, postz.abdate, 4)
            itemView.tvvaluebaa.text = postz.bmtotalabcd
            //
            itemView.tvprofitdbaa.text = postz.bnmultiprofi
            if (postz.bnmultiprofi < 0.0.toString())
                itemView.tvprofitdbaa.setTextColor(context.resources.getColor(R.color.color_below_zero))
            if (postz.bnmultiprofi > 0.0.toString())
                itemView.tvprofitdbaa.setTextColor(context.resources.getColor(R.color.color_above_zero))
            //
            itemView.tvprofitbaa.text = postz.bototalprofi
            if (postz.bototalprofi < 0.0.toString())
                itemView.tvprofitbaa.setTextColor(context.resources.getColor(R.color.color_below_zero))
            if (postz.bototalprofi > 0.0.toString())
                itemView.tvprofitbaa.setTextColor(context.resources.getColor(R.color.color_above_zero))
            //
            itemView.tvdailypercbaa.text = postz.bppercentagex
            if (postz.bppercentagex < 0.0.toString())
                itemView.tvdailypercbaa.setTextColor(context.resources.getColor(R.color.color_below_zero))
            if (postz.bppercentagex > 0.0.toString())
                itemView.tvdailypercbaa.setTextColor(context.resources.getColor(R.color.color_above_zero))
            //
            itemView.tvtotalpercbaa.text = postz.bqpercentagey
            if (postz.bqpercentagey < 0.0.toString())
                itemView.tvtotalpercbaa.setTextColor(context.resources.getColor(R.color.color_below_zero))
            if (postz.bqpercentagey > 0.0.toString())
                itemView.tvtotalpercbaa.setTextColor(context.resources.getColor(R.color.color_above_zero))
        }
    }
}