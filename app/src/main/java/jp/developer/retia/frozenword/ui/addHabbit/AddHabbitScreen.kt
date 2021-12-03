package jp.developer.retia.frozenword.ui.addHabbit

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
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


