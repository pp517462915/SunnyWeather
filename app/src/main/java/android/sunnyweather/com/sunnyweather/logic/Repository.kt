package android.sunnyweather.com.sunnyweather.logic

import android.database.CursorJoiner
import android.sunnyweather.com.sunnyweather.logic.dao.PlaceDao
import android.sunnyweather.com.sunnyweather.logic.model.Place
import android.sunnyweather.com.sunnyweather.logic.model.Weather
import android.sunnyweather.com.sunnyweather.logic.network.SunnyWeatherNetwork
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext




/**
 * Created by pp517 on 2020/5/30.
 */
object Repository {

    fun searchPlaces(query: String) = fire(Dispatchers.IO){
        val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
        if (placeResponse.status == "ok") {
            val places = placeResponse.places
            kotlin.Result.success(places)
        } else {
            kotlin.Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }

    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO){
        coroutineScope {
            val deferredRealtime = async {
                SunnyWeatherNetwork.getRealtimeWeather(lng, lat)
            }
            val deferredDaliy = async {
                SunnyWeatherNetwork.getDailyWeather(lng, lat)
            }
            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaliy.await()
            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok"){
                val weather = Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                Result.success(weather)
            }else{
                Result.failure(
                        RuntimeException("realtime response status is ${realtimeResponse.status}" +
                                "daliy response status is ${dailyResponse.status}")
                )
            }
        }
    }

    fun savePlace(place: Place) = PlaceDao.savePlace(place)

    fun getSavedPlace() = PlaceDao.getSavedPlace()

    fun isPlaceSaved() = PlaceDao.isPlaceSaved()


    private fun <T> fire(context: CoroutineContext, block: suspend () -> kotlin.Result<T>) =
            liveData<kotlin.Result<T>>(context) {
                val result = try {
                    block()
                } catch (e: Exception) {
                    kotlin.Result.failure<T>(e)
                }
                emit(result)
            }
}