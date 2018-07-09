package com.fr.fbsreport.ui.report

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseFragment
import com.fr.fbsreport.extension.*
import com.fr.fbsreport.utils.CommonUtils
import com.fr.fbsreport.widget.FilterTimeDialog

abstract class BaseReportTypeFragment : BaseFragment() {

    protected var filter: String = FILTER_TYPE_TODAY
    protected var limit: Int? = 20
    protected var page = 1
    protected var isLoading = false

    protected lateinit var reportList: RecyclerView
    protected lateinit var txtFilter: TextView

    protected val branchCode: String by androidLazy {
        arguments?.getString(EXTRA_BRANCH_CODE) ?: ""
    }
    protected lateinit var adapter: BaseReportAdapter

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
        reportList = view!!.findViewById(R.id.report_list)
        reportList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            clearOnScrollListeners()
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0 && !isLoading && CommonUtils.checkConnection(app)) {
                        val visibleItemCount = recyclerView.childCount
                        val totalItemCount = layoutManager.itemCount
                        val firstVisibleItem = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                        if (!isLoading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + 5)) {
                            requestMoreReports()
                            isLoading = true
                        }
                    }
                }
            })
        }
    }

    private fun initFilterView() {
        txtFilter = view!!.findViewById(R.id.txt_filter)
        txtFilter.setOnClickListener {
            val filterDialog = FilterTimeDialog.newInstance(filter, object : FilterTimeDialog.OnClickFilterDialogListener {
                override fun onClickFilter(time: String) {
                    when (time) {
                        FILTER_TYPE_TODAY -> setupFilter(FILTER_TYPE_TODAY, null, "Hôm nay")
                        FILTER_TYPE_YESTERDAY -> setupFilter(FILTER_TYPE_YESTERDAY, null, "Hôm qua")
                        FILTER_TYPE_WEEK -> setupFilter(FILTER_TYPE_WEEK, null, "Tuần")
                        FILTER_TYPE_MONTH -> setupFilter(FILTER_TYPE_MONTH, null, "Tháng")
                    }
                }
            })
            getBaseActivity()?.showDialogFragment(filterDialog)
        }
    }

    fun setupFilter(filter: String, limit: Int?, textFilter: String) {
        this.filter = filter
        this.limit = limit
        this.page = 0
        txtFilter.text = textFilter
        requestReports()
    }

    protected abstract fun requestReports()

    protected abstract fun requestMoreReports()
}