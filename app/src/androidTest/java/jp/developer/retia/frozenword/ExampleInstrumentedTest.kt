package jp.developer.retia.frozenword

import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performTextInput
import jp.developer.retia.frozenword.ui.activity.AddHabbitActivity
import org.junit.Rule
import org.junit.Test

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleInstrumentedTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<AddHabbitActivity>()

    @Test
    fun MyTest() {
        composeTestRule.onNode(hasSetTextAction()).performTextInput("hogehoge")
        composeTestRule.onNode(hasText("hogehoge")).assertExists()
    }
}
