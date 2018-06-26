package com.fr.fbsreport.base

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.fr.fbsreport.R
import com.fr.fbsreport.extension.androidLazy
import com.fr.fbsreport.widget.FilterDialog

abstract class BaseReportFragment<T : ViewType> : BaseFragment() {

    protected lateinit var infiniteScrollListener: InfiniteScrollListener
    protected var filter: String? = null
    protected var limit: Int? = 20
    protected var page = 1

    protected lateinit var recyclerReport: RecyclerView
    protected lateinit var txtFilter: TextView
    protected lateinit var viewTotal: LinearLayout
    protected lateinit var txtTotal: TextView

    protected val branchCode: String by androidLazy { arguments?.getString(EXTRA_BRANCH_CODE) ?: "" }
    protected lateinit var reportAdapter: BaseReportAdapter<T>

    override fun onItemLeft() {
        super.onItemLeft()
        getBaseBottomTabActivity()?.onBackPressed()
    }

    override fun getTextIdToolbarLeft(): Int? {
        return R.string.action_back
    }

    override fun initViews() {
        initFilterView()
        initReportList()
    }

    open fun initReportList() {
        recyclerReport = view!!.findViewById(R.id.recycler_report)
        recyclerReport.apply {
            setHasFixedSize(true)
            val linearLayout = LinearLayoutManager(context)
            layoutManager = linearLayout
            clearOnScrollListeners()
            infiniteScrollListener = InfiniteScrollListener({ requestReports(false) }, linearLayout)
            addOnScrollListener(infiniteScrollListener)
        }
    }

    private fun initFilterView() {
        txtFilter = view!!.findViewById(R.id.txt_filter)
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

        viewTotal = view!!.findViewById(R.id.ll_total)
        viewTotal.visibility = View.GONE

        txtTotal = view!!.findViewById(R.id.txt_total)
    }

    fun setupFilter(filter: String?, limit: Int?, textFilter: String, totalVisible: Boolean) {
        this.filter = filter
        this.limit = limit
        this.page = 0
        txtFilter.text = textFilter
        txtTotal.text = ""
        viewTotal.visibility = if (totalVisible) View.VISIBLE else View.GONE
        requestReports(true)
    }

    protected abstract fun requestReports(forceRefresh: Boolean)
}