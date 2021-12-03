package jp.developer.retia.frozenword.ui.addHabbit

import com.google.common.truth.Truth.assertThat
import io.mockk.coVerify
import io.mockk.mockk
import jp.developer.retia.frozenword.repository.HabbitRepository
import jp.developer.retia.frozenword.setMainDispatcher
import jp.developer.retia.frozenword.toList
import jp.developer.retia.frozenword.ui.viewmodel.AddHabbitEvent
import jp.developer.retia.frozenword.ui.viewmodel.AddHabbitUiState
import jp.developer.retia.frozenword.ui.viewmodel.AddHabbitViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

@OptIn(ExperimentalCoroutinesApi::class)
object AddHabbitViewModelSpec : Spek({
    val mockHabbitRepository by memoized { mockk<HabbitRepository>(relaxUnitFun = true) }
    val addHabbitViewModel by memoized { AddHabbitViewModel(mockHabbitRepository) }
    setMainDispatcher()

    describe("onHabbitTitleUpdated") {
        val title = "title1"
        beforeEachTest {
            addHabbitViewModel.onHabbitTitleUpdated("title1")
        }

        it("dummyHabbitsが渡される") {
            assertThat(addHabbitViewModel.uiState.value).isEqualTo(
                AddHabbitUiState.HabbitTitlePage(
                    title,
                    emptyList(),
                    true
                )
            )
        }
    }

    describe("onHabbitTitleUpdated") {
        val title = "title1"
        lateinit var actual: List<AddHabbitEvent>
        beforeEachTest {
            addHabbitViewModel.onHabbitTitleUpdated("title1")
            runBlockingTest {
                actual = addHabbitViewModel.events.toList {
                    addHabbitViewModel.onHabbitTitleNextButtonClicked()
                }
            }
        }

        it("Habbitsが保存される") {
            coVerify {
                mockHabbitRepository.insert(title = title, trigger = "")
            }
        }

        it("closeが降ってくる") {
            assertThat(actual).isEqualTo(listOf(AddHabbitEvent.Back))
        }
    }
})
