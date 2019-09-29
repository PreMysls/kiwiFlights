package com.example.kiwifirst

import android.content.ContentValues.TAG
import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import java.util.*

internal class ParseJson {

    private val flights: ArrayList<Flight>? = ArrayList()

    private var flight: JSONObject? = null

    fun parse(json: String?): ArrayList<Flight>? {
        if (json == null || json.isEmpty()) {
            return null
        }
        try {
            val root = JSONObject(json)
            val dataArray = root.getJSONArray("data")
            val length = dataArray.length()
            for (i in 0 until length) {
                flight = dataArray.getJSONObject(i)
                val cityFrom = flight!!.getString("cityFrom")
                val cityTo = flight!!.getString("cityTo")
                val flyDate = flight!!.getInt("dTime")
                val id = flight!!.getString("id")
                val image = flight!!.getJSONArray("route").getJSONObject(0).getString("mapIdto")
                val price = flight!!.getInt("price")

                flights?.add(Flight(image, cityFrom, cityTo, price, flyDate))
            }
        } catch (e: JSONException) {
            Log.e(TAG, "parse: ", e)
        }

        return flights
    }
}
