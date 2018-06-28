package com.fr.fbsreport.base

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.fr.fbsreport.ui.chart.month.ChartMonthFragment
import com.fr.fbsreport.ui.chart.today.ChartTodayFragment
import com.fr.fbsreport.ui.chart.week.ChartWeekFragment
import com.fr.fbsreport.ui.chart.yesterday.ChartYesterdayFragment
import com.fr.fbsreport.ui.report.bill.BillReportChartFragment

//
//class ReportPagerAdapter(private val branchCode: String, fm: FragmentManager) : FragmentPagerAdapter(fm) {
//
//    override fun getItem(position: Int): Fragment? {
//        when (position) {
//            0 -> return BillReportChartFragment.newInstance(branchCode)
//            1 -> return ChartYesterdayFragment.newInstance(branchCode)
//            2 -> return ChartWeekFragment.newInstance(branchCode)
//            3 -> return ChartTodayFragment.newInstance(branchCode)
//        }
//        return null
//    }
//
//    override fun getCount(): Int {
//        return 4
//    }
//
//    override fun getPageTitle(position: Int): CharSequence? {
//        when (position) {
//            0 -> return "Today"
//            1 -> return "Yesterday"
//            2 -> return "This week"
//            3 -> return "This month"
//        }
//        return null
//    }
//}