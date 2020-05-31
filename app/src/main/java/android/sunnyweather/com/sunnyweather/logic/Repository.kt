package android.sunnyweather.com.sunnyweather.logic

import android.database.CursorJoiner
import android.sunnyweather.com.sunnyweather.logic.model.Place
import android.sunnyweather.com.sunnyweather.logic.network.SunnyWeatherNetwork
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

import javax.xml.transform.Result

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