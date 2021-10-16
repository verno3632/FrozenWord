package jp.developer.retia.frozenword.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import jp.developer.retia.frozenword.R
import jp.developer.retia.frozenword.ui.theme.FrozenWordTheme
import jp.developer.retia.frozenword.ui.viewmodel.AddHabbitEvent
import jp.developer.retia.frozenword.ui.viewmodel.AddHabbitUiState
import jp.developer.retia.frozenword.ui.viewmodel.AddHabbitViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@OptIn(InternalCoroutinesApi::class)
@AndroidEntryPoint
class AddHabbitActivity : ComponentActivity() {

    private val mainViewModel: AddHabbitViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is AddHabbitUiState.HabbitTitlePage -> show1stPane(
                            uiState.title,
                            uiState.sampleTitle
                        )
                        is AddHabbitUiState.ShortHabbitTitlePage -> TODO()
                        is AddHabbitUiState.TitlePage -> TODO()
                    }
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.events.collect { event ->
                    when (event) {
                        is AddHabbitEvent.Back -> onBackPressed()
                    }
                }
            }
        }
    }

    private fun show1stPane(defaultTitle: String, sampleTitles: List<String>) {
        setContent {
            var title: String by remember { mutableStateOf(defaultTitle) }
            FrozenWordTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    FirstPane(title = title, sampleTitles = sampleTitles, onTitleChanged = {
                        title = it
                        mainViewModel.onHabbitTitleUpdated(title)
                    }, onSuggestionClicked = {
                        mainViewModel.onHabbitTitleNextButtonClicked()
                    }, onTitleNextButtonClicked = {
                        mainViewModel.onHabbitTitleNextButtonClicked()
                    })
                }
            }
        }
    }

    companion object {
        fun createIntent(context: Context): Intent = Intent(context, AddHabbitActivity::class.java)
    }
}

@Preview
@Composable
fun PreviewFirstPane() {
    FrozenWordTheme {
        Surface {
            FirstPane(title = "hogehoge", sampleTitles = listOf("運動する", "勉強する", "絵を描く"))
        }
    }
}

@Composable
fun FirstPane(
    title: String,
    sampleTitles: List<String>,
    onTitleChanged: (String) -> Unit = {},
    onSuggestionClicked: (String) -> Unit = {},
    onTitleNextButtonClicked: () -> Unit = {},
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("継続したいことは？")
        OutlinedTextField(
            value = title,
            onValueChange = onTitleChanged
        )
        sampleTitles.take(3).forEach { Chip(it, onSuggestionClicked) }
        Button(onClick = onTitleNextButtonClicked) {
            Text("次へ")
        }
    }
}

// @Composable
// fun ListItem(task: Task, onChangedCheckbox: (Task, Boolean) -> Unit) {
//    val checkedState = remember { mutableStateOf(task.checked) }
//    ListItem(task.oldHabbit.title, checkedState.value, onCheckedChanged = {
//        checkedState.value = it
//        onChangedCheckbox(task, it)
//    })
// }

@Composable
fun ListItem(text: String, checkedState: Boolean, onCheckedChanged: (Boolean) -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Row(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Checkbox(checked = checkedState, onCheckedChange = onCheckedChanged)
            Text(text = text)
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

// @Composable
// fun TaskList(tasks: List<Task>, onChangedCheckbox: (Task, Boolean) -> Unit) {
//    LazyColumn {
//        items(tasks, { it.oldHabbit.id.hashCode() }) { task ->
//            ListItem(task, onChangedCheckbox)
//        }
//    }
// }

// @Preview(showBackground = true)
// @Composable
// fun DefaultPreview() {
//    Sample(
//        listOf(listOf(Task(Date(), false, OldHabbit("hogehoge"))))
//
//
//    ) { task, checked -> }
// }

// @Composable
// private fun Sample(tasksList: List<List<Task>>, onChangedCheckbox: (Task, Boolean) -> Unit) {
//    val navController = rememberNavController()
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text(stringResource(id = R.string.app_name)) },
//            )
//        },
//        modifier = Modifier.fillMaxSize(),
//        backgroundColor = MaterialTheme.colors.onBackground
//
//    ) {
//        TaskList(tasksList[0], onChangedCheckbox)
// //        Column(Modifier.fillMaxSize()) {
// //            // Display 10 items
// //            val pagerState = rememberPagerState(
// //                pageCount = tasksList.size,
// //                // We increase the offscreen limit, to allow pre-loading of images
// //                initialOffscreenLimit = 2,
// //                infiniteLoop = true,
// //            )
// //
// //            HorizontalPager(
// //                state = pagerState,
// //                // Add some horizontal spacing between items
// //                itemSpacing = 4.dp,
// //                modifier = Modifier
// //                    .weight(1f)
// //                    .fillMaxWidth()
// //            ) { page ->
// //            }
// //        }
//    }
// }
