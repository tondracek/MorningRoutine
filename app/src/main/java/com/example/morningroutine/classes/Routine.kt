package com.example.morningroutine.classes

import android.content.Context
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.morningroutine.R
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable

class Routine(var activities: List<MorningActivity> = arrayListOf()) : Serializable {
    companion object {
        private const val FILENAME = "routine.bin"
        fun loadRoutine(context: Context): Routine {
            val file = File(context.filesDir, FILENAME)

            try {
                ObjectInputStream(FileInputStream(file)).use {
                    return it.readObject() as Routine
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Failed loading your routine.", Toast.LENGTH_SHORT).show()
                println(e)
                file.createNewFile()
//                return Routine()
                return defaultRoutine
            }
        }

        fun saveRoutine(routine: Routine, context: Context) {
            println("SAVING")

            val file = File(context.filesDir, FILENAME)
            if (!file.exists()) {
                file.createNewFile()
            }

            try {
                ObjectOutputStream(FileOutputStream(file)).use {
                    it.writeObject(routine)
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Failed saving your routine.", Toast.LENGTH_SHORT).show()
                println(e)
            }
        }

        private var defaultRoutine: Routine = Routine(
            arrayListOf(
                MorningActivity(
                    name = "Wash your face",
                    icon = R.drawable.sink,
                    containerColor = Color(0, 188, 212, 255).toArgb(),
                ),
                MorningActivity(
                    name = "Beard oil",
                    icon = R.drawable.skincare,
                    containerColor = Color(255, 235, 59, 255).toArgb(),
                ),
                MorningActivity(
                    name = "Vitamins + creatine",
                    icon = R.drawable.suplements,
                    containerColor = Color(139, 195, 74, 255).toArgb(),
                ),
                MorningActivity(
                    name = "Coffee",
                    icon = R.drawable.coffee,
                    containerColor = Color(228, 162, 80, 255).toArgb(),
                ),
                MorningActivity(
                    name = "Bathroom",
                    icon = R.drawable.toilet,
                    containerColor = Color(33, 150, 243, 255).toArgb(),
                ),
                MorningActivity(
                    name = "Breakfast",
                    icon = R.drawable.breakfast,
                    containerColor = Color(255, 87, 34, 255).toArgb(),
                )
            )
        )
    }
}