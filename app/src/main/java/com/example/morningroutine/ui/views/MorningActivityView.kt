package com.example.morningroutine.ui.views

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.morningroutine.model.MorningActivity
import com.example.morningroutine.ui.components.MorningActivityIcon

/**
 * This is a morning activity composable view.
 * It displays the icon and name
 * After clicking on it, it changes the done state and its color to disabled color.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MorningActivityView(
    modifier: Modifier = Modifier,
    morningActivity: MorningActivity,
    onDoneChange: (Boolean) -> Unit,
) {
    val disabledColor: Color = Color(0xFFBDBDBD)
    val done by remember {
        mutableStateOf(morningActivity.done)
    }

    Card(
        modifier = modifier
            .padding(8.dp)
            .aspectRatio(1f),
        colors = CardDefaults.cardColors(
            morningActivity.containerColor,
            morningActivity.contentColor,
            disabledColor
        ),
        enabled = !done,
        onClick = { onDoneChange(!morningActivity.done) },
        elevation = CardDefaults.cardElevation(32.dp),
    ) {
        MorningActivityIcon(
            modifier = Modifier.weight(1f),
            morningActivity = morningActivity
        )
        Text(
            text = morningActivity.name,
            color = morningActivity.contentColor,
        )
    }
}