package com.fr.fbsreport.ui.report

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseFragment
import com.fr.fbsreport.extension.EXTRA_BRANCH_CODE
import com.fr.fbsreport.extension.androidLazy
import com.fr.fbsreport.utils.CommonUtils

abstract class BaseReportTypeFragment : BaseFragment() {

    protected var filter: String? = null
    protected var limit: Int? = 20
    protected var page = 1
    protected var isLoading = false

    protected lateinit var reportList: RecyclerView

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

    protected abstract fun requestReports()

    protected abstract fun requestMoreReports()
}