package com.example.tracktrip

import android.graphics.fonts.Font
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowRowScopeInstance.align
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tracktrip.ui.theme.TrackTripTheme
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.InputStreamReader

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrackTripTheme {
                Surface {
//                    StopList(stopList = mutableListOf("stop1","stop2","stop3","stop4","stop5"))
                    StopList()
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
    color : Color = MaterialTheme.colorScheme.background
) {
    var tempList = mutableListOf("stop1","stop2","stop3","stop4","stop5")
//    StopList(stopList = tempList )
    StopList()
}

@Composable
fun StopList(
    modifier : Modifier = Modifier
        .statusBarsPadding()
        .fillMaxSize()
        .padding(top = 40.dp),
    color : Color = Color.Black,
//    stopList : MutableList<String>,
) {
    val curcontext = LocalContext.current
    var totalDistance = 0
    var curprogress  =  remember { mutableStateOf(0) }
    var stopCount = remember { mutableStateOf(0) }
    var stopList = remember { Readtext(context = curcontext) }
    var isKM = remember { mutableStateOf(true) }
    for (i in 1..stopList.size-1) totalDistance += stopList[i].distFromSource
    var nextStop : () -> Unit = {
        if (stopCount.value < stopList.size ) {
//            for (i in 0..stopCount.value) {
                curprogress.value += stopList[stopCount.value].distFromSource
//            }
            Log.i("curprog","$curprogress")
            stopCount.value++
        }
        else stopCount.value
    }
    Column (
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 5.dp)
            .fillMaxSize()
//            .align(Alignment.CenterVertically)
    ) {
        if (stopList.size >3) {
            LazyColumn (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 16.dp)
                    .background(color = Color(0xff1d2024), shape = RoundedCornerShape(6))
                    .weight(1f),
//                    .align(alignment = Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,

            ) {
                itemsIndexed(stopList) { ind,curstop ->
//                    Text(text = "stop $ind",
//                        modifier  = Modifier
//                            .padding(bottom = 16.dp),
//                        color = if(ind <= stopCount.value) Color.Red else Color.Blue
//                    )
                    Box(modifier = Modifier
                        .width(300.dp)
                        .padding(top = 15.dp)
                    ) {
                        Text("${curstop.stopName}",
                            color = if (ind <= stopCount.value) Color(0xffdae2f9) else Color(0xffaac7ff),
                            textAlign = TextAlign.Center,
//                            fontFamily = FontFamily(fonts = sa),
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.ExtraBold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = Color(0xff3e4759),
                                    shape = RoundedCornerShape(60)
                                )
                                .padding(vertical = 10.dp)

                        )
                    }
                }
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
                for ( i in 0..stopList.size-1) {
                    Box(modifier = Modifier.background(color = Color(0xff284777))) {
                        Text("${stopList[i].stopName}",
                            color = if (i <= stopCount.value) Color.Red else Color.Blue
                        )
                    }
                }
            }
        }
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xff1d2024), shape = RoundedCornerShape(12))
                .padding(vertical = 20.dp, horizontal = 36.dp),
//                    .align(alignment = Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            LinearProgressIndicator(
                progress = { curprogress.value/totalDistance.toFloat() },
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally),
            )
            Spacer(modifier = Modifier.size(20.dp))
            Text(
                text = "Distance Covered : ${if (isKM.value) curprogress.value else curprogress.value*2}",
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.size(20.dp))
            Text(
                text = "Distance left : ${if(isKM.value) (totalDistance - curprogress.value) else (totalDistance - curprogress.value)*2}"
            )
        }

        Spacer(modifier = Modifier.size(20.dp))
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { isKM.value = !isKM.value },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xffddbce0)
                )
            ) {
                Text(
                    text = "change units",
                    color = Color(0xff3f2844)
                )
            }
            Spacer (modifier = Modifier.size(16.dp))
            Button(
                onClick = nextStop,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xffddbce0)
                )
            ) {
                Text(
                    text = "next stop reached",
                    color = Color(0xff3f2844)
                )
            }
        }

    }
}

data class Stop (val stopName : String,val distFromSource : Int,val visaRequired : Boolean)

fun ReadAndSetStops() : MutableList<Stop> {
    val fileName  = "assets/stops.csv"
    var stops = mutableListOf<Stop>()
    var file = File(fileName)
//    var newfile = Context.
    var lines = file.readLines()
    var tokens = listOf<String>()
    lines.forEach {textlist ->
        tokens = textlist.split(",")
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

    var inp =
    return stops
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