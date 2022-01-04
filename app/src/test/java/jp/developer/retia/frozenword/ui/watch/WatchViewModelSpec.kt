package jp.developer.retia.frozenword.ui.watch

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
object WatchViewModelSpec : Spek({
    val habbit = mockk<Habbit>()
    val testCoroutineDispatcher = TestCoroutineDispatcher()
    val mockHabbitRepository by memoized { mockk<HabbitRepository>(relaxUnitFun = true) }
    val watchViewModel by memoized {
        WatchViewModel(
            12345,
            mockHabbitRepository,
            testCoroutineDispatcher
        )
    }
    beforeEachTest {
        coEvery { mockHabbitRepository.getHabbit(12345) } returns habbit
    }
    setMainDispatcher(testCoroutineDispatcher)

    describe("init") {
        it("選択した習慣が表示される") {
            assertThat(watchViewModel.uiState.value).isEqualTo(WatchUiState.Loaded(habbit))
        }
    }
})
