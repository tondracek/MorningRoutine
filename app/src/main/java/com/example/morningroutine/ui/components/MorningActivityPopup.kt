package com.example.morningroutine.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.morningroutine.classes.MorningActivity


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditActivityPopup(
    activity: MorningActivity,
    openEditPopup: MutableState<Boolean>,
) {
    var newName by remember {
        mutableStateOf(activity.name)
    }
    var newColor: Int by remember {
        mutableStateOf(0)
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
                activity.setContainerColor(newColor)

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
                TextField(
                    value = newName,
                    onValueChange = {
                        newName = it
                    }
                )
            }
        }
    )
}