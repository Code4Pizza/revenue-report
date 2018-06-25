package com.fr.fbsreport.ui.branch

import android.support.v7.widget.LinearLayoutManager
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseActivity
import com.fr.fbsreport.base.BaseRecyclerAdapter
import com.fr.fbsreport.extension.androidLazy
import com.fr.fbsreport.model.Branch
import com.fr.fbsreport.network.ErrorUtils
import com.fr.fbsreport.ui.home.HomeActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_branch.*

class BranchActivity : BaseActivity() {

    private val branchAdapter by androidLazy { BranchAdapter() }

    override fun getLayoutId(): Int {
        return R.layout.activity_branch
    }

    override fun initViews() {
        initSwipeRefresh()
        initBranchList()
        requestBranchs()
    }

    private fun initSwipeRefresh() {
        swipe_refresh.setOnRefreshListener({ requestBranchs() })
    }

    private fun initBranchList() {
        branch_list.apply {
            layoutManager = LinearLayoutManager(this@BranchActivity)
            adapter = branchAdapter
        }
        branchAdapter.setOnRecyclerItemClickListener(object : BaseRecyclerAdapter.OnRecyclerItemClickListener<Branch> {
            override fun onItemClick(item: Branch, position: Int) {
                startActivity(HomeActivity.newIntent(this@BranchActivity, item.code))
            }
        })
    }

    private fun requestBranchs() {
        requestApi(appRepository.getBranch()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    if (!swipe_refresh.isRefreshing) showLoading()
                }
                .doFinally { swipe_refresh.isRefreshing = false }
                .subscribe({ branch ->
                    hideLoading()
                    branchAdapter.setItems(branch.data)
                }, { err ->
                    hideLoading()
                    ErrorUtils.handleCommonError(this, err)
                }))
    }
}
