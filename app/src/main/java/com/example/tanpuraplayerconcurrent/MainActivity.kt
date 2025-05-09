package com.example.tanpuraplayerconcurrent

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.tanpuraplayerconcurrent.ui.theme.TanpuraPlayerConcurrentTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TanpuraPlayerConcurrentTheme {
                AudioLooper(
                    1200L,
                    mutableListOf(
                        R.raw.a4,
                        R.raw.d5,
                        R.raw.d4
                    )
                )
            }
        }
    }
}

@Composable
fun AudioLooper(interval: Long, audioIds: List<Int>) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        while (true) {
            context.playAudio(audioIds[0], scope, interval * 8)
            delay(interval)
            context.playAudio(audioIds[1], scope, interval * 6)
            delay(interval)
            context.playAudio(audioIds[1], scope, interval * 4)
            delay(interval)
            context.playAudio(audioIds[2], scope, interval * 2)
            delay(interval)
        }
    }
}

fun Context.playAudio(
    id: Int,
    scope: CoroutineScope,
    stopAfter: Long,
) {
    val player = MediaPlayer.create(this, id)
    scope.launch {
        player.start()
        delay(stopAfter)
        player.stop()
        player.release()
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
    TanpuraPlayerConcurrentTheme {
        Greeting("Android")
    }
}