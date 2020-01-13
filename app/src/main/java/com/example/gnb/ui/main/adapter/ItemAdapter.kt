package com.example.gnb.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gnb.R

/**
 * Adapter representing a list of products
 * @param items the list of products
 * @param listener the listener for item clicks
 */
class ItemAdapter(private val items: Array<String>, private var listener: ItemClickListener) :
    RecyclerView.Adapter<ItemAdapter.MyViewHolder>() {

    /**
     * ViewHolder class for the products adapter
     * @param textView the name of the product
     */
    class MyViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView) {

        /**
         * Binds the data with the UI
         * @param item the product name
         * @param listener the listener for item click
         */
        fun bind(item: String, listener: ItemClickListener) {
            textView.setOnClickListener { listener.onItemClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val textView = LayoutInflater.from(parent.context).inflate(
            android.R.layout.simple_list_item_1, parent,
            false
        ) as TextView
        return MyViewHolder(textView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textView.text =
            holder.textView.resources.getString(R.string.label_product, items[position])
        holder.textView.isClickable = true
        holder.bind(items[position], listener)
    }

    override fun getItemCount() = items.size

    interface ItemClickListener {
        fun onItemClick(item: String)
    }
}
