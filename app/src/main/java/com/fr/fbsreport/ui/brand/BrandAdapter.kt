package com.fr.fbsreport.ui.brand

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseRecyclerAdapter
import com.fr.fbsreport.model.Brand
import kotlinx.android.synthetic.main.item_view_brand.view.*

class BrandAdapter(context: Context) : BaseRecyclerAdapter<Brand, BrandAdapter.BrandViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandViewHolder {
        return BrandViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_view_brand, parent, false))
    }

    override fun onBindViewHolder(holder: BrandAdapter.BrandViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.bind(items[position])
    }

    class BrandViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(brand: Brand) {
            itemView.img_logo.setImageResource(brand.logo)
            itemView.txt_name.text = brand.name
            itemView.txt_address.text = brand.adress
        }
    }
}