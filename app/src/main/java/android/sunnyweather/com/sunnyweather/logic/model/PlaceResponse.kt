package android.sunnyweather.com.sunnyweather.logic.model

import com.google.gson.annotations.SerializedName

/**
 * Created by pp517 on 2020/5/29.
 */

// 返回的状态和地方
data class PlaceResponse(val status: String, val places: List<Place>)

//地方
data class Place(val name: String, val location: Location, @SerializedName("formatted_address") val address: String)

// 经纬度
data class Location(val lng: String, val lat: String)