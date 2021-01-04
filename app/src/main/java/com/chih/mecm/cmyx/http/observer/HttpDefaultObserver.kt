package com.chih.mecm.cmyx.http.observer

import com.chih.mecm.cmyx.app.AppManager
import com.chih.mecm.cmyx.bean.BaseResult
import com.chih.mecm.cmyx.http.api.HttpDefaultCallback
import com.google.gson.JsonParseException
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.json.JSONException
import timber.log.Timber
import java.lang.reflect.ParameterizedType
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

// 给 Result 脱壳,对服务器错误统一处理
abstract class HttpDefaultObserver<T> : Observer<BaseResult<T>>, HttpDefaultCallback<T> {

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
        // code!=200 代表业务出错，进行过滤
        else {
            filterCode(t.msg, t.code)
        }
    }

    private fun filterCode(msg: String, code: Int) {
        when (code) {
            // 登录信息已失效，请重新登录
            400 -> {
                 // toast
            }
            401 -> {
                AppManager.resetUser()
                onError(
                    BusinessHttpException(msg, code)
                )
            }
            //未知 code 将 errorMessage 封装成异常,由 onError() 处理
            else -> onError(
                BusinessHttpException(msg, code)
            )
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
            Timber.w(e.businessMessage)
            e.businessMessage
        } else {
            e.message ?: "未知错误"
        }
        e.printStackTrace()
        onError(errorMessage)
    }


}