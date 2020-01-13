package com.example.gnb.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gnb.R
import com.example.gnb.api.model.Transaction

/**
 * Adapter representing the list of transactions for a specified product
 * @param items the list of transactions for a given product
 */
class ProductAdapter(private val items: List<Transaction>) :
    RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {

    /**
     * VieHolder for the product adapter
     * @param itemView the view container in which the transaction info will be displayed
     */
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            android.R.layout.two_line_list_item, parent,
            false
        )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(android.R.id.text1).text = holder.itemView.resources
            .getString(R.string.label_amount, items[position].amount)
        holder.itemView.findViewById<TextView>(android.R.id.text2).text = holder.itemView.resources
            .getString(R.string.label_currency, items[position].currency)
    }

    override fun getItemCount() = items.size
}