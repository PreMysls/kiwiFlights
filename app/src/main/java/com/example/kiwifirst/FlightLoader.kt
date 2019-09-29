package com.example.kiwifirst

import android.content.Context
import android.support.v4.content.AsyncTaskLoader
import android.util.Log

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

internal class FlightLoader(context: Context, private val url: String?) : AsyncTaskLoader<String>(context) {

    init {
        val context1 = context
        Log.e(TAG, "NewsLoader: Started")
    }

    override fun onStartLoading() {
        forceLoad()
    }

    override fun loadInBackground(): String? {
        if (url!!.length < 1 || url == null) {
            return null
        }
        val result = StringBuilder()
        var bufferedReader: BufferedReader? = null
        try {
            val newUrl = URL(url)
            val myConnection = newUrl.openConnection() as HttpURLConnection
            if (myConnection.responseCode == 200) {
                bufferedReader = BufferedReader(InputStreamReader(myConnection.inputStream))

                var charsRead = bufferedReader.read()
                while (charsRead > 0) {
                    result.append(charsRead.toChar())
                    charsRead = bufferedReader.read()
                }
                bufferedReader.close()
                Log.e(TAG, "loadInBackground: OK" + result.length)
                return result.toString()
            } else {
                Log.e(TAG, "Error response code: " + myConnection.responseCode)
            }
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    override fun deliverResult(data: String?) {
        super.deliverResult(data)
    }

    companion object {

        private val TAG = "NewsLoader"
    }
}
