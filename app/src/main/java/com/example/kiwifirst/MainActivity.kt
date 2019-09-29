package com.example.kiwifirst

import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<String> {
    private var newUrl: String? = null

    private var flightArrayList: ArrayList<in Flight>? = null
    private var myAdapter: MyAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val localDate = LocalDate.now().plusWeeks(1)
        val dateInWeek = dtf.format(localDate)

        newUrl = newUrlPart1 + dateInWeek + newUrlPart2 + dateInWeek + newUrlPart3
        flightArrayList = ArrayList()
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        myAdapter = MyAdapter(flightArrayList as ArrayList<Flight>, dateInWeek)

        recyclerView.adapter = myAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        if (true) {
            LoaderManager.getInstance(this).initLoader(FLIGHT_LOADER_ID, null, this)
        } else {
            val textView = findViewById<TextView>(R.id.no_connection_textView)
            textView.text = getString(R.string.noConnection)
        }
    }

    override fun onCreateLoader(i: Int, bundle: Bundle?): Loader<String> {
        return FlightLoader(this, newUrl)
    }

    override fun onLoadFinished(loader: Loader<String>, s: String?) {
        val parseJson = ParseJson()
        if (s == null || s.length == 0) {
            //
        } else {
            flightArrayList = parseJson.parse(s)
            myAdapter!!.setflight(flightArrayList as ArrayList<Flight>?)
        }
    }

    override fun onLoaderReset(loader: Loader<String>) {
        myAdapter!!.notifyDataSetChanged()
    }

    companion object {
        private val FLIGHT_LOADER_ID = 22

        private val newUrlPart1 = "https://api.skypicker.com/flights?v=2&sort=popularity&asc=0&locale=en&daysInDestinationFrom=&daysInDestinationTo=&affilid=&children=0&infants=0&flyFrom=49.2-16.61-250km&to=anywhere&featureName=aggregateResults&dateFrom="
        private val newUrlPart2 = "&dateTo="
        private val newUrlPart3 = "&typeFlight=oneway&returnFrom=&returnTo=&one_per_date=0&oneforcity=1&wait_for_refresh=0&adults=1&limit=5&partner=picky"
    }
}