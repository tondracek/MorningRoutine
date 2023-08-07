package com.example.morningroutine.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.morningroutine.R
import com.example.morningroutine.classes.MorningActivity
import com.example.morningroutine.ui.theme.AppTheme

@Composable
fun MorningActivityEdit(
    modifier: Modifier = Modifier,
    activity: MorningActivity
) {
    val openEditPopup = remember {
        mutableStateOf(false)
    }

    if (openEditPopup.value) {
        editActivityPopup(
            activity = activity,
            openEditPopup = openEditPopup,
        )
    }

    Card(
        modifier = modifier.clickable {
            openEditPopup.value = true
        },
        colors = CardDefaults.cardColors(
            containerColor = activity.containerColor,
            contentColor = activity.contentColor,
        ),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = activity.name,
            )
            Icon(
                painter = painterResource(id = activity.img),
                contentDescription = "Morning activity's image",
            )
        }
    }
}

@Composable
fun editActivityPopup(
    activity: MorningActivity,
    openEditPopup: MutableState<Boolean>,
) {

}

@Preview
@Composable
fun MorningActivityEditPrev() {
    AppTheme {
        MorningActivityEdit(
            modifier = Modifier
                .height(64.dp)
                .width(256.dp),
            activity = MorningActivity(
                name = "Meditation",
                img = R.drawable.meditation,
                containerColor = Color(129, 252, 129, 255),
            )
        )
    }
}