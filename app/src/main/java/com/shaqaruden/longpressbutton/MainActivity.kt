package com.shaqaruden.longpressbutton

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.shaqaruden.longpressbutton.ui.theme.FaceDetectionTheme
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FaceDetectionTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        ProgressButton(modifier = Modifier.padding(16.dp), label = { Text("Click Me") }) {
                            Log.d("ProgressButton", "Progress finished")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProgressButton(
    modifier: Modifier = Modifier,
    label: @Composable () -> Unit = {},
    color: Color = Color.Blue,
    trackColor: Color = Color.LightGray,
    onProgressFinish: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    var progress by remember { mutableStateOf(0f) }

    if (isPressed){
        if(progress < 1f) progress += 0.01f

        //Use if + DisposableEffect to wait for the press action is completed
        DisposableEffect(Unit) {
            onDispose {
                if(progress >= 1f) {
                    onProgressFinish()
                }
                if(progress < 1f) progress = 0f
            }
        }
    }

    Box(
        modifier
            .height(50.dp)
            .fillMaxWidth(0.8f)) {
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(50.dp)),
            color = color,
            trackColor = trackColor
        )
        Button(
            modifier = Modifier.matchParentSize(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            onClick = {},
            interactionSource = interactionSource
        ){
            label()
        }
    }
}