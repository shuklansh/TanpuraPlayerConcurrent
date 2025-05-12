package com.example.tanpuraplayerconcurrent

import android.content.Context
import android.media.MediaPlayer
import android.media.PlaybackParams
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.example.tanpuraplayerconcurrent.ui.theme.TanpuraPlayerConcurrentTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.pow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TanpuraPlayerConcurrentTheme {
                AudioLooper(
                    2000L,
                    mutableListOf(
                        R.raw.c_sp_3_pa,
                        R.raw.c_sp_3_sa
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
            context.playAudio(
                audioIds[1], scope, interval * 8,
                PlaybackParams().setPitch(2f.pow(-11f/12))
            )
            delay(interval)
            context.playAudio(
                audioIds[1], scope, interval,
                PlaybackParams().setPitch(2f.pow(-4f/12))
            )
            delay(interval / 2)
            context.playAudio(
                audioIds[1], scope, interval * 4,
                PlaybackParams().setPitch(2f.pow(-4f/12))
            )
            delay(interval)
            context.playAudio(
                audioIds[1],
                scope,
                interval * 2,
                PlaybackParams().setPitch(2f.pow(-16f/12))
            )
            delay(interval)
        }
    }
}

fun Context.playAudio(
    id: Int,
    scope: CoroutineScope,
    stopAfter: Long,
    playbackParams: PlaybackParams = PlaybackParams()
) {
    val player = MediaPlayer.create(this, id)
    player.playbackParams = playbackParams
    scope.launch {
        player.start()
        delay(stopAfter)
        player.stop()
        player.release()
    }
}
