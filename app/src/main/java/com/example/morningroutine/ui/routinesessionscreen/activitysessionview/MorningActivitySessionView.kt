package com.example.morningroutine.ui.routinesessionscreen.activitysessionview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.morningroutine.R
import com.example.morningroutine.domain.model.MorningActivity
import com.example.morningroutine.ui.components.IconResource
import com.example.morningroutine.ui.components.IconView

@Composable
fun MorningActivitySessionView(
    modifier: Modifier = Modifier,
    state: MorningActivity,
    onClick: () -> Unit,
) {

    Card(
        modifier = modifier
            .alpha(if (state.isDone) 0.5f else 1f),
        elevation = CardDefaults.cardElevation(8.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = state.containerColor,
            contentColor = state.contentColor
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .aspectRatio(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            IconView(
                Modifier
                    .weight(1f)
                    .padding(8.dp),
                icon = state.iconSource
            )

            Text(
                text = state.name,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Preview
@Composable
fun MorningActivitySessionViewPreview() {
    MorningActivitySessionView(
        modifier = Modifier.size(200.dp),
        state = MorningActivity(
            name = "Morning Activity",
            iconSource = IconResource(R.drawable.icon_sun),
            containerColor = Color.Yellow
        ),
        onClick = {}
    )
}