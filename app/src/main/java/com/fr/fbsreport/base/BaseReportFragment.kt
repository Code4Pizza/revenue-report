package com.fr.fbsreport.base

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import android.widget.TextView
import com.fr.fbsreport.R
import com.fr.fbsreport.extension.androidLazy

abstract class BaseReportFragment<T : ViewType> : BaseFragment() {

    protected lateinit var infiniteScrollListener: InfiniteScrollListener
    protected var filter: String? = null
    protected var limit: Int? = 20
    protected var page = 1

    protected lateinit var reportList: RecyclerView
    private lateinit var txtFilter: TextView
    private lateinit var viewTotal: LinearLayout
    private lateinit var txtTotal: TextView

    protected val branchCode: String by androidLazy {
        arguments?.getString(EXTRA_BRANCH_CODE) ?: ""
    }
    protected lateinit var reportAdapter: BaseReportAdapter<T>

    override fun onItemLeft() {
        super.onItemLeft()
        getBaseBottomTabActivity()?.onBackPressed()
    }

    override fun getTextIdToolbarLeft(): Int? {
        return R.string.action_back
    }

    override fun initViews() {
        initReportList()
    }

    open fun initReportList() {
        reportList = view!!.findViewById(R.id.report_list)
        reportList.apply {
            setHasFixedSize(true)
            val linearLayout = LinearLayoutManager(context)
            layoutManager = linearLayout
            clearOnScrollListeners()
            infiniteScrollListener = InfiniteScrollListener({ requestReports(false) }, linearLayout)
            addOnScrollListener(infiniteScrollListener)
        }
    }

//    private fun initFilterView() {
//        txtFilter = view!!.findViewById(R.id.txt_filter)
//        txtFilter.setOnClickListener {
//            val filterDialog = FilterTimeDialog.newInstance(txtFilter.text.toString(), object : FilterTimeDialog.OnClickFilterDialogListener {
//                override fun onClickFilter(time: String) {
//                    when (time) {
//                        FILTER_TYPE_TODAY -> setupFilter(FILTER_TYPE_TODAY, null, FILTER_TYPE_TODAY, true)
//                        FILTER_TYPE_YESTERDAY -> setupFilter(FILTER_TYPE_YESTERDAY, null, FILTER_TYPE_YESTERDAY, true)
//                        FILTER_TYPE_WEEK -> setupFilter(FILTER_TYPE_WEEK, null, FILTER_TYPE_WEEK, true)
//                        FILTER_TYPE_MONTH -> setupFilter(FILTER_TYPE_MONTH, null, FILTER_TYPE_MONTH, true)
//                    }
//                }
//            })
//            getBaseActivity()?.showDialogFragment(filterDialog)
//        }
//
//        viewTotal = view!!.findViewById(R.id.ll_total)
//        viewTotal.visibility = View.GONE
//
//        txtTotal = view!!.findViewById(R.id.txt_total)
//    }
//
//    fun setupFilter(filter: String?, limit: Int?, textFilter: String, totalVisible: Boolean) {
//        this.filter = filter
//        this.limit = limit
//        this.page = 0
//        txtFilter.text = textFilter
//        txtTotal.text = ""
//        viewTotal.visibility = if (totalVisible) View.VISIBLE else View.GONE
//        requestReports(true)
//    }

    protected abstract fun requestReports(forceRefresh: Boolean)
}