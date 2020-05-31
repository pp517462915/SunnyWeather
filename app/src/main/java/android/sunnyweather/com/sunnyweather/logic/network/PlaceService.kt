package android.sunnyweather.com.sunnyweather.logic.network

import android.sunnyweather.com.sunnyweather.SunnyWeatherApplication
import android.sunnyweather.com.sunnyweather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by pp517 on 2020/5/29.
 */
interface PlaceService {
    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>
}