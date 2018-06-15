package com.fr.fbsreport.base

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.fr.fbsreport.R
import com.fr.fbsreport.widget.FilterDialog

abstract class BaseReportFragment<T> : BaseFragment() {

    protected lateinit var infiniteScrollListener: InfiniteScrollListener
    protected var filter: String? = null
    protected var limit: Int? = 20
    protected var page = 1

    protected lateinit var recyclerReport: RecyclerView
    protected lateinit var txtFilter: TextView
    protected lateinit var viewTotal: LinearLayout
    protected lateinit var txtTotal: TextView

    override fun onItemLeft() {
        super.onItemLeft()
        getBaseBottomTabActivity()?.onBackPressed()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerReport = view.findViewById(R.id.recycler_report)
        recyclerReport.apply {
            setHasFixedSize(true)
            val linearLayout = LinearLayoutManager(context)
            layoutManager = linearLayout
            clearOnScrollListeners()
            infiniteScrollListener = InfiniteScrollListener({ requestReports() }, linearLayout)
            addOnScrollListener(infiniteScrollListener)
        }

        txtFilter = view.findViewById(R.id.txt_filter)
        txtFilter.setOnClickListener {
            val filterDialog = FilterDialog.newInstance(txtFilter.text.toString())
            filterDialog.setOnClickFilterDialogListener(object : FilterDialog.OnClickFilterDialogListener {
                override fun onClickRecent() {
                    setupFilter(null, 20, "Recent", false)
                }

                override fun onClickToday() {
                    setupFilter("today", null, "Today", true)
                }

                override fun onClickYesterday() {
                    setupFilter("yesterday", null, "Yesterday", true)
                }

                override fun onClickWeek() {
                    setupFilter("week", null, "Week", true)
                }

                override fun onClickMonth() {
                    setupFilter("month", null, "Month", true)
                }
            })
            getBaseActivity()?.showDialogFragment(filterDialog)
        }

        viewTotal = view.findViewById(R.id.ll_total)
        viewTotal.visibility = View.GONE

        txtTotal = view.findViewById(R.id.txt_total)
    }

    fun setupFilter(filter: String?, limit: Int?, textFilter: String, totalVisible: Boolean) {
        this.filter = filter
        this.limit = limit
        this.page = 0
        txtFilter.text = textFilter
        txtTotal.text = ""
        viewTotal.visibility = if (totalVisible) View.VISIBLE else View.GONE
        filterReports()
    }

    protected abstract fun getLayoutId(): Int

    protected abstract fun requestReports()

    protected abstract fun filterReports()

    protected abstract suspend fun fetchData(): T
}