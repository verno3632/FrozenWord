package jp.developer.retia.frozenword.ui.addHabbit

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.developer.retia.frozenword.ui.habbits.HabbitCard

@Preview(showBackground = true)
@Composable
fun PreviewAddSimpleHabbitTitleScreen() {
    AddSimpleHabbitTitleScreen(habbit = "運動", simpleHabbitTitle = "運動着に着替える", buttonEnabled = false)
}

@Composable
fun AddSimpleHabbitTitleScreen(
    habbit: String,
    onSimpleHabbitTitleCompleteClicked: (String) -> Unit = {},
    onNextClicked: (String) -> Unit = {},
) {
    val (simpleHabbitTitle, setSimpleHabbitTitle) = remember { mutableStateOf("") }

    AddSimpleHabbitTitleScreen(
        habbit = habbit,
        simpleHabbitTitle = simpleHabbitTitle,
        buttonEnabled = simpleHabbitTitle.isNotBlank(),
        onSimpleHabbitTitleChanged = setSimpleHabbitTitle,
        onSimpleHabbitTitleCompleteClicked = { onSimpleHabbitTitleCompleteClicked(simpleHabbitTitle) },
        onNextClicked = { onNextClicked(simpleHabbitTitle) }
    )
}

@Composable
fun AddSimpleHabbitTitleScreen(
    habbit: String,
    simpleHabbitTitle: String,
    buttonEnabled: Boolean,
    onSimpleHabbitTitleChanged: (String) -> Unit = {},
    onSimpleHabbitTitleCompleteClicked: () -> Unit = {},
    onNextClicked: () -> Unit = {},
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(32.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(habbit, style = MaterialTheme.typography.h6)
            Text("のために1分でできる習慣は？", style = MaterialTheme.typography.h6)
            Text("1分間の習慣を行えばその日は達成！忙しければそこで終わっても良いし、やる気があれば続けてやっても良いです！")
        }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            value = simpleHabbitTitle,
            onValueChange = onSimpleHabbitTitleChanged
        )

        HabbitCard(
            simpleHabbitTitle = simpleHabbitTitle,
            title = habbit,
            trigger = "",
            modifier = Modifier.offset(y = 16.dp),
            length = 1
        )

        Row(
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth()
                .offset(y = 4.dp)
        ) {
            Button(
                modifier = Modifier.padding(top = 16.dp),
                onClick = onSimpleHabbitTitleCompleteClicked,
                enabled = buttonEnabled
            ) {
                Text("これで作る")
            }

            Button(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .offset(12.dp),
                onClick = onNextClicked,
                enabled = buttonEnabled
            ) {
                Text("達成率を上げる")
            }
        }
    }
}
