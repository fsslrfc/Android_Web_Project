package com.example.newapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button

class MainActivity : Activity() {

    private lateinit var mBtnNew: Button
    private lateinit var mBtnOld: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_main_activity)

        init_view()
        init_click()
    }

    private fun init_view() {
        mBtnOld = findViewById(R.id.btn_old)
        mBtnNew = findViewById(R.id.btn_new)
    }

    private fun init_click() {
        mBtnOld.apply{
            setOnClickListener {
                startActivity(Intent(this@MainActivity, OldActivity::class.java))
            }
        }
        mBtnNew.apply {
            setOnClickListener {
                startActivity(Intent(this@MainActivity, NewActivity::class.java))
            }
        }
    }
}