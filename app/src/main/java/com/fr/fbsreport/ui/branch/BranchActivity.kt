package com.fr.fbsreport.ui.branch

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseActivity
import com.fr.fbsreport.base.BaseRecyclerAdapter
import com.fr.fbsreport.extension.androidLazy
import com.fr.fbsreport.model.Branch
import com.fr.fbsreport.source.network.ErrorUtils
import com.fr.fbsreport.ui.home.HomeActivity
import com.fr.fbsreport.ui.main.MainActivity
import com.fr.fbsreport.widget.AppDialog
import com.fr.fbsreport.widget.AppToolbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_branch.*
import java.util.concurrent.TimeUnit

class BranchActivity : BaseActivity(), AppDialog.OnClickAppDialogListener {

    private val branchAdapter by androidLazy { BranchAdapter() }

    override fun getLayoutId(): Int {
        return R.layout.activity_branch
    }

    override fun initViews() {
        initToolbar()
        initSwipeRefresh()
        initBranchList()
        requestBranches()
    }

    private fun initToolbar() {
        toolbar.setOnClickToolbarListener(object : AppToolbar.OnClickToolbarListener {
            override fun onItemLeft() {
                showDialogFragment(AppDialog.newInstance("Đăng xuất", "Bạn có chắc muốn đăng xuất tài khoản này ?"))
            }
        })
    }

    override fun onClickConfirm() {
        finishAffinity()
        userPreference.signOut()
        startActivity(Intent(this@BranchActivity, MainActivity::class.java))
    }

    private fun initSwipeRefresh() {
        swipe_refresh.setOnRefreshListener { requestBranches() }
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

    private fun requestBranches() {
        requestApi(appRepository.getBranch()
                .subscribeOn(Schedulers.io())
                .materialize()
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    println("Operator " + Thread.currentThread().name)
                    it.error?.let {
                        ErrorUtils.handleCommonError(this, it)
                    }
                    it
                }
                .filter { !it.isOnError }
                .dematerialize<List<Branch>>()
                .debounce(2000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    if (!swipe_refresh.isRefreshing) showLoading()
                }
                .doOnTerminate {
                    hideLoading()
                    swipe_refresh.isRefreshing = false
                }
                .doOnNext {
                    println("Concat next")
                    branchAdapter.setItems(it)
                }
                .doOnComplete {
                    println("Concat complete")
                }
                .subscribe())
    }
}
