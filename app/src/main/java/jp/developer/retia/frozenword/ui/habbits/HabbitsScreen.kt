package jp.developer.retia.frozenword.ui.habbits

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
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
            Habbits(
                listOf(
                    HabbitAndLog(
                        Habbit(
                            title = "hogehoge",
                            trigger = "まるまるしたら",
                            simpleHabbitTitle = "何何する"
                        ), emptyList()
                    ),
                    HabbitAndLog(
                        Habbit(
                            title = "hogehoge",
                            trigger = "まるまるしたら",
                            simpleHabbitTitle = "なになにする"
                        ), emptyList()
                    ),
                    HabbitAndLog(
                        Habbit(
                            title = "hogehoge",
                            trigger = "まるまるしたら",
                            simpleHabbitTitle = "naninaniする"
                        ), emptyList()
                    ),
                )
            )
        }
    }
}

@Composable
fun HabbitsScreen(
    uiState: HabbitsUiState,
    onClickActionButton: (() -> Unit) = {}
) {
    when (uiState) {
        is HabbitsUiState.Loaded -> {
            Habbits(habbits = uiState.habbits, onClickActionButton)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Habbits(
    habbits: List<HabbitAndLog>,
    onClickActionButton: (() -> Unit) = {}
) {
    LazyColumn(
        modifier = Modifier
            .background(Color.Gray)
            .wrapContentHeight()
    ) {
        items(habbits) { habbit ->
            Habbit(habbit)
        }
    }
}

@Composable
fun Habbit(
    habbit: HabbitAndLog
) {
    Habbit(habbit.habbit.trigger, habbit.habbit.title, habbit.habbit.trigger)
}

@Composable
fun Habbit(
    title: String,
    simpleHabbitTitle: String,
    trigger: String? = null,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.padding(2.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontSize = 12.sp)
                trigger?.let {
                    Text(trigger, fontSize = 12.sp)
                }
                Text(simpleHabbitTitle, fontSize = 16.sp)
            }
            TaskDots(modifier = Modifier.weight(1f), 24)
        }
    }
}

@Composable
fun TaskDots(modifier: Modifier = Modifier, days: Int) {
    val columnSize = min(days / 7, 4)
    Column {
        repeat(columnSize) {
            Row {
                repeat(7) {
                    Row(
                        modifier = Modifier
                            .width(12.dp)
                            .height(12.dp)
                            .background(Color.Blue),
                    ) {}
                }
            }
        }
        if (columnSize < 4) {
            Row {
                repeat(days % 7) {
                    Row(
                        modifier = Modifier
                            .width(12.dp)
                            .height(12.dp)
                            .background(Color.Blue)
                    ) {}
                }
            }
        }
    }
}
