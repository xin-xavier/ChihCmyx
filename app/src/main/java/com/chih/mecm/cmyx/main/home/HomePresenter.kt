package com.chih.mecm.cmyx.main.home

import android.annotation.SuppressLint
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.ResourceUtils
import com.chih.mecm.cmyx.ChihApplication
import com.chih.mecm.cmyx.app.api.ConstantPool.Companion.RX_CACHE_TOP_CLAZZ_DIR
import com.chih.mecm.cmyx.base.presenter.BasePresenter
import com.chih.mecm.cmyx.bean.result.TopClazzResult
import com.chih.mecm.cmyx.http.client.RetrofitHelper
import com.chih.mecm.cmyx.http.observer.BusinessHttpException
import com.chih.mecm.cmyx.http.observer.HttpDefaultObserver
import com.chih.mecm.cmyx.http.observer.HttpSuccessObserver
import com.lei.lib.java.rxcache.RxCache
import com.lei.lib.java.rxcache.entity.CacheResponse
import com.chih.mecm.cmyx.bean.result.ResultTopClazz
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.lang.ClassCastException

class HomePresenter(view: HomeContract.View) : BasePresenter<HomeContract.View>(view),
    HomeContract.Presenter<HomeContract.View> {
    override fun topClazz() {
        RetrofitHelper.apiServer
            .topClazz()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : HttpDefaultObserver<List<TopClazzResult>>() {
                override fun disposable(d: Disposable) {
                    addSubscribe(d)
                }

                @SuppressLint("CheckResult")
                override fun onSuccess(t: List<TopClazzResult>) {
                    view?.showTopClazz(t)
                    RxCache.getInstance()
                        .put(
                            RX_CACHE_TOP_CLAZZ_DIR,
                            "This is $RX_CACHE_TOP_CLAZZ_DIR cache",
                            1000 * 60 * 60 * 24 * 7
                        ).subscribe {
                            Timber.d("$RX_CACHE_TOP_CLAZZ_DIR cache successful!");
                        }
                }

                @SuppressLint("CheckResult")
                override fun onError(errorMessage: String) {
                    // view?.showMessage(errorMessage)
                    // ResultTopClazz
                    Timber.i("topClazz#onError $errorMessage")
                    RxCache.getInstance()
                        .get(
                            RX_CACHE_TOP_CLAZZ_DIR,
                            false,
                            ResultTopClazz::class.java
                        )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : Observer<CacheResponse<ResultTopClazz?>> {
                            override fun onSubscribe(d: Disposable) {

                            }

                            override fun onNext(cacheResponse: CacheResponse<ResultTopClazz?>) {
                                val data = cacheResponse.data
                                if (data == null) {
                                    //onError("缓存为空")
                                    //onError("获取不到数据")
                                    obtainedByAssetsFile(BusinessHttpException("获取不到数据",0))
                                } else {
                                    val result = data.result
                                    if (result.isNullOrEmpty()) {
                                        //onError("缓存为空")
                                        //onError("获取不到数据")
                                        obtainedByAssetsFile(BusinessHttpException("获取不到数据",0))
                                    } else {
                                        view?.showTopClazz(result)
                                    }
                                }
                            }

                            override fun onError(ex: Throwable) {
                                //view?.showMessage(ex.message)
                                obtainedByAssetsFile(ex)
                            }

                            override fun onComplete() {

                            }
                        })
                }

            })
    }

    private fun obtainedByAssetsFile(ex: Throwable){
        ChihApplication.easyCachedExecutor.async(
            {
                ResourceUtils.readAssets2String("TopClazz.json")
            },
            { jsonString ->
                try {
                    val response = GsonUtils.fromJson(
                        jsonString,
                        ResultTopClazz::class.java
                    )
                    val result = response.result
                    if (result.isNullOrEmpty()) {
                        view?.showMessage(ex.message)
                    } else {
                        view?.showTopClazz(result)
                    }
                } catch (e: ClassCastException) {
                    view?.showMessage(e.message)
                }
            }
        )
    }

    override fun getDefaultSearch() {
        RetrofitHelper.apiServer
            .getDefaultSearch()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : HttpSuccessObserver<String>() {
                override fun disposable(d: Disposable) {
                    addSubscribe(d)
                }

                override fun onSuccess(t: String) {
                    view?.showDefaultSearch(t)
                }
            })
    }


}