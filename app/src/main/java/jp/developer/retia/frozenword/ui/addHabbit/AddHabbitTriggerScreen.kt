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
fun PreviewAddHabbitTriggerScreen() {
    AddHabbitTriggerScreen(
        habbit = "運動",
        simpleHabbitTitle = "運動着に着替える",
        trigger = "朝起きたら",
        buttonEnabled = false
    )
}

@Composable
fun AddHabbitTriggerScreen(
    habbit: String,
    simpleHabbitTitle: String,
    onHabbitTriggerCompleteClicked: (String) -> Unit = {},
    onNextClicked: () -> Unit = {},
) {
    val (trigger, setTrigger) = remember { mutableStateOf("") }

    AddHabbitTriggerScreen(
        habbit = habbit,
        simpleHabbitTitle = simpleHabbitTitle,
        trigger = trigger,
        buttonEnabled = simpleHabbitTitle.isNotBlank(),
        onTriggerChanged = setTrigger,
        onTriggerCompleteClicked = { onHabbitTriggerCompleteClicked(trigger) },
        onTriggerNextClicked = onNextClicked
    )
}

@Composable
fun AddHabbitTriggerScreen(
    habbit: String,
    simpleHabbitTitle: String,
    trigger: String,
    buttonEnabled: Boolean,
    onTriggerChanged: (String) -> Unit = {},
    onTriggerCompleteClicked: () -> Unit = {},
    onTriggerNextClicked: () -> Unit = {},
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
            "いつやる？",
            style = MaterialTheme.typography.h1,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            value = trigger,
            onValueChange = onTriggerChanged
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
                onClick = onTriggerCompleteClicked,
                enabled = buttonEnabled
            ) {
                Text("これで作る")
            }

            Button(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .offset(12.dp),
                onClick = onTriggerNextClicked,
                enabled = buttonEnabled
            ) {
                Text("達成率を上げる")
            }
        }
    }
}
