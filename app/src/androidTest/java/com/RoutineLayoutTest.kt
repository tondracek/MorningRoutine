package com

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.toColor
import androidx.navigation.NavController
import androidx.test.espresso.action.ViewActions
import com.example.morningroutine.classes.MorningActivity
import com.example.morningroutine.ui.layout.RoutineLayout
import com.example.morningroutine.ui.theme.AppTheme
import org.junit.Rule
import org.junit.Test

class RoutineLayoutTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun testRoutineCompletion() {
        fun testSingleActivityCard(cardText: String) {
            rule.onNodeWithText(cardText).assertIsDisplayed()

            val activityCardColorPixels = IntArray(1)
            rule.onNodeWithText(cardText)
                .captureToImage()
                .readPixels(activityCardColorPixels, 50, 50, 1, 1)

            val appBarColorPixels = IntArray(1)
            rule.onNodeWithText("Morning Routine")
                .captureToImage()
                .readPixels(appBarColorPixels, 0, 0, 1, 1)

            assert(activityCardColorPixels[0].toColor() == appBarColorPixels[0].toColor())

            rule.onNodeWithText(cardText).performClick()
        }

        rule.setContent {
            AppTheme {
                RoutineLayout(navController = NavController(LocalContext.current))
            }
        }

        rule.onNodeWithTag("progressText").assert(hasText("0/6"))
        testSingleActivityCard("Wash your face")
        rule.onNodeWithTag("progressText").assert(hasText("1/6"))
        testSingleActivityCard("Beard oil")
        rule.onNodeWithTag("progressText").assert(hasText("2/6"))
        testSingleActivityCard("Vitamins + creatine")
        rule.onNodeWithTag("progressText").assert(hasText("3/6"))
        testSingleActivityCard("Coffee")
        rule.onNodeWithTag("progressText").assert(hasText("4/6"))
        testSingleActivityCard("Bathroom")
        rule.onNodeWithTag("progressText").assert(hasText("5/6"))
        testSingleActivityCard("Breakfast")
        rule.onNodeWithTag("progressText").assert(hasText("6/6"))

        rule.onAllNodesWithTag("activityCard").apply {
            fetchSemanticsNodes().forEachIndexed { i, _ ->
                val activityCardColorPixels = IntArray(1)
                get(i)
                    .performScrollTo()
                    .captureToImage()
                    .readPixels(activityCardColorPixels, 50, 50, 1, 1)

                println(activityCardColorPixels[0])
                println(MorningActivity.disabledColor.toArgb())
                assert(activityCardColorPixels[0] == MorningActivity.disabledColor.toArgb())
            }
        }
    }
}

@Preview
@Composable
fun TestsPreview() {
    AppTheme {
        RoutineLayout(NavController(LocalContext.current))
    }
}