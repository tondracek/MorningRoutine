package com.example.morningroutine.classes

class Routine(val activities: ArrayList<MorningActivity> = arrayListOf()) {
    fun add(morningActivity: MorningActivity): Routine {
        activities.add(morningActivity)
        return this
    }
}
