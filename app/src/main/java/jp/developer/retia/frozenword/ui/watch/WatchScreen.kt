package jp.developer.retia.frozenword.ui.watch

import androidx.annotation.IntRange
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.developer.retia.frozenword.ui.theme.FrozenWordTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview
@Composable
fun PreviewHabbits() {
    FrozenWordTheme {
        Surface {
            WatchScreen(
                title = "運動",
                simpleHabbitTitle = "着替える",
                trigger = "朝起きたら",
                place = "自室で"
            )
        }
    }
}

@Composable
fun WatchScreen(
    title: String,
    simpleHabbitTitle: String,
    trigger: String? = null,
    place: String? = null,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(title, style = MaterialTheme.typography.h6)
        trigger?.let {
            Text(trigger, style = MaterialTheme.typography.h6)
        }
        place?.let {
            Text(place + "で", style = MaterialTheme.typography.h6)
        }
        Text(simpleHabbitTitle, style = MaterialTheme.typography.h5)

        IndicatorButton()
    }
}

sealed class IndicatorState {
    object Waiting : IndicatorState()
    data class Doing(@IntRange(from = 1, to = 600) val remainingTime: Int) : IndicatorState()
    object Done : IndicatorState()
}

@Preview
@Composable
fun PreviewWaitingIndicatorButton() {
    FrozenWordTheme {
        Surface {
            IndicatorButton(indicatorState = IndicatorState.Waiting)
        }
    }
}

@Preview
@Composable
fun PreviewDoingIndicatorButton() {
    FrozenWordTheme {
        Surface {
            IndicatorButton(indicatorState = IndicatorState.Doing(20))
        }
    }
}

@Preview
@Composable
fun PreviewDoneIndicatorButton() {
    FrozenWordTheme {
        Surface {
            IndicatorButton(indicatorState = IndicatorState.Done)
        }
    }
}

@Composable
fun IndicatorButton() {
    var state: IndicatorState by remember { mutableStateOf(IndicatorState.Waiting) }
    val composableScope = rememberCoroutineScope()
    IndicatorButton(indicatorState = state, onClickStartButton = {
        composableScope.launch {
            (0 until 599).forEach { passed ->
                state = IndicatorState.Doing(600 - passed)
                delay(100)
            }

            state = IndicatorState.Done
        }
    })
}

@Composable
fun IndicatorButton(
    indicatorState: IndicatorState,
    onClickStartButton: () -> Unit = {}
) {
    val (message, progress) = when (indicatorState) {
        is IndicatorState.Waiting -> "開始" to 1f
        is IndicatorState.Doing -> (indicatorState.remainingTime / 10).toString() to indicatorState.remainingTime / 600f
        IndicatorState.Done -> "✔" to 0f
    }
    Box(
        modifier = Modifier
            .padding(top = 10.dp)
            .width(100.dp)
            .height(100.dp)
    ) {
        CircularProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .align(alignment = Alignment.Center)
                .let {
                    if (indicatorState is IndicatorState.Waiting) {
                        it.clickable {
                            onClickStartButton()
                        }
                    } else {
                        it
                    }
                }
        )
        Text(
            message,
            modifier = Modifier
                .align(alignment = Alignment.Center)
        )
    }
}