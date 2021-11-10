package jp.developer.retia.frozenword.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import jp.developer.retia.frozenword.model.Habbit
import jp.developer.retia.frozenword.model.HabbitAndLog
import jp.developer.retia.frozenword.ui.theme.FrozenWordTheme
import jp.developer.retia.frozenword.ui.viewmodel.*
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@OptIn(InternalCoroutinesApi::class)
@AndroidEntryPoint
class HabbitsActivity : ComponentActivity() {

    private val habbitsViewModel: HabbitsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PreviewHabbits()
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                habbitsViewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is HabbitsUiState.Loaded -> {
                            setContent {
                                Habbits(habbits = uiState.habbits)
                            }
                        }
                    }
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                habbitsViewModel.events.collect { event ->
                    when (event) {
                        is HabbitsEvent.Back -> onBackPressed()
                    }
                }
            }
        }
    }

    private fun show1stPane(defaultTitle: String, sampleTitles: List<String>) {
        setContent {
            FrozenWordTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                }
            }
        }
    }

    companion object {
        fun createIntent(context: Context): Intent = Intent(context, HabbitsActivity::class.java)
    }
}

@Preview
@Composable
fun PreviewHabbits() {
    FrozenWordTheme {
        Surface {
            Habbits(
                listOf(
                    HabbitAndLog(Habbit(title = "hogehoge", trigger = "まるまるしたら"), emptyList()),
                    HabbitAndLog(Habbit(title = "hogehoge", trigger = "まるまるしたら"), emptyList()),
                    HabbitAndLog(Habbit(title = "hogehoge", trigger = "まるまるしたら"), emptyList()),
                )
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Habbits(
    habbits: List<HabbitAndLog>
) {
    LazyColumn {
        items(habbits) { habbit ->
            Card(modifier = Modifier.padding(2.dp)) {
                Row(modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(8.dp)) {
                    Column {
                        Text(habbit.habbit.trigger)
                        Text(habbit.habbit.title)
                    }
                }
            }
        }
    }
}