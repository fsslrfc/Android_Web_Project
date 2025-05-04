package com.example.newapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button

class MainActivity : Activity() {

//    private lateinit var mBtnNew: Button
//    private lateinit var mBtnOld: Button
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.layout_main_activity)
//
//        init_view()
//        init_click()
//    }
//
//    private fun init_view() {
//        mBtnOld = findViewById(R.id.btn_old)
//        mBtnNew = findViewById(R.id.btn_new)
//    }
//
//    private fun init_click() {
//        mBtnOld.apply{
//            setOnClickListener {
//                startActivity(Intent(this@MainActivity, OldActivity::class.java))
//            }
//        }
//        mBtnNew.apply {
//            setOnClickListener {
//                startActivity(Intent(this@MainActivity, NewActivity::class.java))
//            }
//        }
//    }
    
    private lateinit var mBtn1: Button
    private lateinit var mBtn2: Button
    private lateinit var mBtn3: Button
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.del_layout_main)

        init()
    }

    private fun init() {
        mBtn1 = findViewById<Button?>(R.id.btn_1).apply {
            setOnClickListener {
                startActivity(Intent(this@MainActivity, DelActivityWebView::class.java))
            }
        }
        mBtn2 = findViewById<Button?>(R.id.btn_2).apply {
            setOnClickListener {
                startActivity(Intent(this@MainActivity, DelActivityJson::class.java))
            }
        }
        mBtn3 = findViewById<Button?>(R.id.btn_3).apply {
            setOnClickListener {
                startActivity(Intent(this@MainActivity, DelActivityXml::class.java))
            }
        }
    }

    private fun init_click() {
        mBtn1.apply{
            setOnClickListener {
                startActivity(Intent(this@MainActivity, OldActivity::class.java))
            }
        }
        mBtn2.apply {
            setOnClickListener {
                startActivity(Intent(this@MainActivity, NewActivity::class.java))
            }
        }
    }
    
    
    
}