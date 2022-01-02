package jp.developer.retia.frozenword.ui.addHabbit

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.developer.retia.frozenword.ui.theme.FrozenWordTheme

@Preview(showBackground = true)
@Composable
fun PreviewAddSimpleHabbitScreen() {
    AddSimpleHabbitScreen(habbit = "運動", simpleHabbit = "運動着に着替える", buttonEnabled = false)
}

@Composable
fun AddSimpleHabbitScreen(
    habbit: String,
    onSimpleHabbitChanged: (String) -> Unit = {},
    onCompleteClicked: () -> Unit = {},
    onNextClicked: () -> Unit = {},
) {
    var simpleHabbit: String by remember { mutableStateOf("") }

    AddSimpleHabbitScreen(
        habbit = habbit,
        simpleHabbit = simpleHabbit,
        buttonEnabled = simpleHabbit.isNotBlank(),
        onSimpleHabbitChanged = { changed ->
            simpleHabbit = changed
            onSimpleHabbitChanged(simpleHabbit)
        },
        onCompleteClicked = onCompleteClicked,
        onNextClicked = onNextClicked
    )
}

@Composable
fun AddSimpleHabbitScreen(
    habbit: String,
    simpleHabbit: String,
    buttonEnabled: Boolean,
    onSimpleHabbitChanged: (String) -> Unit = {},
    onCompleteClicked: () -> Unit = {},
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
            Text(habbit, style = MaterialTheme.typography.subtitle1)
            Text("のために1分でできる習慣は？", style = MaterialTheme.typography.subtitle1)
            Text("1分間の習慣を行えばその日は達成！忙しければそこで終わっても良いし、やる気があれば続けてやっても良いです！")
        }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            value = simpleHabbit,
            onValueChange = onSimpleHabbitChanged
        )
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth()
        ) {
            Button(
                modifier = Modifier.padding(top = 16.dp),
                onClick = onCompleteClicked,
                enabled = buttonEnabled
            ) {
                Text("これで作る")
            }

            Button(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .offset(12.dp),
                onClick = onCompleteClicked,
                enabled = buttonEnabled
            ) {
                Text("達成率を上げる")
            }
        }
    }
}
