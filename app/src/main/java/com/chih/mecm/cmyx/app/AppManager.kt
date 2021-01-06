package com.chih.mecm.cmyx.app

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.DeviceUtils
import com.blankj.utilcode.util.SPStaticUtils
import com.chih.mecm.cmyx.ChihApplication
import com.chih.mecm.cmyx.app.api.ConstantPool
import com.chih.mecm.cmyx.app.api.ConstantPool.Companion.ACTION_TACHYCARDIA_RECEIVER
import com.chih.mecm.cmyx.app.api.ConstantPool.Companion.MODEL
import com.chih.mecm.cmyx.app.api.ConstantPool.Companion.VERSION
import com.chih.mecm.cmyx.app.api.ConstantTransmit.Companion.USER_AGREEMENT
import com.chih.mecm.cmyx.bean.EmptyBean
import com.chih.mecm.cmyx.http.client.RetrofitHelper
import com.chih.mecm.cmyx.http.observer.HttpSuccessObserver
import com.chih.mecm.cmyx.popup.UserAgreementPopup
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

object AppManager {

    private var isForeground: Boolean = true
    val intent = Intent()

    init {
        intent.action = ACTION_TACHYCARDIA_RECEIVER
    }

    fun setForeground(boolean: Boolean) {
        if (isForeground == boolean) {
            return
        }
        isForeground = boolean
        // 发送
        LocalBroadcastManager.getInstance(ChihApplication.appContext)
            .sendBroadcast(intent)
    }

    fun getForeground(): Boolean {
        return isForeground
    }

    fun isLogin(): Boolean {
        //return PrefUtils.getBoolean(Constants.LOGIN, false)
        return false
    }

    fun getToken(): String {
        return ""
    }

    /**
     * 退出登录，重置用户状态
     */
    fun resetUser() {
        //发送退出登录消息
        /*EventBus.getDefault().post(LogoutEvent())
        PrefUtils.setBoolean(Constants.LOGIN, false)
        PrefUtils.removeKey(Constants.USER_INFO)*/
    }

    // 用户协议弹窗
    fun userAgreement(context: Context) {
        if (SPStaticUtils.getBoolean(USER_AGREEMENT)) {
            return
        }
        val popup = UserAgreementPopup(context)
        popup.showPopupWindow()
    }

    // 检查版本更新
    fun checkVersionUpdates(context: Context) {
        val map = mapOf<String, String>(
            MODEL to DeviceUtils.getManufacturer(),
            VERSION to AppUtils.getAppVersionName()
        )
        RetrofitHelper.apiServer
            .checkVersionUpdates(map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : HttpSuccessObserver<EmptyBean>() {
                override fun onSuccess(t: EmptyBean) {

                }
            })
    }

}