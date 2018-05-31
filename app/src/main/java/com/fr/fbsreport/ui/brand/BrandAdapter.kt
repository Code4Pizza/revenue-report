package com.fr.fbsreport.ui.brand

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseRecyclerAdapter
import com.fr.fbsreport.model.Brand
import kotlinx.android.synthetic.main.item_view_brand.view.*

class BrandAdapter : BaseRecyclerAdapter<Brand, BrandAdapter.BrandViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandViewHolder {
        return BrandViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_view_brand, parent, false))
    }

    override fun onBindViewHolder(holder: BrandAdapter.BrandViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val brand = getItem(position)
        holder.imgLogo.setImageResource(brand.logo)
        holder.txtName.text = brand.name
        holder.txtAddress.text = brand.adress
    }

    class BrandViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgLogo = view.img_logo!!
        val txtName = view.txt_name!!
        val txtAddress = view.txt_address!!
    }
}