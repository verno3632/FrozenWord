package jp.developer.retia.frozenword.ui.habbits

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import jp.developer.retia.frozenword.model.HabbitAndLog
import jp.developer.retia.frozenword.repository.HabbitRepository
import jp.developer.retia.frozenword.setMainDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

@OptIn(ExperimentalCoroutinesApi::class)
object HabbitsViewModelSpec : Spek({
    val mockHabbitRepository by memoized { mockk<HabbitRepository>(relaxUnitFun = true) }
    val habbitsViewModel by memoized {
        HabbitsViewModel(
            mockHabbitRepository,
            TestCoroutineDispatcher()
        )
    }
    setMainDispatcher()

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
})
