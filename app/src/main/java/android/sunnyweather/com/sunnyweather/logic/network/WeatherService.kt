package android.sunnyweather.com.sunnyweather.logic.network

import android.sunnyweather.com.sunnyweather.SunnyWeatherApplication
import android.sunnyweather.com.sunnyweather.logic.model.DailyResponse
import android.sunnyweather.com.sunnyweather.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherService {
    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealTimeWeather(@Path("lng") lng: String, @Path("lat") lat: String): Call<RealtimeResponse>

    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng") lng:String, @Path("lat") lat:String): Call<DailyResponse>
}