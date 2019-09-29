package com.example.kiwifirst

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.kiwifirst.R.drawable.kiwicom
import java.util.*

internal class MyAdapter(private var flightArrayList: ArrayList<Flight>?, private val dateInAweek: String) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val context = viewGroup.context
        val inflater = LayoutInflater.from(context)

        val flightView = inflater.inflate(R.layout.flight_item, viewGroup, false)

        return MyViewHolder(flightView)
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
        val flight = flightArrayList!![i]

        val flyFromTextView = myViewHolder.flyFromTextView
        flyFromTextView.text = "FROM: " + flight.cityFrom

        val flyToTextView = myViewHolder.flyToTextView
        flyToTextView.text = "TO: " + flight.cityTo

        val priceTextView = myViewHolder.priceTextView
        val price = flight.price.toString()
        priceTextView.text = "$price EUR"

        val flyDateTextView = myViewHolder.flyDateTextView
        flyDateTextView.text = dateInAweek

        val imageView = myViewHolder.cityImageView
        imageView.setImageResource(kiwicom)
    }

    override fun getItemCount(): Int {
        return flightArrayList!!.size
    }

    fun setflight(flightArrayList: ArrayList<Flight>?) {
        this.flightArrayList = flightArrayList
        notifyDataSetChanged()
    }

    class MyViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val flyFromTextView: TextView
        val flyToTextView: TextView
        val flyDateTextView: TextView
        val priceTextView: TextView
        val cityImageView: ImageView

        init {
            itemView.tag = this

            flyFromTextView = itemView.findViewById(R.id.textView_flyFrom)
            flyToTextView = itemView.findViewById(R.id.textView_flyTo)
            priceTextView = itemView.findViewById(R.id.textView_price)
            flyDateTextView = itemView.findViewById(R.id.textView_flyDate)
            cityImageView = itemView.findViewById(R.id.imageView)
        }
    }
}
