package com.fr.fbsreport.base

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.fr.fbsreport.ui.chart.month.ChartMonthFragment
import com.fr.fbsreport.ui.chart.today.ChartTodayFragment
import com.fr.fbsreport.ui.chart.week.ChartWeekFragment
import com.fr.fbsreport.ui.chart.yesterday.ChartYesterdayFragment

const val INDEX_TODAY: Int = 0
const val INDEX_YESTERDAU: Int = 1
const val INDEX_WEEK: Int = 2
const val INDEX_MONTH: Int = 2

class ReportPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return ChartTodayFragment.newInstance()
            1 -> return ChartYesterdayFragment.newInstance()
            2 -> return ChartWeekFragment.newInstance()
            3 -> return ChartMonthFragment.newInstance()
        }
        return ChartTodayFragment.newInstance()
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "Today"
            1 -> return "Yesterday"
            2 -> return "This week"
            3 -> return "This month"
        }
        return null
    }
}