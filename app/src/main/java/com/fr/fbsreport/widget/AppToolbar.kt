package com.fr.fbsreport.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.fr.fbsreport.R
import kotlinx.android.synthetic.main.view_app_toolbar.view.*

class AppToolbar : RelativeLayout {

    private var listener: OnClickToolbarListener? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int, defStyleRes: Int) : super(context, attrs, defStyle, defStyleRes) {
        LayoutInflater.from(context).inflate(R.layout.view_app_toolbar, this, true)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.AppToolbar)
            val textLeft = typedArray.getString(R.styleable.AppToolbar_atb_text_left)
            val imageLeft = typedArray.getDrawable(R.styleable.AppToolbar_atb_image_left)
            val textTitle = typedArray.getString(R.styleable.AppToolbar_atb_text_title)

            txt_left.text = textLeft
            imageLeft?.let {
                imageLeft.setBounds(0, 0, imageLeft.intrinsicWidth, imageLeft.intrinsicHeight)
                txt_left.setCompoundDrawables(imageLeft, null, null, null)
            }
            textTitle?.let {
                img_logo.visibility = View.GONE
                txt_title.text = textTitle
            }
            typedArray.recycle()
        }
        txt_left.setOnClickListener { listener!!.onItemLeft() }
    }

    fun setOnClickToolbarListener(listener: OnClickToolbarListener) {
        this.listener = listener
    }

    interface OnClickToolbarListener {
        fun onItemLeft()
    }
}