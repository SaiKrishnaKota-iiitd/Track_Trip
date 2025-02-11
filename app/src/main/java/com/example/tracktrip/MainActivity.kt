package com.example.tracktrip

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.xml_ui_layout)

        val stops = Readtext(context = this)
        var curprogress = 0
        var maxdist = 0
        var stopcount = 0
        for (ind in 0..stops.size - 1)
            if (ind != 0) maxdist += stops[ind].distFromSource

        val nextBtn = findViewById<Button>(R.id.nextStop)
        val unitBtn = findViewById<Button>(R.id.changeUnit)
        val progBar = findViewById<ProgressBar>(R.id.ProgBar)
        val distCoveredText = findViewById<TextView>(R.id.DistanceCovered)
        val distLeftText = findViewById<TextView>(R.id.DistanceLeft)
        val recyclerView = findViewById<RecyclerView>(R.id.stopList)
        var isMile = false

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = stopAdapter(stops)

        progBar.max = maxdist
        progBar.progress = curprogress
        distCoveredText.text = "At ${stops[0].stopName}, Distance Covered : $curprogress"
        distLeftText.text = "Distance Left : ${maxdist - curprogress}"

        nextBtn.setOnClickListener {
            if(stopcount<stops.size -1){
                stopcount++
//                if(stopcount != stops.size -1) stopcount++
                curprogress+=stops[stopcount].distFromSource
                progBar.progress = curprogress
                distCoveredText.text = " At ${stops[stopcount].stopName}, Distance Covered : $curprogress ${
                    if (isMile) "MILES" else "KM"
                }"
                distLeftText.text = " Distance Left : ${maxdist - curprogress} ${
                    if (isMile) "MILES" else "KM"
                }"
            }
            else{
                distLeftText.text = "Destination reached"
            }

        }

        unitBtn.setOnClickListener {
            isMile = !isMile
            if (stopcount < stops.size) {
                distCoveredText.text = " At ${stops[stopcount].stopName}, Distance Covered : $curprogress ${
                    if (isMile) "MILES" else "KM"
                }"
                distLeftText.text = " Distance Left : ${maxdist - curprogress} ${
                    if (isMile) "MILES" else "KM"
                }"
            }
        }




    }
}



data class Stop (val stopName : String,val distFromSource : Int,val visaRequired : Boolean)

class StopViewHolder(stopView : View) : RecyclerView.ViewHolder(stopView) {
    val stopDetails = itemView.findViewById<TextView>(R.id.textDetails)
}

class stopAdapter(val stopList: List<Stop>) : RecyclerView.Adapter<StopViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StopViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.stop_card, parent, false)
        return StopViewHolder(view)
    }

    override fun onBindViewHolder(holder: StopViewHolder, position: Int) {
        val stop = stopList[position]
        holder.stopDetails.text = "${stop.stopName} ${ if (stop.visaRequired) "(visa required)" else ""}"
    }

    override fun getItemCount() = stopList.size
}



fun Readtext (context: Context) : MutableList<Stop> {
    var curfile = context.assets.open("stops.txt")
    var curReader = BufferedReader(InputStreamReader(curfile))
    var tokens = listOf<String>()
    var stops = mutableListOf<Stop>()
    Log.i("successful","file read")
    curReader.readLines().forEach { textline ->
        tokens = textline.split(",")
        if (tokens.size == 3) {
            stops.add(
                Stop(
                    stopName = tokens[0],
                    distFromSource = tokens[1].toInt(),
                    visaRequired = if (tokens[2] == "yes") true else false
                )
            )
        }
    }
    return stops
}