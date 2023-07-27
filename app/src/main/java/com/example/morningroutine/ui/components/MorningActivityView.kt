package com.example.morningroutine.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.morningroutine.R
import com.example.morningroutine.classes.MorningActivity
import com.example.morningroutine.ui.theme.AppTheme

@Composable
fun MorningActivityView(
    modifier: Modifier = Modifier,
    activity: MorningActivity,
    updateParent: () -> Unit
) {
    val done = remember {
        mutableStateOf(activity.done)
    }

    Card(
        modifier = modifier
            .aspectRatio(1f)
            .clickable {
                done.value = !done.value
                updateParent()
            },
        colors =
        if (done.value) {
            CardDefaults.cardColors(MorningActivity.disabledColor)
        } else {
            CardDefaults.cardColors(activity.color)
        },
        elevation = CardDefaults.cardElevation(32.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.weight(1f),
                painter = painterResource(id = activity.img),
                contentDescription = activity.name,
                colorFilter = ColorFilter.tint(activity.contentColor),
            )
            Text(
                modifier = Modifier.padding(4.dp),
                text = activity.name,
                style = TextStyle(
                    color = activity.contentColor,
                    fontWeight = FontWeight.Bold
                ),
            )
        }
    }
}


@Preview
@Composable
fun MorningActivityViewPrev() {
    AppTheme {
        MorningActivityView(
            activity = MorningActivity(
                name = "Meditation",
                img = R.drawable.meditation,
                color = Color(129, 252, 129, 255),
            )
        ) {}
    }
}
