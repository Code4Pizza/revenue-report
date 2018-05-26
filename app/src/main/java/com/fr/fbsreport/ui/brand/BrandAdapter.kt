package com.fr.fbsreport.ui.brand

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fr.fbsreport.R
import com.fr.fbsreport.model.Brand
import kotlinx.android.synthetic.main.item_view_brand.view.*

class BrandAdapter : RecyclerView.Adapter<BrandAdapter.BrandViewHolder>() {

    var brands: ArrayList<Brand> = ArrayList()
    var listener: OnItemBrandClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandViewHolder {
        return BrandViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_view_brand, parent, false))
    }

    override fun getItemCount(): Int {
        return brands.size
    }

    override fun onBindViewHolder(holder: BrandViewHolder, position: Int) {
        val brand = brands.get(position)
        holder.imgLogo.setImageResource(brand.logo)
        holder.txtName.text = brand.name
        holder.txtAddress.text = brand.adress
        holder.itemView.setOnClickListener({ listener?.onBrandClick(brand) })
    }

    class BrandViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgLogo = view.img_logo!!
        val txtName = view.txt_name!!
        val txtAddress = view.txt_address!!
    }

    fun addBrands(brands: ArrayList<Brand>) {
        this.brands.clear()
        this.brands.addAll(brands)
    }

    fun setOnItemBrandClickListener(listener: OnItemBrandClickListener) {
        this.listener = listener
    }

    interface OnItemBrandClickListener {
        fun onBrandClick(brand: Brand)
    }
}
