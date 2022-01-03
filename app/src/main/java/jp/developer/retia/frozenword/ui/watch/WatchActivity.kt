package jp.developer.retia.frozenword.ui.watch

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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import jp.developer.retia.frozenword.ui.theme.FrozenWordTheme
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(InternalCoroutinesApi::class)
@AndroidEntryPoint
class WatchActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FrozenWordTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    WatchScreen()
                }
            }
        }
    }

    companion object {
        fun createIntent(context: Context): Intent = Intent(context, WatchActivity::class.java)
    }
}

@Composable
fun WatchScreen(
) {

    WatchScreen(
        title = "dummyTitle",
        simpleHabbitTitle = "dummySimpleTitle",
        trigger = "dummyTrigger",
        place = "dummyPlace"
    )
}