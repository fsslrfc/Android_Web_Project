package com.example.newapp

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter


class NewActivity : Activity() {
	private lateinit var mUserName: EditText
	private lateinit var mPassword: EditText
	private lateinit var mCaptcha: EditText
	private lateinit var mBtnLogin: Button
	
	private lateinit var mOkHttpClient: OkHttpClient
	private lateinit var mRequest: Request
	private lateinit var mResponse: Response
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.layout_new_start)
		init_view()
		init_okHttp()
		init_click_listener()
	}
	
	private fun init_okHttp() {
		mOkHttpClient = OkHttpClient()
	}
	
	private fun init_view() {
		mUserName = findViewById(R.id.et_user_name)
		mPassword = findViewById(R.id.et_user_password)
		mCaptcha = findViewById(R.id.et_captcha)
		mBtnLogin = findViewById(R.id.btn_login)
	}
	
	private fun init_click_listener() {
		mBtnLogin.setOnClickListener {
//			同步请求
			Thread() {
				run() {
					mRequest = Request.Builder().apply {
						url("https://mis.bjtu.edu.cn")
					}.build()
					val responseBody: String
					mResponse = mOkHttpClient.newCall(mRequest).execute().apply {
						// 重点修复：body.string() 只能调用一次
						responseBody = body?.string() ?: ""
						if (responseBody.isNotEmpty()) {
							try {
								val file = File(this@NewActivity.filesDir, "response.txt")
								BufferedWriter(FileWriter(file)).use { writer ->
									writer.write(responseBody)
									Log.i("NewActivity", "文件保存成功: ${file.absolutePath}")
								}
							} catch (e: Exception) {
								Log.e("NewActivity", "写入失败: ${e.stackTraceToString()}")
							}
						}
					}
					Log.i("NewActivity", "请求如下: \n" + responseBody)
					
					val file = File(this@NewActivity.filesDir, "response.html")
					if (!file.exists()) {
						file.mkdirs()
					}
					file.writeText(responseBody)
				}
			}.start()

////			异步请求
//			mRequest = Request.Builder().apply {
//				url("https://mis.bjtu.edu.cn")
//			}.build()
//			mOkHttpClient.newCall(mRequest).enqueue(object : Callback {
//				override fun onFailure(call: Call, e: IOException) {
//					Log.e("NewActivity", "请求失败: ${e.message}")
//				}
//
//				override fun onResponse(call: Call, response: Response) {
//					if (response.isSuccessful) {
//						mResponse = response
//						val responseBody = mResponse.body?.string() ?: ""
//
//						//调试信息
//						Log.i("NewActivity", "请求如下: \n" + responseBody)
//
//						val file = File(this@NewActivity.filesDir, "response.html")
//						if(!file.exists()){
//							file.createNewFile()
//						}
//						file.writeText(responseBody)
//
//						//实际操作
//						val doc = Jsoup.parse(responseBody) // 使用 Jsoup 解析 HTML
//
//						// 通过 CSS 选择器定位验证码图片
//						val captchaImg = doc.select("img.captcha").first()
//						val relativePath = captchaImg?.attr("src") ?: "" // 获取相对路径
//
//						// 拼接完整 URL（注意处理可能存在的相对路径问题）
//						val captchaUrl = "https://mis.bjtu.edu.cn$relativePath"
//
//						Request.Builder().url(captchaUrl).build().also { imgRequest ->
//							mOkHttpClient.newCall(imgRequest).enqueue(object : Callback {
//								override fun onFailure(call: Call, e: IOException) {
//									Log.e("NewActivity", "验证码请求失败: ${e.message}")
//								}
//
//								override fun onResponse(call: Call, response: Response) {
//									response.body?.bytes()?.let { bytes ->
//										// 保存验证码图片到本地
//										val imageFile =
//											File(this@NewActivity.filesDir, "captcha.jpg").apply {
//												parentFile?.mkdirs()
//											}
//
//										BufferedOutputStream(FileOutputStream(imageFile)).use {
//											it.write(bytes)
//											Log.i(
//												"NewActivity",
//												"验证码保存成功: ${imageFile.absolutePath}"
//											)
//										}
//									}
//								}
//							})
//						}
//
//					}
//					else {
//						Log.e("NewActivity", "请求未成功: ${response.code}")
//					}
//				}
//			})
			
			
		}
	}
	
	
}