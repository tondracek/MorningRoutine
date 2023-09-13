package com.example.morningroutine.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.morningroutine.classes.MorningActivity
import com.example.morningroutine.ui.theme.AppTheme
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditActivityPopup(
    activity: MorningActivity,
    openEditPopup: MutableState<Boolean>,
) {
    var newName by remember {
        mutableStateOf(activity.name)
    }

    val colorPickerController = rememberColorPickerController()
    colorPickerController.setWheelColor(Color.Black)

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
                Row {
                    TextField(
                        value = newName,
                        onValueChange = {
                            newName = it
                        },
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = colorPickerController.selectedColor.value,
                            unfocusedContainerColor = colorPickerController.selectedColor.value,
                        )
                    )
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
}

@Preview
@Composable
fun EditActivityPopupPreview() {
    AppTheme {
        EditActivityPopup(
            activity = MorningActivity("Preview", 0, Color.White.toArgb(), Color.Black.toArgb()),
            openEditPopup = remember { mutableStateOf(false) }
        )
    }
}