package com.chih.mecm.cmyx.main.mine.login

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.blankj.utilcode.util.RegexUtils
import com.blankj.utilcode.util.SpanUtils
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.chih.mecm.cmyx.R
import com.chih.mecm.cmyx.base.activity.FullScreenImageActivity
import com.chih.mecm.cmyx.utils.MaterialShapeDrawableUtils
import com.chih.mecm.cmyx.utils.extend.toast
import com.chih.mecm.cmyx.utils.extend.value
import com.chih.mecm.cmyx.widget.EditTextWithDel
import kotlinx.android.synthetic.main.activity_login.*
import kotlin.math.abs

const val REGISTERED = 0
const val MOBILE_NUMBER_LOGIN = 1
const val VERIFICATION_CODE_LOGIN = 2

class LoginActivity : FullScreenImageActivity() {

    private var currentItem = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    private val mAnimator = ViewPager2.PageTransformer { page, position ->
        val absPos = abs(position)
        page.apply {
            rotation = 0f
            translationY = 0f
            translationX = 0f
            val scale = if (absPos > 1) 0F else 1 - absPos
            scaleX = scale
            scaleY = scale
        }
    }

    override fun ui() {
        val data = mutableListOf<LoginCartEntity>(
            LoginCartEntity(itemType = REGISTERED),
            LoginCartEntity(itemType = MOBILE_NUMBER_LOGIN),
            LoginCartEntity(itemType = VERIFICATION_CODE_LOGIN)
        )
        viewPager.adapter = LoginCartAdapter(data)
        viewPager.isUserInputEnabled = false
        viewPager.setPageTransformer(mAnimator)
        viewPager.background = MaterialShapeDrawableUtils.getShapeDrawable(8f, R.color.white)
        viewPager.currentItem = currentItem

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                currentItem = position
                setSwitcher()
            }
        })

        switcherBtn.setOnClickListener {
            when (currentItem) {
                MOBILE_NUMBER_LOGIN -> {
                    viewPager.currentItem = REGISTERED
                }
                else -> {
                    viewPager.currentItem = MOBILE_NUMBER_LOGIN
                }
            }
        }

    }

    private fun setSwitcher() {
        val span = SpanUtils.with(switcherBtn)
        when (currentItem) {
            REGISTERED -> {
                setSwitcherSpan(span, "您已有账号? ", "马上登录")
            }
            else -> {
                setSwitcherSpan(span, "还没有账户? ", "马上注册")
            }
        }
    }

    private fun setSwitcherSpan(span: SpanUtils, status: String, action: String) {
        span.append(status)
            .setForegroundColor(R.color.grey_500_alpha_100.value())
            .append(action)
            .setForegroundColor(R.color.blue_grey_900_alpha_100.value())
            .create()
    }

    // BaseMultiItemQuickAdapter
    // 说明：适用于类型较少，业务不复杂的场景，便于快速使用。
    // 所有的数据类型，都必须实现MultiItemEntity接口
    inner class LoginCartAdapter(data: MutableList<LoginCartEntity>?) :
        BaseMultiItemQuickAdapter<LoginCartEntity, BaseViewHolder>(data) {
        init {
            addItemType(REGISTERED, R.layout.login_registered_view);
            addItemType(MOBILE_NUMBER_LOGIN, R.layout.login_mobile_number_view);
            addItemType(VERIFICATION_CODE_LOGIN, R.layout.login_verification_code_view);
        }

        override fun convert(holder: BaseViewHolder, item: LoginCartEntity) {
            val initialShapeDrawable =
                MaterialShapeDrawableUtils.getShapeDrawable(23f, R.color.initial)
            val buttonShapeDrawable =
                MaterialShapeDrawableUtils.getShapeDrawable(25f, R.color.grey_900_alpha_100)
            when (item.itemType) {
                REGISTERED -> {
                    holder.getView<View>(R.id.accountNumberView).background = initialShapeDrawable
                    holder.getView<Button>(R.id.nextStep).background = buttonShapeDrawable
                    val inputPhoneNumber = holder.getView<EditTextWithDel>(R.id.inputPhoneNumber)
                    holder.getView<Button>(R.id.nextStep)
                        .setOnClickListener {
                            val text = inputPhoneNumber.text.toString()
                            if (text.isEmpty()) {
                                "请您的手机号".toast()
                                return@setOnClickListener
                            }
                            if (!RegexUtils.isMobileExact(text)) {
                                "请输入正确的手机号".toast()
                                return@setOnClickListener
                            }
                            nextStep(text)
                        }
                }
                MOBILE_NUMBER_LOGIN -> {
                    holder.getView<View>(R.id.accountNumberView).background = initialShapeDrawable
                    holder.getView<View>(R.id.passwordView).background = initialShapeDrawable
                    val loginBtn = holder.getView<Button>(R.id.loginBtn)
                    loginBtn.background = buttonShapeDrawable
                    loginBtn.setOnClickListener {
                        "登录".toast()
                    }
                    holder.getView<TextView>(R.id.toVerificationCodeLoginPageBtn)
                        .setOnClickListener {
                            viewPager.currentItem = VERIFICATION_CODE_LOGIN
                        }
                }
                VERIFICATION_CODE_LOGIN -> {
                    holder.getView<View>(R.id.accountNumberView).background = initialShapeDrawable
                    holder.getView<View>(R.id.verificationCodeView).background =
                        initialShapeDrawable
                    val getVerificationCodeBtn =
                        holder.getView<Button>(R.id.getVerificationCodeBtn)
                    getVerificationCodeBtn.background =
                        MaterialShapeDrawableUtils.rightShapeDrawable(
                            23f,
                            R.color.grey_900_alpha_100
                        )
                    getVerificationCodeBtn.setOnClickListener {
                        "获取验证码".toast()
                    }
                    val loginBtn = holder.getView<Button>(R.id.loginBtn)
                    loginBtn.background = buttonShapeDrawable
                    loginBtn.setOnClickListener {
                        "登录".toast()
                    }
                    holder.getView<TextView>(R.id.toAccountPasswordPageBtn)
                        .setOnClickListener {
                            viewPager.currentItem = MOBILE_NUMBER_LOGIN
                        }
                }
                else -> {
                }
            }
        }

    }

    private fun nextStep(text: String) {
        intent(NewUserRegistrationActivity::class.java, Bundle().apply {
            putString("phone_number", text)
        })
    }

    data class LoginCartEntity(
        val phoneNumber: String = "", override val itemType: Int
    ) : MultiItemEntity

}