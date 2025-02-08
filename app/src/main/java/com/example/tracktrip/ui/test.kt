package com.example.tracktrip.ui

import android.util.Log
import java.io.File

fun ReadAndSetStops() {
    val fileName  = "../res/stops.txt"
    var stopList : List<String>? = File(fileName).useLines { it.toList() }
    var stopDist : List<Float>? = null
    if (stopList != null) {
        for (i in stopList) {
            Log.i("stop is :",i)
        }
    }
}

fun main() {
    ReadAndSetStops()
}