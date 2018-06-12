package com.fr.fbsreport.ui.signup

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseActivity
import com.fr.fbsreport.source.AppRepository
import com.fr.fbsreport.utils.EditTextUtils
import com.fr.fbsreport.widget.AppToolbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        toolbar.setOnClickToolbarListener(object : AppToolbar.OnClickToolbarListener {
            override fun onItemLeft() {
                finish()
            }
        })

        btn_sign_up.setOnClickListener({
            validateInput()
        })
    }

    private fun validateInput() {
        val username = edt_username.text.toString()
        val email = edt_email.text.toString()
        val password = edt_password.text.toString()

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this@SignUpActivity, "Fill username", Toast.LENGTH_SHORT).show()
            return
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this@SignUpActivity, "Fill email", Toast.LENGTH_SHORT).show()
            return
        }
        if (!EditTextUtils.isEmailValid(email)) {
            Toast.makeText(this@SignUpActivity, "Email invalid", Toast.LENGTH_SHORT).show()
            return
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this@SignUpActivity, "Fill password", Toast.LENGTH_SHORT).show()
            return
        }
        register(username, email, password)
    }

    private fun register(username: String, email: String, password: String) {
        requestApi(appRepository.register(username, email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showLoading() }
                .doFinally({ hideLoading() })
                .subscribe({ user ->
                    run {
                        Toast.makeText(this@SignUpActivity, "Success", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }, { error ->
                    run {
                        Toast.makeText(this@SignUpActivity, error.message, Toast.LENGTH_SHORT).show()
                    }
                }))

    }
}
