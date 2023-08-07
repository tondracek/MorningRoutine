package com.example.morningroutine.classes

class Routine(var activities: ArrayList<MorningActivity> = arrayListOf()) {
    fun add(morningActivity: MorningActivity): Routine {
        activities.add(morningActivity)
        return this
    }
}
