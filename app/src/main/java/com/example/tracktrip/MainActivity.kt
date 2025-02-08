package com.example.tracktrip

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tracktrip.ui.theme.TrackTripTheme
import java.io.File

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrackTripTheme {
                Surface {
                    Greeting(name  = "android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TrackTripTheme {
        Greeting("Android")
    }
}

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