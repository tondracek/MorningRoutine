package com.example.morningroutine.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.morningroutine.R
import com.example.morningroutine.ui.theme.AppTheme

@Composable
fun ActivityIconsPopup(
    iconID: MutableState<Int>,
    openEditPopup: MutableState<Boolean>,
    selectedColor: Color,
) {
    val iconIDs = arrayOf(R.drawable.baseline_android_24)

    var newID: Int by remember {
        mutableIntStateOf(iconID.value)
    }

    AlertDialog(
        onDismissRequest = {
            openEditPopup.value = false
        },
        confirmButton = {
            Button(onClick = {
                iconID.value = newID
                openEditPopup.value = false
            }) {
                Text(text = "Choose")
            }
        },
        dismissButton = {
            Button(onClick = {
                openEditPopup.value = false
            }) {
                Text(text = "Cancel")
            }
        },
        text = {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(50.dp),
            ) {
                items(iconIDs) {
                    IconButton(onClick = {
                        newID = it
                    }) {
                        Icon(
                            modifier = Modifier
                                .padding(4.dp)
                                .background(
                                    if (newID == it) {
                                        selectedColor
                                    } else {
                                        Color.Transparent
                                    }
                                ),
                            painter = painterResource(id = it),
                            contentDescription = "Icon $it"
                        )
                    }
                }
            }
        }
    )
}

@Preview
@Composable
fun ActivityIconsPopupPrev() {
    AppTheme {
        ActivityIconsPopup(
            iconID = remember {
                mutableIntStateOf(R.drawable.baseline_android_24)
            },
            openEditPopup = remember {
                mutableStateOf(true)
            },
            selectedColor = Color.Cyan,
        )
    }
}
