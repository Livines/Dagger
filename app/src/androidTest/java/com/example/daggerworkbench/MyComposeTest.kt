package com.example.daggerworkbench

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.hasAnySibling
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.printToLog
import com.example.daggerworkbench.ui.RememberSaveableExample
import org.junit.Rule
import org.junit.Test

// file: app/src/androidTest/java/com/package/MyComposeTest.kt

class MyComposeTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    // use createAndroidComposeRule<YourActivity>() if you need access to
    // an activity

    @Test
    fun myTest() {
        // Start the app
        composeTestRule.setContent {
            MaterialTheme {
                RememberSaveableExample()
            }
        }

        composeTestRule.onNodeWithText("Initial Text")
        composeTestRule.onNode(hasAnySibling(hasTestTag("TEST")))
        composeTestRule.onAllNodes(isRoot(), useUnmergedTree = true)
            .printToLog("Print Root", maxDepth = 10)

        composeTestRule.onNode(hasText("Initial Text"))
            .assertExists()
    }
}