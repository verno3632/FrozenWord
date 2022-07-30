package jp.developer.retia.frozenword.ui.watch

import androidx.annotation.IntRange
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.developer.retia.frozenword.model.Log
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WatchScreen(
    modifier: Modifier = Modifier,
    title: String,
    simpleHabbitTitle: String,
    trigger: String? = null,
    place: String? = null,
    logs: List<Log> = emptyList(),
    onCompleted: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            LogsScreen(logs = logs)
        }
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

            IndicatorButton(onCompleted)
        }
    }
}

@Composable
fun EditMemoScreen(
    logId: Int,
    memo: String,
    onButtonClicked: (Int, String) -> Unit = { _, _ -> }
) {
    val (text, setText) = remember { mutableStateOf(memo) }
    EditMemoScreen(
        memo = text,
        onMemoEdited = setText,
        onButtonClicked = { onButtonClicked(logId, text) }
    )
}

@Composable
fun EditMemoScreen(
    modifier: Modifier = Modifier,
    memo: String,
    onMemoEdited: (String) -> Unit = {},
    onButtonClicked: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("本日分完了", style = MaterialTheme.typography.h6)
        Text("メモを残そう", style = MaterialTheme.typography.h6)
        TextField(value = memo, onValueChange = onMemoEdited)
        Button(onClick = onButtonClicked) {
            Text("保存")
        }
    }
}

@Composable
fun LogsScreen(modifier: Modifier = Modifier, logs: List<Log>) {
    LazyColumn {
        itemsIndexed(logs) { i, item ->
            val before = logs.getOrNull(i - 1)
            val next = logs.getOrNull(i + 1)
            LogCard(
                iconTitle = "${item.time.month + 1}/${item.time.date}",
                message = item.message,
                date = item.time.toString(),
                showBottomLine = next != null && item.time.date + 1 == next.time.date,
                showTopLine = before != null && item.time.date - 1 == before.time.date
            )
        }
    }
}

@Preview
@Composable
fun PreviewLogCard() {
    FrozenWordTheme {
        Surface {
            LogCard(iconTitle = "1/16", message = "腕立て10回", date = "1/16 20:00")
        }
    }
}

@Composable
fun LogCard(
    modifier: Modifier = Modifier,
    iconTitle: String,
    message: String,
    date: String,
    showBottomLine: Boolean = false,
    showTopLine: Boolean = false
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 8.dp, end = 8.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            val endLineWidth = if (showTopLine) 2 else 0
            Divider(
                color = Color.Red,
                modifier = Modifier
                    .height(16.dp)
                    .width(endLineWidth.dp)
            )

            Text(text = iconTitle, fontSize = 16.sp)

            val startLineWidth = if (showBottomLine) 2 else 0
            Divider(
                color = Color.Red, modifier = Modifier
                    .height(16.dp)
                    .width(startLineWidth.dp)
            )

        }
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .fillMaxHeight()
                .padding(top = 8.dp, bottom = 8.dp, start = 16.dp)
        ) {
            Text(message, fontSize = 16.sp)
            Text(date, fontSize = 8.sp)
        }
    }
}

sealed class IndicatorState {
    object Waiting : IndicatorState()
    data class Doing(@IntRange(from = 1, to = 600) val remainingTime: Int) : IndicatorState()
    object Done : IndicatorState()
}

@Composable
fun PreviewWaitingIndicatorButton() {
    FrozenWordTheme {
        Surface {
            IndicatorButton(indicatorState = IndicatorState.Waiting)
        }
    }
}

@Composable
fun PreviewDoingIndicatorButton() {
    FrozenWordTheme {
        Surface {
            IndicatorButton(indicatorState = IndicatorState.Doing(20))
        }
    }
}

@Composable
fun PreviewDoneIndicatorButton() {
    FrozenWordTheme {
        Surface {
            IndicatorButton(indicatorState = IndicatorState.Done)
        }
    }
}

@Composable
fun IndicatorButton(onCompleted: () -> Unit = {}) {
    var state: IndicatorState by remember { mutableStateOf(IndicatorState.Waiting) }
    val composableScope = rememberCoroutineScope()
    IndicatorButton(indicatorState = state, onClickStartButton = {
        composableScope.launch {
            (0 until 599).forEach { passed ->
                state = IndicatorState.Doing(599 - passed)
                delay(100)
            }

            state = IndicatorState.Done
            onCompleted()
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
        is IndicatorState.Doing ->
            (indicatorState.remainingTime / 10).toString() to indicatorState.remainingTime / 600f
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