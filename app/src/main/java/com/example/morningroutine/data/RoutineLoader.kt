package com.example.morningroutine.data

import android.content.Context
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import com.example.morningroutine.model.MorningActivity
import com.example.morningroutine.model.Routine
import com.example.morningroutine.utils.assetsURL
import com.google.gson.Gson
import java.io.File


/***
 * This is a static class for loading and saving the routine into json.
 * This uses the Gson library.
 */
object RoutineLoader {
    private const val FILENAME = "routine.json"

    fun loadRoutine(context: Context): Routine {
        val file = File(context.filesDir, FILENAME)

        return try {
            val routineJson = file.readText()
            Gson().fromJson(routineJson, Routine::class.java)
        } catch (e: Exception) {
            Toast.makeText(context, "Failed loading your routine.", Toast.LENGTH_SHORT).show()
            println(e)
            file.createNewFile()

            defaultRoutine
        }
    }

    fun saveRoutine(routine: Routine, context: Context) {
        println("SAVING")

        val file = File(context.filesDir, FILENAME)
        if (!file.exists()) {
            file.createNewFile()
        }

        try {
            val routineJson = Gson().toJson(routine)
            file.writeText(routineJson)
        } catch (e: Exception) {
            Toast.makeText(context, "Failed saving your routine.", Toast.LENGTH_SHORT).show()
            println(e)
        }
    }

    private var defaultRoutine: Routine = Routine()

    init {
        defaultRoutine.addMorningActivity(
            MorningActivity(
                name = "Wash your face",
                iconSource = assetsURL("activity_icons/facial-massage.png"),
                containerColor = Color(0, 188, 212, 255),
            )
        )
        defaultRoutine.addMorningActivity(
            MorningActivity(
                name = "Beard oil",
                iconSource = assetsURL("activity_icons/facial-massage.png"),
                containerColor = Color(255, 152, 0, 255),
            )
        )
        defaultRoutine.addMorningActivity(
            MorningActivity(
                name = "Brush your teeth",
                iconSource = assetsURL("activity_icons/toothbrush.png"),
                containerColor = Color(255, 255, 255, 255),
            )
        )
        defaultRoutine.addMorningActivity(
            MorningActivity(
                name = "Drink water",
                iconSource = assetsURL("activity_icons/water.png"),
                containerColor = Color(0, 150, 136, 255),
            )
        )
        defaultRoutine.addMorningActivity(
            MorningActivity(
                name = "Drink coffee",
                iconSource = assetsURL("activity_icons/coffee.png"),
                containerColor = Color(121, 85, 72, 255),
            )
        )
        defaultRoutine.addMorningActivity(
            MorningActivity(
                name = "Eat breakfast",
                iconSource = assetsURL("activity_icons/breakfast.png"),
                containerColor = Color(255, 152, 0, 255),
            )
        )
        defaultRoutine.addMorningActivity(
            MorningActivity(
                name = "Take vitamins and creatine",
                iconSource = assetsURL("activity_icons/dietary-supplement.png"),
                containerColor = Color(156, 39, 176, 255),
            )
        )
    }
}