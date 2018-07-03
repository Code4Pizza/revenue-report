package com.fr.fbsreport.ui.login

import android.content.Intent
import android.widget.Toast
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseActivity
import com.fr.fbsreport.network.ApiException
import com.fr.fbsreport.network.ErrorUtils
import com.fr.fbsreport.ui.branch.BranchActivity
import com.fr.fbsreport.ui.forgotpassword.ForgotPasswordActivity
import com.fr.fbsreport.utils.EditTextUtils
import com.fr.fbsreport.widget.AppToolbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initViews() {
        toolbar.setOnClickToolbarListener(object : AppToolbar.OnClickToolbarListener {
            override fun onItemLeft() {
                finish()
            }
        })
        btn_login.setOnClickListener {
            validateInput()
        }
        txt_forgot_password.setOnClickListener {
            startActivity(Intent(this@LoginActivity, ForgotPasswordActivity::class.java))
        }
    }

    private fun validateInput() {
        val email = edt_email.text.toString()
        val password = edt_password.text.toString()

        if (email.isBlank()) {
            Toast.makeText(this@LoginActivity, "Fill email", Toast.LENGTH_SHORT).show()
            return
        }
        if (!EditTextUtils.isEmailValid(email)) {
            Toast.makeText(this@LoginActivity, "Email invalid", Toast.LENGTH_SHORT).show()
            return
        }
        if (password.isBlank()) {
            Toast.makeText(this@LoginActivity, "Fill password", Toast.LENGTH_SHORT).show()
            return
        }
        login(email, password)
    }

    private fun login(email: String, password: String) {
        requestApi(appRepository.login(email, password)
                .doOnSubscribe { showLoading() }
                .flatMap { tokenModel ->
                    userPreference.storeTokenModel(tokenModel)
                    return@flatMap appRepository.getUserInfo()
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ user ->
                    userPreference.storeUserInfo(user)
                    hideLoading()
                    startActivity(Intent(this@LoginActivity, BranchActivity::class.java))
                    finishAffinity()
                }, { err ->
                    hideLoading()
                    handleLoginError(err)
                }))
    }

    private fun handleLoginError(err: Throwable) {
        if (err is ApiException && err.type == ApiException.Type.INVALID_REQUEST) {
            Toast.makeText(this, "Email or password invalid", Toast.LENGTH_SHORT).show()
            return
        }
        ErrorUtils.handleCommonError(this, err)
    }
}
