package jp.developer.retia.frozenword.ui.watch

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import jp.developer.retia.frozenword.model.Habbit
import jp.developer.retia.frozenword.ui.theme.FrozenWordTheme
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(InternalCoroutinesApi::class)
@AndroidEntryPoint
class WatchActivity : ComponentActivity() {

    private val watchViewModel: WatchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FrozenWordTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    WatchScreen(watchViewModel)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                watchViewModel.events.collect { event ->
                    when (event) {
                        WatchEvent.Back -> onBackPressed()
                    }
                }
            }
        }
    }

    companion object {
        const val BUNDLE_KEY_ID = "id"
        fun createIntent(context: Context, habbit: Habbit): Intent =
            Intent(context, WatchActivity::class.java).apply {
                putExtras(
                    bundleOf(BUNDLE_KEY_ID to habbit.id)
                )
            }
    }
}

@Composable
fun WatchScreen(
    watchViewModel: WatchViewModel
) {
    val state by watchViewModel.uiState.collectAsState()

    when (val s = state) {
        is WatchUiState.Loaded ->
            WatchScreen(
                title = s.habbit.title,
                simpleHabbitTitle = s.habbit.simpleHabbitTitle,
                trigger = s.habbit.trigger,
                place = s.habbit.place,
                onCompleted = watchViewModel::onCompleted
            )
        is WatchUiState.EditMemo ->
            EditMemoScreen(
                logId = s.logId,
                memo = s.message,
                onButtonClicked = watchViewModel::onMemoSaved
            )
    }
}