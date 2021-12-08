package jp.developer.retia.frozenword

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import javax.inject.Inject
import jp.developer.retia.frozenword.db.AppDatabase
import jp.developer.retia.frozenword.db.HabbitDao
import jp.developer.retia.frozenword.ui.addHabbit.AddHabbitActivity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@HiltAndroidTest
class AddHabbitActivityUnitTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<AddHabbitActivity>()

    @Inject
    lateinit var habbitDao: HabbitDao

    @Inject
    lateinit var appDatabase: AppDatabase

    @Before
    fun setup() {
        hiltRule.inject()
        appDatabase.clearAllTables()
    }

    @After
    fun cleanup() {
        appDatabase.clearAllTables()
    }

    // Action
    @Test
    fun `入力`() {
        composeTestRule.onNode(hasSetTextAction()).apply {
            performTextClearance()
            performTextInput("hogehoge")
        }
        composeTestRule.onNode(hasText("hogehoge")).assertExists()

        composeTestRule.onNode(hasText("次へ")).apply {
            performClick()
        }

        runBlocking {
            val habbit = habbitDao.getAll()[0]
            assertThat(habbit.title).isEqualTo("hogehoge")
            composeTestRule.waitForIdle()
        }
    }
}
