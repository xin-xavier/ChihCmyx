package com.chih.mecm.cmyx.main.mine.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.chih.mecm.cmyx.R
import com.chih.mecm.cmyx.utils.MaterialShapeDrawableUtils
import com.chih.mecm.cmyx.utils.extend.value
import kotlinx.android.synthetic.main.activity_new_user_registration.*

class NewUserRegistrationActivity : AppCompatActivity() {

    private var enable = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user_registration)

        val extras = intent.extras
        val phoneNumber = extras?.let { "phone_number" } ?: ""

        inputVerificationCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    val b = it.length >= 4
                    if (b != enable) {
                        enable = b
                        setDetermineStatus()
                    }
                }
            }

        })

    }

    private fun setDetermineStatus() {
        determine.isEnabled = enable
        if (enable) {
            determine.background = MaterialShapeDrawableUtils.getShapeDrawable(
                4f,
                R.color.grey_900_alpha_100
            )
            determine.setTextColor(R.color.main_orange.value())
        } else {
            determine.background = MaterialShapeDrawableUtils.getShapeDrawable(
                4f,
                R.color.grey_700_alpha_100
            )
            determine.setTextColor(R.color.white.value())
        }
    }
}