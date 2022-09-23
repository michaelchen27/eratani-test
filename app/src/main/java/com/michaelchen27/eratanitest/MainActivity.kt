package com.michaelchen27.eratanitest

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btnFindWordActivity: Button
    private lateinit var btnProcessDataActivity: Button
    private lateinit var btnAnimationActivity: Button
    private lateinit var btnApiCallingActivity: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main)

        initView()
        initInteractions()
    }

    private fun initView() {
        btnFindWordActivity = findViewById(R.id.btn_find_word_activity)
        btnProcessDataActivity = findViewById(R.id.btn_process_data_activity)
        btnAnimationActivity = findViewById(R.id.btn_animation_activity)
        btnApiCallingActivity = findViewById(R.id.btn_api_calling_activity)
    }

    private fun initInteractions() {
        btnFindWordActivity.setOnClickListener(this)
        btnProcessDataActivity.setOnClickListener(this)
        btnAnimationActivity.setOnClickListener(this)
        btnApiCallingActivity.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_find_word_activity -> startActivity(
                Intent(
                    this, FindWordActivity::class.java
                )
            )
            R.id.btn_process_data_activity -> startActivity(
                Intent(
                    this,
                    ProcessDataActivity::class.java
                )
            )
            R.id.btn_animation_activity -> startActivity(
                Intent(
                    this,
                    AnimationActivity::class.java
                )
            )
            R.id.btn_api_calling_activity -> startActivity(
                Intent(
                    this,
                    ApiCallingActivity::class.java
                )
            )


        }
    }
}