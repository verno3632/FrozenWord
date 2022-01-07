package jp.developer.retia.frozenword.ui.watch

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import jp.developer.retia.frozenword.model.Habbit
import jp.developer.retia.frozenword.repository.HabbitRepository
import jp.developer.retia.frozenword.setMainDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

@OptIn(ExperimentalCoroutinesApi::class)
object WatchViewModelSpec : Spek({
    val habbit = mockk<Habbit>()
    val testCoroutineDispatcher = TestCoroutineDispatcher()
    val mockHabbitRepository by memoized { mockk<HabbitRepository>(relaxUnitFun = true) }
    val mockSaveStateHandle by memoized {
        mockk<SavedStateHandle> {
            every { get<Int>(WatchActivity.BUNDLE_KEY_ID) } returns 12345

        }
    }
    val watchViewModel by memoized {
        WatchViewModel(
            mockSaveStateHandle,
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

    describe("onCompleted") {
        beforeEachTest {
            watchViewModel.onCompleted()
        }

        it("ログに追加される") {
            coVerify { mockHabbitRepository.insertLog(12345, any(), "") }
        }
    }
})
