package jp.developer.retia.frozenword.ui.habbits

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import jp.developer.retia.frozenword.model.Habbit
import jp.developer.retia.frozenword.model.HabbitAndLog
import jp.developer.retia.frozenword.repository.HabbitRepository
import jp.developer.retia.frozenword.setMainDispatcher
import jp.developer.retia.frozenword.toList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

@OptIn(ExperimentalCoroutinesApi::class)
object HabbitsViewModelSpec : Spek({
    val testCoroutineDispatcher = TestCoroutineDispatcher()
    val mockHabbitRepository by memoized { mockk<HabbitRepository>(relaxUnitFun = true) }
    val habbitsViewModel by memoized {
        HabbitsViewModel(
            mockHabbitRepository,
            testCoroutineDispatcher
        )
    }
    beforeEachTest {
        coEvery { mockHabbitRepository.getHabbitAndLogs() } returns emptyList()
    }
    setMainDispatcher(testCoroutineDispatcher)

    describe("init") {
        val habbits = listOf(mockk<HabbitAndLog>())
        beforeEachTest {
            coEvery { mockHabbitRepository.getHabbitAndLogs() } returns habbits
        }

        it("習慣一覧が表示される") {
            assertThat(habbitsViewModel.uiState.value).isEqualTo(
                HabbitsUiState.Loaded(habbits)
            )
        }
    }

    describe("onResume") {
        val habbits = listOf(mockk<HabbitAndLog>())
        beforeEachTest {
            coEvery { mockHabbitRepository.getHabbitAndLogs() } returnsMany listOf(
                emptyList(),
                habbits
            )

            habbitsViewModel.onResume()
        }

        it("習慣一覧が表示される") {
            assertThat(habbitsViewModel.uiState.value).isEqualTo(
                HabbitsUiState.Loaded(habbits)
            )
        }
    }

    describe("onClickActionButton") {
        lateinit var actual: List<HabbitsEvent>
        beforeEachTest {
            runBlockingTest {
                actual = habbitsViewModel.events.toList {
                    habbitsViewModel.onClickActionButton()
                }
            }
        }

        it("週間追加画面に遷移する") {
            assertThat(actual).isEqualTo(listOf(HabbitsEvent.NavigateToAdd))
        }
    }

    describe("onClickHabbitCard") {
        val habbit = mockk<Habbit>()
        lateinit var actual: List<HabbitsEvent>
        beforeEachTest {
            runBlockingTest {
                actual = habbitsViewModel.events.toList {
                    habbitsViewModel.onClickHabbitCard(habbit)
                }
            }
        }

        it("習慣開始画面に遷移する") {
            assertThat(actual).isEqualTo(listOf(HabbitsEvent.NavigateToStart(habbit)))
        }
    }
})
