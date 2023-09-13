package com.example.morningroutine.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.morningroutine.R
import com.example.morningroutine.classes.MorningActivity
import com.example.morningroutine.ui.theme.AppTheme
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController


@Composable
fun EditActivityPopup(
    activity: MorningActivity,
    openEditPopup: MutableState<Boolean>,
) {
    var newName by remember {
        mutableStateOf(activity.name)
    }

    val newIcon: MutableState<Int> = remember {
        mutableIntStateOf(activity.getIcon())
    }

    val colorPickerController = rememberColorPickerController()
    colorPickerController.setWheelColor(Color.Black)

    val openActivityIconsPopup = remember {
        mutableStateOf(false)
    }

    AlertDialog(
        title = {
            Text(text = "Edit ${activity.name} activity")
        },
        onDismissRequest = {
            openEditPopup.value = false
        },
        confirmButton = {
            Button(onClick = {
                activity.name = newName
                activity.setContainerColor(colorPickerController.selectedColor.value.toArgb())
                activity.setIcon(newIcon.value)

                openEditPopup.value = false
            }) {
                Text(text = "Confirm")
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
            Column {
                Row(
                    modifier = Modifier.background(colorPickerController.selectedColor.value),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    TextField(
                        modifier = Modifier.weight(1f),
                        value = newName,
                        onValueChange = {
                            newName = it
                        },
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = colorPickerController.selectedColor.value,
                            unfocusedContainerColor = colorPickerController.selectedColor.value,
                        ),
                    )

                    IconButton(
                        onClick = {
                            openActivityIconsPopup.value = true
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = newIcon.value),
                            contentDescription = "New icon for the activity"
                        )
                    }
                }

                HsvColorPicker(
                    modifier = Modifier
                        .height(300.dp)
                        .padding(16.dp),
                    controller = colorPickerController,
                    initialColor = activity.getContainerColor()
                )
            }
        }
    )

    if (openActivityIconsPopup.value) {
        ActivityIconsPopup(
            iconID = newIcon,
            openEditPopup = openActivityIconsPopup,
            selectedColor = colorPickerController.selectedColor.value,
        )
    }
}

@Preview
@Composable
fun EditActivityPopupPreview() {
    AppTheme {
        EditActivityPopup(
            activity = MorningActivity(
                "Preview",
                R.drawable.coffee,
                Color.White.toArgb(),
                Color.Black.toArgb()
            ),
            openEditPopup = remember { mutableStateOf(false) }
        )
    }
}