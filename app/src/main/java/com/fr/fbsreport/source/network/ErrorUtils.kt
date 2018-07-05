package com.fr.fbsreport.source.network

import android.content.Context
import android.content.Intent
import com.fr.fbsreport.extension.toast
import com.fr.fbsreport.ui.login.LoginActivity

class ErrorUtils {
    companion object {
        fun handleCommonError(context: Context?, throwable: Throwable) {
            if (throwable !is ApiException) {
                context?.toast("Đã có lỗi xảy ra, vui  lòng thử lại")
                return
            }
            when (throwable.type) {
                ApiException.Type.CRITICAL_ERROR -> throw RuntimeException("Critical exception", throwable)
                ApiException.Type.NETWORK_ERROR -> context?.toast("Mạng không khả dụng, kiểm trả kết nối")
                ApiException.Type.OAUTH_ERROR -> {
                    val intent = Intent(context, LoginActivity::class.java)
                    context?.toast("Phiên hết hạn, vui lòng đăng nhập lại")
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    context?.startActivity(intent)
                }
                ApiException.Type.INTERNAL_SERVER_ERROR -> context?.toast("Xảy ra lỗi khi lấy dữ liệu từ server, vui lòng thử lại")
                else -> context?.toast("Đã có lỗi xảy ra, vui  lòng thử lại")

            }
        }
    }
}