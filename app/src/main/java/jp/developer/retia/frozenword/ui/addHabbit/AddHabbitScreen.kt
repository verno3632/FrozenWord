package jp.developer.retia.frozenword.ui.addHabbit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.developer.retia.frozenword.R
import jp.developer.retia.frozenword.ui.theme.FrozenWordTheme
import jp.developer.retia.frozenword.ui.viewmodel.AddHabbitUiState

@Composable
fun AddHabbitScreen(
    state: AddHabbitUiState,
    onTitleChanged: (String) -> Unit = {},
    onSuggestionClicked: (String) -> Unit = {},
    onTitleNextButtonClicked: () -> Unit = {},
) {
    when (state) {
        is AddHabbitUiState.HabbitTitlePage ->
            FirstPane(
                defaultTitle = state.title,
                sampleTitles = state.sampleTitle,
                onTitleChanged = onTitleChanged,
                onSuggestionClicked = onSuggestionClicked,
                onTitleNextButtonClicked = onTitleNextButtonClicked,
            )
        is AddHabbitUiState.ShortHabbitTitlePage -> TODO()
        is AddHabbitUiState.TitlePage -> TODO()
    }
}
@Preview
@Composable
fun PreviewFirstPane() {
    FrozenWordTheme {
        Surface {
            FirstPane(defaultTitle = "hogehoge", sampleTitles = listOf("運動する", "勉強する", "絵を描く"))
        }
    }
}

@Composable
fun FirstPane(
    defaultTitle: String,
    sampleTitles: List<String>,
    onTitleChanged: (String) -> Unit = {},
    onSuggestionClicked: (String) -> Unit = {},
    onTitleNextButtonClicked: () -> Unit = {},
) {
    var title: String by remember { mutableStateOf(defaultTitle) }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("継続したいことは？")
        OutlinedTextField(
            value = title,
            onValueChange = {
                title = it
                onTitleChanged(title)
            }
        )
        sampleTitles.take(3).forEach { Chip(it, onSuggestionClicked) }
        Button(onClick = onTitleNextButtonClicked) {
            Text("次へ")
        }
    }
}

@Composable
fun Chip(
    title: String,
    onSelectedCategoryChanged: (String) -> Unit,
) {
    Surface(
        modifier = Modifier
            .padding(end = 8.dp, bottom = 8.dp)
            .clickable { onSelectedCategoryChanged(title) },
        elevation = 8.dp,
        shape = RoundedCornerShape(16.dp),
        color = colorResource(R.color.purple_500)
    ) {
        Row {
            Text(
                text = title,
                style = MaterialTheme.typography.body2,
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

