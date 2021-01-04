package com.chih.mecm.cmyx.http.api

import io.reactivex.disposables.Disposable

interface HttpDefaultCallback<T>{
    fun disposable(d: Disposable)
    fun onSuccess(t: T)
    fun onError(errorMessage: String)
}