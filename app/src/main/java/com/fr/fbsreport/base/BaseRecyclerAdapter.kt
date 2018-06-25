package com.fr.fbsreport.base

import android.support.v7.widget.RecyclerView
import java.util.*

abstract class BaseRecyclerAdapter<I, VH> : RecyclerView.Adapter<VH>() where VH : RecyclerView.ViewHolder, I : ViewType {

    interface OnRecyclerItemClickListener<I> {
        fun onItemClick(item: I, position: Int)
    }

    private var listener: OnRecyclerItemClickListener<I>? = null
    protected var items = ArrayList<I>()

    fun setOnRecyclerItemClickListener(listener: OnRecyclerItemClickListener<I>) {
        this.listener = listener
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.itemView.setOnClickListener { listener?.onItemClick(items[position], position) }
    }

    fun setItems(items: List<I>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun clearItems() {
        this.items.clear()
        notifyDataSetChanged()
    }

    fun addItemsAtFront(items: List<I>) {
        this.items.addAll(0, items)
        notifyDataSetChanged()
    }

    fun addItems(items: List<I>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun addItem(item: I) {
        this.items.add(item)
        notifyDataSetChanged()
    }

    fun addItem(item: I, position: Int) {
        this.items.add(position, item)
        notifyDataSetChanged()
    }

    fun deleteItem(item: I) {
        this.items.remove(item)
        notifyDataSetChanged()
    }

    fun getItems(): List<I> {
        return this.items
    }

    fun getItem(position: Int): I {
        return this.items[position]
    }
}