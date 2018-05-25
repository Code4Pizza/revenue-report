package com.fr.fbsreport.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.fr.fbsreport.R
import kotlinx.android.synthetic.main.view_app_toolbar.view.*

class AppToolbar : RelativeLayout {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int, defStyleRes: Int) : super(context, attrs, defStyle, defStyleRes) {
        LayoutInflater.from(context).inflate(R.layout.view_app_toolbar, this, true)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.AppToolbar, 0, 0)
            val textLeft = resources.getText(typedArray
                    .getResourceId(R.styleable.AppToolbar_text_left, R.string.action_back))
            val imageLeft = resources.getDrawable(typedArray
                    .getResourceId(R.styleable.AppToolbar_image_left, R.drawable.icon_back), null)
            val visibleImageLeft = resources.getBoolean(typedArray
                    .getResourceId(R.styleable.AppToolbar_visible_image_left, 0))

            txt_left.text = textLeft
            img_left.setImageDrawable(imageLeft)
            if (visibleImageLeft) {
                img_left.visibility = View.VISIBLE
                val param = txt_left.layoutParams as LayoutParams
                param.setMargins(0, 0, 0, 0);
                img_left.layoutParams = param
            } else {
                img_left.visibility = View.GONE
                val param = txt_left.layoutParams as LayoutParams
                param.setMargins(10, 0, 0, 0);
                img_left.layoutParams = param
            }
            typedArray.recycle()
        }
    }
}