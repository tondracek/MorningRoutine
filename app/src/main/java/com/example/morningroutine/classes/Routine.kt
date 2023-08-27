package com.example.morningroutine.classes

import android.content.Context
import androidx.compose.ui.graphics.Color
import com.example.morningroutine.R
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable

class Routine(var activities: ArrayList<MorningActivity> = arrayListOf()) : Serializable {
    fun add(morningActivity: MorningActivity): Routine {
        activities.add(morningActivity)
        return this
    }

    companion object {
        private const val FILENAME = "routine.bin"
        fun loadRoutine(context: Context): Routine {
            val file = File(context.filesDir, FILENAME)

            try {
                ObjectInputStream(FileInputStream(file)).use {
                    return it.readObject() as Routine
                }
            } catch (e: Exception) {
                println(e)
                file.createNewFile()
//                return Routine()
                return defaultRoutine
            }
        }

        fun saveRoutine(routine: Routine, context: Context) {
            val file = File(context.filesDir, FILENAME)
            if (!file.exists()) {
                file.createNewFile()
            }

            ObjectOutputStream(FileOutputStream(file)).use {
                it.writeObject(routine)
                println(
                    """
                    ----------------------
                    Successfully saved
                    ----------------------
                    """.trimIndent()
                )
            }
        }

        private var defaultRoutine: Routine = Routine()
            .add(
                MorningActivity(
                    name = "Wash your face",
                    img = R.drawable.sink,
                    containerColor = Color(0, 188, 212, 255),
                )
            )
            .add(
                MorningActivity(
                    name = "Beard oil",
                    img = R.drawable.skincare,
                    containerColor = Color(255, 235, 59, 255),
                )
            )
            .add(
                MorningActivity(
                    name = "Vitamins + creatine",
                    img = R.drawable.suplements,
                    containerColor = Color(139, 195, 74, 255),
                )
            )
            .add(
                MorningActivity(
                    name = "Coffee",
                    img = R.drawable.coffee,
                    containerColor = Color(228, 162, 80, 255),
                )
            )
            .add(
                MorningActivity(
                    name = "Bathroom",
                    img = R.drawable.toilet,
                    containerColor = Color(33, 150, 243, 255),
                )
            )
            .add(
                MorningActivity(
                    name = "Breakfast",
                    img = R.drawable.breakfast,
                    containerColor = Color(255, 87, 34, 255),
                )
            )
    }
}