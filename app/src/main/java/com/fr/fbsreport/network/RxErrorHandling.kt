package com.fr.fbsreport.network

import android.annotation.SuppressLint
import io.reactivex.*
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.IOException
import java.lang.reflect.Type

class RxErrorHandling : CallAdapter.Factory() {

    private var original: RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()

    override fun get(returnType: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): CallAdapter<*, *>? {
        val adapter = original.get(returnType, annotations, retrofit)
        if (adapter != null) {
            return RxCallAdapterWrapper(adapter)
        }
        return null
    }

    companion object {
        fun create(): CallAdapter.Factory {
            return RxErrorHandling()
        }

        @SuppressLint("CheckResult")
        class RxCallAdapterWrapper<R>(private var wrapped: CallAdapter<R, *>) : CallAdapter<R, Any> {

            override fun adapt(call: Call<R>): Any {
                val callResult = wrapped.adapt(call)
                if (callResult is Observable<*>) {
                    return callResult.onErrorResumeNext { throwable: Throwable ->
                        Observable.error(asApiException(throwable))
                    }
                }
                if (callResult is Single<*>) {
                    return callResult.onErrorResumeNext { throwable: Throwable ->
                        Single.error(asApiException(throwable))
                    }
                }
                if (callResult is Flowable<*>) {
                    return callResult.onErrorResumeNext { throwable: Throwable ->
                        Flowable.error(asApiException(throwable))
                    }
                }
                if (callResult is Maybe<*>) {
                    return callResult.onErrorResumeNext { throwable: Throwable ->
                        Maybe.error(asApiException(throwable))
                    }
                }
                if (callResult is Completable) {
                    return callResult.onErrorResumeNext { throwable: Throwable ->
                        Completable.error(asApiException(throwable))
                    }
                }
                return callResult
            }

            override fun responseType(): Type {
                return wrapped.responseType()
            }

            private fun asApiException(throwable: Throwable): ApiException {
                // We had non-200 http error
                if (throwable is HttpException) {
                    val response = throwable.response()
                    return ApiException.httpError(response)
                }
                // A network error happened
                return if (throwable is IOException) {
                    ApiException.networkError(throwable)
                } else {
                    ApiException.criticalError(throwable)
                }
                // We don't know what happened. We need to simply convert to an critical error
            }
        }
    }
}