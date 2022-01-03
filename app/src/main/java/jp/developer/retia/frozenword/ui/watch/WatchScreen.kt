package jp.developer.retia.frozenword.ui.watch

import androidx.annotation.IntRange
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.developer.retia.frozenword.model.Habbit
import jp.developer.retia.frozenword.model.HabbitAndLog
import jp.developer.retia.frozenword.ui.theme.FrozenWordTheme
import kotlin.math.min

@Preview
@Composable
fun PreviewHabbits() {
    FrozenWordTheme {
        Surface {
            WatchWaitingScreen(
                title = "運動",
                simpleHabbitTitle = "着替える",
                trigger = "朝起きたら",
                place = "自室で"
            )
        }
    }
}

@Composable
fun WatchWaitingScreen(
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

        IndicatorButton(IndicatorState.Waiting)
    }
}

sealed class IndicatorState {
    object Waiting : IndicatorState()
    data class Doing(@IntRange(from = 1, to = 60) val remainingTime: Int) : IndicatorState()
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
fun IndicatorButton(
    indicatorState: IndicatorState
) {
    Box(
        modifier = Modifier
            .padding(top = 10.dp)
            .width(100.dp)
            .height(100.dp)
    ) {
        CircularProgressIndicator(
            progress = 1f,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .align(alignment = Alignment.Center)
        )
        when (indicatorState) {
            is IndicatorState.Waiting -> {
                Text(
                    "開始",
                    modifier = Modifier
                        .align(alignment = Alignment.Center)
                )
            }
        }
    }
}