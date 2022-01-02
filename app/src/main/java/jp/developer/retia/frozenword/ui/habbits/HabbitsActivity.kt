package jp.developer.retia.frozenword.ui.habbits

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import jp.developer.retia.frozenword.ui.addHabbit.AddHabbitActivity
import jp.developer.retia.frozenword.ui.theme.FrozenWordTheme
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@OptIn(InternalCoroutinesApi::class)
@AndroidEntryPoint
class HabbitsActivity : ComponentActivity() {

    private val habbitsViewModel: HabbitsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FrozenWordTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    HabbitsScreen(habbitsViewModel = habbitsViewModel)
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                habbitsViewModel.events.collect { event ->
                    when (event) {
                        HabbitsEvent.Back -> onBackPressed()
                        HabbitsEvent.NavigateToAdd -> navigateToAdd()
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        habbitsViewModel.onResume()
    }

    private fun navigateToAdd() {
        startActivity(Intent(this, AddHabbitActivity::class.java))
    }

    companion object {
        fun createIntent(context: Context): Intent = Intent(context, HabbitsActivity::class.java)
    }
}

@Composable
fun HabbitsScreen(habbitsViewModel: HabbitsViewModel) {
    val state by habbitsViewModel.uiState.collectAsState()

    Scaffold(
        floatingActionButton =
        {
            FloatingActionButton(onClick = { habbitsViewModel.onClickActionButton() }) {
                Icon(Icons.Filled.Add, contentDescription = "追加")
            }
        },
        content = {
            HabbitsScreen(state) { habbitsViewModel.onClickActionButton() }
        }
    )
}
