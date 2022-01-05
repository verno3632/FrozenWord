package jp.developer.retia.frozenword.repository

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.util.Date
import jp.developer.retia.frozenword.db.HabbitDao
import jp.developer.retia.frozenword.db.LogDao
import jp.developer.retia.frozenword.model.Habbit
import jp.developer.retia.frozenword.model.HabbitAndLog
import jp.developer.retia.frozenword.model.Log
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

@OptIn(ExperimentalCoroutinesApi::class)
object HabbitRepositorySpec : Spek({
    val mockHabbitDao by memoized { mockk<HabbitDao>(relaxUnitFun = true) }
    val mockLogDao by memoized { mockk<LogDao>(relaxed = true) }
    val habbitRepository by memoized { HabbitRepository(mockHabbitDao, mockLogDao) }

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

    describe("insertLog") {
        val dummyDate = mockk<Date>()
        beforeEachTest {
            runTest {
                habbitRepository.insertLog(12345, dummyDate, "message")
            }
        }

        it("logが挿入される") {
            coVerify {
                mockLogDao.insert(Log(habbitId = 12345, time = dummyDate, message = "message"))
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

    describe("getHabbit") {
        val dummyHabbit = mockk<Habbit>()
        lateinit var actual: Habbit
        beforeEachTest {
            coEvery { mockHabbitDao.getHabbit(12345) } returns dummyHabbit
            runTest {
                actual = habbitRepository.getHabbit(12345)
            }
        }

        it("dummyHabbitが渡される") {
            assertThat(actual).isEqualTo(dummyHabbit)
        }
    }
})
