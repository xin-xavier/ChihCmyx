package com.chih.mecm.cmyx

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.chih.mecm.cmyx.utils.extend.setVisible
import kotlinx.android.synthetic.main.activity_temporary.*

class TemporaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temporary)
        homeAdConstraintLayout.setVisible()
        setImageResource(imageViewOne)
        setImageResource(imageViewTwo)
        setImageResource(imageViewThree)
        setImageResource(imageViewFour)
    }

    private fun setImageResource(imageView: ImageView) {
        imageView.setImageResource(R.drawable.ic_launcher_background)
        imageView.visibility = View.VISIBLE
        imageView.setOnClickListener {
            it.visibility = View.GONE
        }
    }

}