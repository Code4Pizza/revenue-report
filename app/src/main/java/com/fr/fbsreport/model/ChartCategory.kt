package com.fr.fbsreport.model

import com.fr.fbsreport.base.ViewType
import com.fr.fbsreport.base.VIEW_TYPE_CHART_CATEGORY

class ChartCategory : ViewType {
    override fun getViewType(): Int {
        return VIEW_TYPE_CHART_CATEGORY
    }
}