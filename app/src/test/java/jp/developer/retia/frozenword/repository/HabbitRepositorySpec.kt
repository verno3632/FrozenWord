package jp.developer.retia.frozenword.repository

import io.mockk.coVerify
import io.mockk.mockk
import jp.developer.retia.frozenword.db.HabbitDao
import jp.developer.retia.frozenword.model.Habbit
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