package com.fr.fbsreport.ui.brand

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseActivity
import com.fr.fbsreport.base.BaseRecyclerAdapter
import com.fr.fbsreport.model.Brand
import com.fr.fbsreport.ui.home.HomeActivity
import kotlinx.android.synthetic.main.activity_brand.*

class BrandActivity : BaseActivity() {

    private lateinit var brandAdapter: BrandAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brand)

        setBrands()

        recycler_brand.layoutManager = LinearLayoutManager(this)
        recycler_brand.adapter = brandAdapter
    }

    private fun setBrands() {
        val brands = ArrayList<Brand>()
        brands.add(Brand(R.drawable.restaurant_logo_1, "Guta Cafe", "30 Tran Van Cao Street, Ho Chi Minh City"))
        brands.add(Brand(R.drawable.restaurant_logo_2, "Coca Cola", "12 Nguyen Khuyen Street, Ho Chi Minh City"))
        brands.add(Brand(R.drawable.restaurant_logo_3, "Mon Hue", "3 Thanh Cong Street, Ho Chi Minh City"))
        brands.add(Brand(R.drawable.restaurant_logo_4, "Highland Coffee", "4 Ke Ninh Street, Ho Chi Minh City"))

        brandAdapter = BrandAdapter(this)
        brandAdapter.setItems(brands)
        brandAdapter.setOnRecyclerItemClickListener(object : BaseRecyclerAdapter.OnRecyclerItemClickListener<Brand> {
            override fun onItemClick(item: Brand, position: Int) {
                startActivity(Intent(this@BrandActivity, HomeActivity::class.java))
            }
        })
    }
}
