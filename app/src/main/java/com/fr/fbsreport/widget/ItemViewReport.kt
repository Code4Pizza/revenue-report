package com.fr.fbsreport.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.fr.fbsreport.R
import kotlinx.android.synthetic.main.item_view_report.view.*

class ItemViewReport @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RelativeLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.item_view_report, this, true)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.ItemViewReport)
            val imageIcon = typedArray.getDrawable(R.styleable.ItemViewReport_ivr_image_icon)
            val textTitle = typedArray.getString(R.styleable.ItemViewReport_ivr_text_title)
            val marginBorder = typedArray.getDimension(R.styleable.ItemViewReport_ivr_marginBorder, 0f)

            imageIcon?.let {
                img_icon.setImageDrawable(imageIcon)
            }
            txt_title.text = textTitle
            val param = view_border.layoutParams as RelativeLayout.LayoutParams
            param.setMargins(marginBorder.toInt(), 0, 0, 0)
            view_border.layoutParams = param
            typedArray.recycle()
        }
    }
}