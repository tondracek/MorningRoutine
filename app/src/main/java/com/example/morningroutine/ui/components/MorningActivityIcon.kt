package com.example.morningroutine.ui.components

import android.graphics.BitmapFactory
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import com.example.morningroutine.model.MorningActivity
import com.example.morningroutine.utils.assetsURL
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

@Composable
fun MorningActivityIcon(
    modifier: Modifier = Modifier,
    morningActivity: MorningActivity,
) {
    Icon(
        modifier = modifier,
        bitmap = loadBitmapFromURL(morningActivity.iconSource),
        contentDescription = morningActivity.name,
        tint = Color.White,
    )
}

fun loadBitmapFromURL(iconSource: String): ImageBitmap {
    val url = URL(iconSource)
    val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
    connection.doInput = true
    connection.connect()
    val input: InputStream = connection.inputStream
    return BitmapFactory.decodeStream(input).asImageBitmap()
}

@Preview
@Composable

fun MorningActivityIconPrev() {
    MorningActivityIcon(
        morningActivity = MorningActivity(
            name = "Test",
            iconSource = assetsURL("activity_icons/coffee.png"),
            containerColor = Color.Green,
        )
    )
}