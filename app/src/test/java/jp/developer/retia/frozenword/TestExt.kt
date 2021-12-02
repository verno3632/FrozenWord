package jp.developer.retia.frozenword

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.spekframework.spek2.dsl.LifecycleAware

@ExperimentalCoroutinesApi
fun LifecycleAware.setMainDispatcher(
    dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) {
    beforeEachGroup {
        Dispatchers.setMain(dispatcher)
    }

    afterEachGroup {
        dispatcher.cleanupTestCoroutines()
        Dispatchers.resetMain()
    }
}

// 例: FluxのStoreのテストで、Actionを流したときにどのEventが流れてくるかテストする 等
// Flowが閉じておらず流れてくるEventを取得できないので
// blockを実行したらjobをキャンセルしてそこまでに流れてきたイベントを取得する拡張関数
// https://at-sushi.work/blog/20
suspend fun <T> Flow<T>.toList(
    block: suspend CoroutineScope.() -> Unit
): List<T> {
    val list = mutableListOf<T>()
    coroutineScope {
        val job = launch {
            this@toList.collect {
                list.add(it)
            }
        }
        block.invoke(this)
        job.cancel()
    }
    return list
}
