package com.example.newapp

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.util.Xml
import android.widget.TextView
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.xmlpull.v1.XmlPullParser

class DelActivityXml : Activity() {
	private val mIP: String = "192.168.31.40"
	private val mPort: String = "8080"
	
	private lateinit var mApp1Name: TextView
	private lateinit var mApp1Version: TextView
	private lateinit var mApp2Name: TextView
	private lateinit var mApp2Version: TextView
	
	private lateinit var mOkHttpClient: OkHttpClient
	private lateinit var mRequest: Request
	
	private lateinit var mResponse: Response
	
	private var mHandler = object : Handler(Looper.getMainLooper()) {
		@SuppressLint("SetTextI18n")
		override fun handleMessage(msg: Message) {
			super.handleMessage(msg)
			when (msg.what) {
				1 -> {
					val xmlData = msg.obj as? String ?: return
					try {
						val parser = Xml.newPullParser().apply {
							setInput(xmlData.reader())
						}
						var currentAppId = 0
						var currentAppName = ""
						var currentAppVersion = ""
						var eventType = parser.eventType
						while (eventType != XmlPullParser.END_DOCUMENT) {
							when (eventType) {
								XmlPullParser.START_TAG -> {
									when (parser.name) {
										"app" -> {
											// 重置临时变量
											currentAppId = 0
											currentAppName = ""
											currentAppVersion = ""
										}
										"id" -> currentAppId = parser.nextText().toInt()
										"name" -> currentAppName = parser.nextText()
										"version" -> currentAppVersion = parser.nextText()
									}
								}
								XmlPullParser.END_TAG -> {
									if (parser.name == "app") {
										// 根据 ID 更新对应 UI
										when (currentAppId) {
											1 -> {
												mApp1Name.text = currentAppName
												mApp1Version.text = currentAppVersion
											}
											2 -> {
												mApp2Name.text = currentAppName
												mApp2Version.text = currentAppVersion
											}
										}
									}
								}
							}
							eventType = parser.next()
						}
					} catch (e: Exception) {
						Log.e("XML-Parse", "解析失败", e)
					}
					
					
				}
			}
		}
		
	}
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.del_layout_xml)
		init_veiw()
		init_http()
	}
	
	private fun init_veiw() {
		mApp1Name = findViewById(R.id.app1_name)
		mApp1Version = findViewById(R.id.app1_version)
		mApp2Name = findViewById(R.id.app2_name)
		mApp2Version = findViewById(R.id.app2_version)
	}
	
	private fun init_http() {
		mOkHttpClient = OkHttpClient()
		Thread() {
			run() {
				mRequest = Request.Builder().apply {
					url(
						"http://" + mIP + ":" + mPort + "/xml"
					)
				}.build()
				mResponse = mOkHttpClient.newCall(mRequest).execute().apply {
					val responseBody = body?.string() ?: ""
					if (responseBody.isNotEmpty()) {
						Log.i("NewActivity", "请求成功!")
						Log.i("NewActivity", "内容如下：\n" + responseBody)
						
						mHandler.apply {
							sendMessage(Message.obtain().apply {
								what = 1
								obj = responseBody
							})
						}
						
						
					}
					else {
						Log.e("NewActivity", "请求未成功!")
					}
				}
			}
		}.start()
	}
	
}