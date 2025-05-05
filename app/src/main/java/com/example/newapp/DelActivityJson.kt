package com.example.newapp

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.TextView
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject

class DelActivityJson : Activity() {
	private var appid = "76758326"
	private var appsecret = "wHiJ8aaP"
	private var version = "v63"
	private var unescape = "1"
	
	private lateinit var mTemNow: TextView
	private lateinit var mWeather: TextView
	private lateinit var mTemLow: TextView
	private lateinit var mTemHigh: TextView
	private lateinit var mWinDir: TextView
	private lateinit var mWinSpeed: TextView
	private lateinit var mWinMeter: TextView
	private lateinit var mAir: TextView
	private lateinit var mUpdateTime: TextView
	
	private lateinit var mHumidity: TextView
	private lateinit var mOkHttpClient: OkHttpClient
	private lateinit var mRequest: Request
	
	private lateinit var mResponse: Response
	
	private var mHandler = object : Handler(Looper.getMainLooper()) {
		@SuppressLint("SetTextI18n")
		override fun handleMessage(msg: Message) {
			super.handleMessage(msg)
			
			when (msg.what) {
				1 -> {
					val js: JSONObject = JSONObject(msg.obj.toString())
					mTemNow.text = js.optString("tem") + "℃"
					mWeather.text = js.optString("wea")
					mTemLow.text = js.optString("tem_night") + "℃"
					mTemHigh.text = js.optString("tem_day") + "℃"
					mWinDir.text = js.optString("win")
					mWinSpeed.text = js.optString("win_speed")
					mWinMeter.text = js.optString("win_meter")
					mAir.text = js.optString("air")
					mHumidity.text = js.optString("humidity")
					mUpdateTime.text =
						"最后更新时间:" + js.optString("date") + " " + js.optString("update_time")
				}
			}
		}
		
	}
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.del_layout_json)
		init_veiw()
		init_http()
	}
	
	private fun init_veiw() {
		mTemNow = findViewById(R.id.temperature)
		mTemLow = findViewById(R.id.tem_low)
		mTemHigh = findViewById(R.id.tem_high)
		mWeather = findViewById(R.id.weather)
		mWinDir = findViewById(R.id.win_dir)
		mWinSpeed = findViewById(R.id.win_speed)
		mWinMeter = findViewById(R.id.win_meter)
		mAir = findViewById(R.id.air)
		mHumidity = findViewById(R.id.humidity)
		mUpdateTime = findViewById(R.id.update_time)
	}
	
	private fun init_http() {
		mOkHttpClient = OkHttpClient()
		Thread() {
			run() {
				mRequest = Request.Builder().apply {
					url(
						"https://v1.yiketianqi.com/free/day" +
								"?appid=" + appid +
								"&appsecret=" + appsecret +
								"&version=" + version +
								"&unescape=" + unescape +
								"&city=" + "北京"
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