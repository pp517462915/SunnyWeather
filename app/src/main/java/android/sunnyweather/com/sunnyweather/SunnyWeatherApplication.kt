package android.sunnyweather.com.sunnyweather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * Created by pp517 on 2020/5/29.
 */
class SunnyWeatherApplication : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        // 令牌值
        const val TOKEN = "BT4ZR2kOBZB3LgdJ"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}