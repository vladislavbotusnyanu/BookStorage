package com.morning_angel.book_storage.app.ui.screens.add_book

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.morning_angel.book_storage.app.ui.base.main.TopBarTitle
import com.morning_angel.book_storage.app.ui.theme.BookStorageTheme
import com.morning_angel.book_storage.app.util.toast
import com.morning_angel.book_storage.supportedMimeTypes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddBookActivity : ComponentActivity() {

    private lateinit var vm: AddBookViewModel

    private val selectBookContract =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) {
            vm.addBookThroughLocalFiles(it)
            toast(it?.path.toString())
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookStorageTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    vm = viewModel()
                    val state = vm.viewState.observeAsState(AddBookStates.defaultState)
                    checkState(state)

                    AddBookComposable(LocalContext.current, selectBookContract)
                }
            }
        }
    }

    private fun checkState(state: State<AddBookStates>) = when (state.value) {
        is AddBookStates.defaultState -> {}
        is AddBookStates.onBookAddedSuccessState -> {
            onBackPressedDispatcher.onBackPressed()
        }

        is AddBookStates.onShowErrorState -> {
            toast("Book add error ${state.value.error}")
            onBackPressedDispatcher.onBackPressed()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBookComposable(
    context : Context,
    selectBookContract: ActivityResultLauncher<Array<String>>,
) {
    TopBarTitle(title = "Add book") {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val flibustaText = "Get book from flibusta ( coming soon )"
                val royalLibText = "Get book from royallib ( coming soon )"
                AddBookButton(text = "Select file") {
                    selectBookContract.launch(supportedMimeTypes)
                }
                AddBookButton(text = flibustaText) { context.toast(flibustaText) }
                AddBookButton(text = royalLibText) { context.toast(royalLibText) }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBookButton(text: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().height(50.dp).padding(5.dp),
        onClick = { onClick.invoke() }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = text
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddBookPreview() {
    BookStorageTheme {
//        AddBookComposable(selectBookContract, supportedMimeTypes)
    }
}