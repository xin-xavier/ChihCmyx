package com.chih.mecm.cmyx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.home_ad_layout.*

class TemporaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_ad_layout)

        setImageResource(imageView1)
        setImageResource(imageView2)
        setImageResource(imageView3)
        setImageResource(imageView4)
        setImageResource(imageView5)
    }

    private fun setImageResource(imageView: ImageView) {
        imageView.setImageResource(R.drawable.ic_launcher_background)
        imageView.visibility = View.VISIBLE
        imageView.setOnClickListener {
            it.visibility = View.GONE
        }
    }

}