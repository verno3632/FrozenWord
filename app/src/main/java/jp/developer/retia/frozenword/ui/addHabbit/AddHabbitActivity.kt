package jp.developer.retia.frozenword.ui.addHabbit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Surface
import android.widget.Button
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
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import jp.developer.retia.frozenword.R
import jp.developer.retia.frozenword.ui.theme.FrozenWordTheme
import jp.developer.retia.frozenword.ui.viewmodel.AddHabbitUiState
import jp.developer.retia.frozenword.ui.viewmodel.AddHabbitViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import org.w3c.dom.Text

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
