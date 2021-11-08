package jp.developer.retia.frozenword.repository

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import jp.developer.retia.frozenword.db.HabbitDao
import jp.developer.retia.frozenword.model.Habbit
import jp.developer.retia.frozenword.model.HabbitAndLog
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

@OptIn(ExperimentalCoroutinesApi::class)
object HabbitRepositorySpec : Spek({
    val mockHabbitDao by memoized { mockk<HabbitDao>(relaxUnitFun = true) }
    val habbitRepository by memoized { HabbitRepository(mockHabbitDao) }

    describe("insertAll") {
        val dummyHabbit = mockk<Habbit>()
        beforeEachTest {
            runBlockingTest {
                habbitRepository.insertAll(listOf(dummyHabbit))
            }
        }

        it("dummyHabbitsが渡される") {
            coVerify {
                mockHabbitDao.insertAll(dummyHabbit)
            }
        }
    }

    describe("getHabbitAndLogs") {
        val dummyHabbits = listOf(mockk<HabbitAndLog>())
        lateinit var actual: List<HabbitAndLog>
        beforeEachTest {
            coEvery { mockHabbitDao.getHabbitAndLogs() } returns dummyHabbits
            runBlockingTest {
                actual = habbitRepository.getHabbitAndLogs()
            }
        }

        it("dummyHabbitsが渡される") {
            assertThat(actual).isEqualTo(dummyHabbits)
        }
    }
})