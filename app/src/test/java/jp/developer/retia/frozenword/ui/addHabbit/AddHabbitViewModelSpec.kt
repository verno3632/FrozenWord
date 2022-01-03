package jp.developer.retia.frozenword.ui.addHabbit

import com.google.common.truth.Truth.assertThat
import io.mockk.coVerify
import io.mockk.mockk
import jp.developer.retia.frozenword.repository.HabbitRepository
import jp.developer.retia.frozenword.setMainDispatcher
import jp.developer.retia.frozenword.toList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

@OptIn(ExperimentalCoroutinesApi::class)
object AddHabbitViewModelSpec : Spek({
    val mockHabbitRepository by memoized { mockk<HabbitRepository>(relaxUnitFun = true) }
    val addHabbitViewModel by memoized { AddHabbitViewModel(mockHabbitRepository) }
    setMainDispatcher()

    describe("onHabbitTitleNextButtonClicked") {
        val title = "title1"
        lateinit var actual: List<AddHabbitUiState>
        beforeEachTest {
            runBlockingTest {
                actual = addHabbitViewModel.uiState.toList {
                    addHabbitViewModel.onHabbitTitleNextButtonClicked(title)
                }
            }
        }

        it("simpleHabbitTitle画面へ遷移") {
            assertThat(actual[1]).isEqualTo(
                AddHabbitUiState.SimpleHabbitTitlePage(
                    title,
                    emptyList()
                )
            )
        }
    }

    describe("onSipmpleHabbitTitleCompleteClicked") {
        val title = "title1"
        val simpleTitle = "simpleTitle"
        lateinit var actual: List<AddHabbitEvent>
        beforeEachTest {
            runBlockingTest {
                addHabbitViewModel.onHabbitTitleNextButtonClicked(title)
                actual = addHabbitViewModel.events.toList {
                    addHabbitViewModel.onSimpleHabbitTitleCompleteClicked(simpleTitle)
                }
            }
        }

        it("戻る") {
            assertThat(actual[0]).isEqualTo(
                AddHabbitEvent.Back
            )
        }

        it("習慣が保存される") {
            coVerify {
                mockHabbitRepository.insert(title, simpleTitle, "", "")
            }
        }
    }

    describe("onSimpleHabbitTitleNextButtonClicked") {
        val title = "title1"
        val simpleHabbitTitle = "simpleHabbitTitle"
        lateinit var actual: List<AddHabbitUiState>
        beforeEachTest {
            addHabbitViewModel.onHabbitTitleNextButtonClicked(title)
            runBlockingTest {
                actual = addHabbitViewModel.uiState.toList {
                    addHabbitViewModel.onSimpleHabbitTitleNextButtonClicked(simpleHabbitTitle)
                }
            }
        }

        it("トリガー画面へ遷移") {
            assertThat(actual[1]).isEqualTo(
                AddHabbitUiState.HabbitTriggerPage(
                    title,
                    simpleHabbitTitle
                )
            )
        }
    }

    describe("onHabbitTriggerCompleteClicked") {
        val title = "title1"
        val simpleTitle = "simpleTitle"
        val trigger = "trigger"
        lateinit var actual: List<AddHabbitEvent>
        beforeEachTest {
            runBlockingTest {
                addHabbitViewModel.onHabbitTitleNextButtonClicked(title)
                addHabbitViewModel.onSimpleHabbitTitleNextButtonClicked(simpleTitle)
                actual = addHabbitViewModel.events.toList {
                    addHabbitViewModel.onHabbitTriggerCompleteClicked(trigger)
                }
            }
        }

        it("戻る") {
            assertThat(actual[0]).isEqualTo(
                AddHabbitEvent.Back
            )
        }

        it("習慣が保存される") {
            coVerify {
                mockHabbitRepository.insert(title, simpleTitle, trigger, "")
            }
        }
    }

    describe("onHabbitTriggerNextButtonClicked") {
        val title = "title1"
        val simpleHabbitTitle = "simpleHabbitTitle"
        val trigger = "trigger"
        lateinit var actual: List<AddHabbitUiState>
        beforeEachTest {
            addHabbitViewModel.onHabbitTitleNextButtonClicked(title)
            addHabbitViewModel.onSimpleHabbitTitleNextButtonClicked(simpleHabbitTitle)
            runBlockingTest {
                actual = addHabbitViewModel.uiState.toList {
                    addHabbitViewModel.onHabbitTriggerNextButtonClicked(trigger)
                }
            }
        }

        it("場所入力画面へ遷移") {
            assertThat(actual[1]).isEqualTo(
                AddHabbitUiState.HabbitPlacePage(
                    title,
                    simpleHabbitTitle,
                    trigger
                )
            )
        }
    }
})
