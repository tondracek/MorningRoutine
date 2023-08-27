package com.example.morningroutine.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.zIndex
import com.example.morningroutine.R
import com.example.morningroutine.classes.MorningActivity
import com.example.morningroutine.ui.theme.AppTheme

@Composable
fun MorningActivityEdit(
    modifier: Modifier = Modifier,
    activity: MorningActivity,
    moveDownInList: () -> Unit,
    moveUpInList: () -> Unit,
) {
    val openEditPopup = remember {
        mutableStateOf(false)
    }

    if (openEditPopup.value) {
        EditActivityPopup(
            activity = activity,
            openEditPopup = openEditPopup,
        )
    }

    Card(
        modifier = modifier.clickable {
            openEditPopup.value = true
        },
        colors = CardDefaults.cardColors(
            containerColor = activity.getContainerColor(),
            contentColor = activity.getContentColor(),
        ),
        elevation = CardDefaults.cardElevation(16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val swapPositionPopup = remember {
                mutableStateOf(false)
            }
            IconButton(
                onClick = {
                    swapPositionPopup.value = true
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.outline_swap_vert_24),
                    contentDescription = "Swap activity order"
                )
                if (swapPositionPopup.value) {
                    Popup(
                        alignment = Alignment.Center,
                        onDismissRequest = { swapPositionPopup.value = false },
                    ) {
                        Card(
                            modifier = modifier
                                .zIndex(1f)
                                .wrapContentSize(unbounded = true)
                        ) {
                            Column {
                                IconButton(onClick = {
                                    moveDownInList()
                                    swapPositionPopup.value = false
                                }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.outline_arrow_drop_up_24),
                                        contentDescription = "Move activity up"
                                    )
                                }
                                IconButton(onClick = {
                                    moveUpInList()
                                    swapPositionPopup.value = false
                                }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.outline_arrow_drop_down_24),
                                        contentDescription = "Move activity down"
                                    )
                                }
                            }
                        }
                    }
                }
            }
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
fun EditActivityPopup(
    activity: MorningActivity,
    openEditPopup: MutableState<Boolean>,
) {
    // TODO
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
                containerColor = Color(129, 252, 129, 255).toArgb(),
            ),
            moveDownInList = {},
        ) {}
    }
}