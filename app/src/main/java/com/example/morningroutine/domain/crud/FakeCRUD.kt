package com.example.morningroutine.domain.crud

import androidx.compose.ui.graphics.Color
import com.example.morningroutine.R
import com.example.morningroutine.domain.model.ActivityId
import com.example.morningroutine.domain.model.MorningActivity
import com.example.morningroutine.domain.model.MorningRoutine
import com.example.morningroutine.ui.components.IconResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class FakeCRUD : MorningRoutineCRUD {

    private var activities: MutableStateFlow<List<MorningActivity>> =
        MutableStateFlow(sampleActivities)

    override fun getMorningRoutine(): Flow<MorningRoutine> {
        return activities.map { MorningRoutine(it) }
    }

    override suspend fun insertActivity(activity: MorningActivity, index: Int) {
        activities.update { currentActivities ->
            currentActivities
                .toMutableList()
                .apply {
                    add(index, activity)
                }
        }
    }

    override suspend fun updateActivity(activity: MorningActivity) {
        activities.update { currentActivities ->
            currentActivities.map {
                when (it.id) {
                    activity.id -> activity
                    else -> it
                }
            }
        }
    }

    override suspend fun deleteActivity(activity: MorningActivity) {
        activities.update { currentActivities ->
            currentActivities.filterNot { it.id == activity.id }
        }
    }

    override suspend fun toggleActivityDone(activityId: ActivityId) {
        activities.update { currentActivities ->
            currentActivities.map {
                when (it.id) {
                    activityId -> it.copy(isDone = !it.isDone)
                    else -> it
                }
            }
        }
    }
}

val sampleActivities = listOf(
    MorningActivity(
        id = ActivityId(1),
        name = "Wake up",
        iconSource = IconResource(R.drawable.icon_sun),
        containerColor = Color.Green,
    ),
    MorningActivity(
        id = ActivityId(2),
        name = "Drink water",
        iconSource = IconResource(R.drawable.icon_drink_water),
        containerColor = Color.Blue,
    ),
    MorningActivity(
        id = ActivityId(3),
        name = "Make bed",
        iconSource = IconResource(R.drawable.icon_cleaning),
        containerColor = Color.Red,
    ),
    MorningActivity(
        id = ActivityId(4),
        name = "Brush teeth",
        iconSource = IconResource(R.drawable.icon_toothbrush),
        containerColor = Color.Blue,
    ),
    MorningActivity(
        id = ActivityId(5),
        name = "Eat breakfast",
        iconSource = IconResource(R.drawable.icon_breakfast),
        containerColor = Color.Yellow,
    ),
    MorningActivity(
        id = ActivityId(6),
        name = "Supplements",
        iconSource = IconResource(R.drawable.icon_supplements),
        containerColor = Color.Green,
    ),
    MorningActivity(
        id = ActivityId(7),
        name = "Meditate",
        iconSource = IconResource(R.drawable.icon_skincare),
        containerColor = Color.Red,
    ),
    MorningActivity(
        id = ActivityId(8),
        name = "Exercise",
        iconSource = IconResource(R.drawable.icon_workout),
        containerColor = Color.Red,
    ),
    MorningActivity(
        id = ActivityId(9),
        name = "Shower",
        iconSource = IconResource(R.drawable.icon_shower),
        containerColor = Color.Blue,
    ),
    MorningActivity(
        id = ActivityId(10),
        name = "Get dressed",
        iconSource = IconResource(R.drawable.icon_closet),
        containerColor = Color.Green,
    ),
)
