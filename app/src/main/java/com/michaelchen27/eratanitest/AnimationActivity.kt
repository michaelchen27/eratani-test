package com.michaelchen27.eratanitest

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.slider.Slider


class AnimationActivity : AppCompatActivity(), AnimationListener, Slider.OnChangeListener {
    //    private var handlerAnimation = Handler()
    private lateinit var ivBeat: ImageView

    private lateinit var zoomIn: Animation
    private lateinit var zoomOut: Animation
    private lateinit var rangeSlider: Slider
    private lateinit var tvBpm: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)
        initView()
    }

    private fun initView() {
        ivBeat = findViewById(R.id.iv_beat)
        rangeSlider = findViewById(R.id.rs_bpm)

        tvBpm = findViewById(R.id.tv_bpm)
        tvBpm.text = "100"

        rangeSlider.addOnChangeListener(this)

        zoomIn = AnimationUtils.loadAnimation(this, R.anim.zoomin)
        zoomOut = AnimationUtils.loadAnimation(this, R.anim.zoomout)

        zoomIn.startOffset = 1100

        ivBeat.animation = zoomIn
        ivBeat.animation = zoomOut

        zoomIn.setAnimationListener(this)
        zoomOut.setAnimationListener(this)
    }

    override fun onAnimationStart(p0: Animation?) {
    }

    override fun onAnimationEnd(anim: Animation?) {
        when (anim) {
            zoomIn -> {
                ivBeat.startAnimation(zoomOut)
            }
            zoomOut -> {
                ivBeat.startAnimation(zoomIn)
            }
        }

    }

    override fun onAnimationRepeat(p0: Animation?) {
    }

    override fun onValueChange(slider: Slider, value: Float, fromUser: Boolean) {
        tvBpm.text = value.toInt().toString()
        zoomIn.startOffset = (slider.valueTo.toInt() - slider.valueFrom.toInt()).toLong() - value.toLong()
//        zoomIn.duration = 1100 - value.toLong()
//        zoomOut.duration = 1100 - value.toLong()
    }
}