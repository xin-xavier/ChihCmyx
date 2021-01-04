package com.chih.mecm.cmyx.http.api

import io.reactivex.disposables.Disposable

// 只关心 code==200 的 Result
interface HttpSuccessCallback<T> {
    // 默认空实现，不强制子类实现
    fun disposable(d: Disposable){}
    fun onSuccess(t: T)
}