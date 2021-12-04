package jp.developer.retia.frozenword.ui.addHabbit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import jp.developer.retia.frozenword.ui.theme.FrozenWordTheme
import jp.developer.retia.frozenword.ui.viewmodel.AddHabbitViewModel
import kotlinx.coroutines.InternalCoroutinesApi

@OptIn(InternalCoroutinesApi::class)
@AndroidEntryPoint
class AddHabbitActivity : ComponentActivity() {

    private val mainViewModel: AddHabbitViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FrozenWordTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    AddHabbitScreen()
                }
            }
        }
    }

    companion object {
        fun createIntent(context: Context): Intent = Intent(context, AddHabbitActivity::class.java)
    }
}

@Composable
fun AddHabbitScreen(addHabbitViewModel: AddHabbitViewModel = viewModel()) {
    val state by addHabbitViewModel.uiState.collectAsState()

    AddHabbitScreen(
        state = state,
        onTitleChanged = addHabbitViewModel::onHabbitTitleUpdated,
        onSuggestionClicked = addHabbitViewModel::onSuggestionHabbitTitleClicked,
        onTitleNextButtonClicked = addHabbitViewModel::onHabbitTitleNextButtonClicked
    )
}
