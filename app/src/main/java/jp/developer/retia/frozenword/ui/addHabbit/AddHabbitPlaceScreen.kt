package jp.developer.retia.frozenword.ui.addHabbit

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.developer.retia.frozenword.ui.habbits.HabbitCard

@Preview(showBackground = true)
@Composable
fun PreviewAddHabbitPlaceScreen() {
    AddHabbitPlaceScreen(
        habbit = "運動",
        simpleHabbitTitle = "運動着に着替える",
        trigger = "朝起きたら",
    )
}

@Composable
fun AddHabbitPlaceScreen(
    habbit: String,
    simpleHabbitTitle: String,
    trigger: String,
    onHabbitPlaceCompleteClicked: (String) -> Unit = {},
    onHabbitPlaceNextClicked: (String) -> Unit = {},
) {
    val (place, setPlace) = remember { mutableStateOf("") }

    AddHabbitPlaceScreen(
        habbit = habbit,
        simpleHabbitTitle = simpleHabbitTitle,
        trigger = trigger,
        place = place,
        onPlaceChanged = setPlace,
        onPlaceCompleteClicked = { onHabbitPlaceCompleteClicked(place) },
        onPlaceNextClicked = { onHabbitPlaceNextClicked(place) }
    )
}

@Composable
fun AddHabbitPlaceScreen(
    habbit: String,
    simpleHabbitTitle: String,
    trigger: String,
    place: String,
    onPlaceChanged: (String) -> Unit = {},
    onPlaceCompleteClicked: () -> Unit = {},
    onPlaceNextClicked: () -> Unit = {},
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(32.dp)
    ) {
        Text(
            "どこでやる？",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            value = place,
            onValueChange = onPlaceChanged
        )
        HabbitCard(
            title = habbit,
            simpleHabbitTitle = simpleHabbitTitle,
            trigger = trigger,
            modifier = Modifier.offset(y = 16.dp)
        )

        Row(
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth()
        ) {
            Button(
                modifier = Modifier.padding(top = 16.dp),
                onClick = onPlaceCompleteClicked,
            ) {
                Text("これで作る")
            }

            Button(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .offset(12.dp),
                onClick = onPlaceNextClicked,
            ) {
                Text("達成率を上げる")
            }
        }
    }
}
