package com.example.morningroutine.model

import java.io.Serializable

class Routine : Serializable {
    private var morningActivities: List<MorningActivity> = listOf()

    fun addMorningActivity(morningActivity: MorningActivity) {
        morningActivities = morningActivities + morningActivity
    }

    fun getMorningActivity(index: Int): MorningActivity {
        return morningActivities[index]
    }

    fun removeMorningActivity(morningActivity: MorningActivity) {
        morningActivities = morningActivities - morningActivity
    }

    fun size(): Int {
        return morningActivities.size
    }
}