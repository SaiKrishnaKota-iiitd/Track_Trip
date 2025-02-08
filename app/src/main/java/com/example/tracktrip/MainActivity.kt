package com.example.tracktrip

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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




@Preview( showBackground = false)
@Composable
fun TripAppPreview (
    modifier : Modifier = Modifier.statusBarsPadding(),
    color : Color = MaterialTheme.colorScheme.background
) {
    var tempList = mutableListOf("stop1","stop2","stop3","stop4","stop5")
    StopList(stopList = tempList )
}

@Composable
fun StopList(
    modifier : Modifier = Modifier.fillMaxSize(),
    color : Color = Color.Black,
    stopList : MutableList<String>,
) {
    Column (
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
//        Alignment = Alignment.
    ) {
        if (stopList.size >3) {
            LazyColumn (
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(stopList) {stopName -> Text(text = stopName) }
            }
        }
        else {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                stopList.forEach { stopName -> Text(text = stopName) }
            }
        }
        LinearProgressIndicator(
            progress = { 0.5f },
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally),
        )
        Spacer(modifier = Modifier.size(20.dp))
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { Log.i("button clicked","in stopList")},
                modifier = Modifier
            ) {
                Text(
                    text = "change units"
                )
            }
            Spacer (modifier = Modifier.size(16.dp))
            Button(
                onClick = { Log.i("button clicked","in stopList")},
                modifier = Modifier
            ) {
                Text(
                    text = "next stop reached"
                )
            }
        }

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