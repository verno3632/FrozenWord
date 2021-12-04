package jp.developer.retia.frozenword.ui.habbits

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.developer.retia.frozenword.model.Habbit
import jp.developer.retia.frozenword.model.HabbitAndLog
import jp.developer.retia.frozenword.ui.theme.FrozenWordTheme
import jp.developer.retia.frozenword.ui.viewmodel.HabbitsUiState
import kotlin.math.min

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

@Composable
fun HabbitsScreen(
    uiState: HabbitsUiState
) {
    when (uiState) {
        is HabbitsUiState.Loaded -> {
            Habbits(habbits = uiState.habbits)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Habbits(
    habbits: List<HabbitAndLog>
) {
    LazyColumn(
        modifier = Modifier
            .background(Color.Gray)
            .wrapContentHeight()
    ) {
        items(habbits) { habbit ->
            Card(modifier = Modifier.padding(2.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(8.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(habbit.habbit.trigger, fontSize = 13.sp)
                        Text(habbit.habbit.title, fontSize = 16.sp)
                    }
                    TaskDots(modifier = Modifier.weight(1f), 24)
                }
            }
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
