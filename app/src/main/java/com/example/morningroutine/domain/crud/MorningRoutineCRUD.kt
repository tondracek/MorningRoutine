package com.example.morningroutine.domain.crud

import com.example.morningroutine.domain.model.ActivityId
import com.example.morningroutine.domain.model.MorningActivity
import com.example.morningroutine.domain.model.MorningRoutine
import kotlinx.coroutines.flow.Flow

interface MorningRoutineCRUD {

    fun getMorningRoutine(): Flow<MorningRoutine>

    suspend fun insertActivity(activity: MorningActivity, index: Int)

    suspend fun updateActivity(activity: MorningActivity)

    suspend fun deleteActivity(activity: MorningActivity)

    suspend fun toggleActivityDone(activityId: ActivityId)
}
