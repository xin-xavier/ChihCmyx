package com.chih.mecm.cmyx.http.observer

import com.blankj.utilcode.util.ToastUtils
import com.chih.mecm.cmyx.BuildConfig
import com.chih.mecm.cmyx.bean.BaseResult
import com.chih.mecm.cmyx.http.api.HttpSuccessCallback
import com.google.gson.JsonParseException
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.json.JSONException
import timber.log.Timber
import java.lang.reflect.ParameterizedType
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class HttpSuccessObserver<T> : Observer<BaseResult<T>>, HttpSuccessCallback<T> {

    override fun onComplete() {
    }

    override fun onSubscribe(d: Disposable) {
        disposable(d)
    }

    override fun onNext(t: BaseResult<T>) {
        if (t.code == 200) {
            if (t.result == null) {
                try {
                    val tClass =
                        (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
                    t.result = tClass.newInstance()
                } catch (e: ClassCastException) {
                    e.printStackTrace()
                }
            }
            t.result?.let { onSuccess(it) }
        }
    }

    override fun onError(e: Throwable) {
        val errorMessage = if (e is UnknownHostException) {
            "网络异常"
        } else if (e is JSONException || e is JsonParseException) {
            "数据异常"
        } else if (e is SocketTimeoutException) {
            "连接超时"
        } else if (e is ConnectException) {
            "连接错误"
        } else if (e is BusinessHttpException) {
            e.businessMessage
        } else {
            if (BuildConfig.DEBUG) {
                e.message ?: ""
            } else {
                "未知错误"
            }
        }
        Timber.w(e)
        ToastUtils.showShort(errorMessage)
    }

}