package com.example.simpleapicallwithdi.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.simpleapicallwithdi.ui.theme.SimpleAPICallWithDITheme
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleAPICallWithDITheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
//                    Scanner()
                    //MainApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(/*vm: MainViewModel,*/ modifier: Modifier = Modifier) {
    val vm: MainViewModel = viewModel()
    val state = vm.state.collectAsState()
    var code by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current

    val barcodeLauncher = rememberLauncherForActivityResult(ScanContract()) { result ->
        if (result.contents == null) {
            Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG).show()
        } else {
            code = result.contents
            Toast.makeText(
                context,
                "Scanned: " + result.contents,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Column {

        Row(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = code, onValueChange = { newText ->
                    code = newText
                }, modifier = Modifier.weight(1f)
            )

            IconButton(
                onClick = {
                    barcodeLauncher.launch(ScanOptions().apply { setOrientationLocked(false) })
                },
            ) {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = "Localized description"
                )
            }
        }

        Button(onClick = { vm.postSearch(code, "br") }) {
            Text(text = "Submit")
        }

        Text(
            text = "${state.value.data?.data?.message}!",
            modifier = modifier.fillMaxWidth()
        )
        Text(
            text = " ${state.value.data?.data?.location?.big_location}!",
            modifier = modifier.fillMaxWidth()
        )
        Text(
            text = " ${state.value.data?.data?.location?.small_location}!",
            modifier = modifier.fillMaxWidth()
        )

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(vm: MainViewModel, navHostController: NavHostController) {
    ///val vm: MainViewModel = viewModel()
    val state = vm.state.collectAsState()

    Column {
        var text by remember { mutableStateOf(TextFieldValue(vm.code)) }

        TextField(
            value = text, onValueChange = { newText ->
                text = newText
            }
        )

        IconButton(onClick = {
            navHostController.navigate(NavScreen.Scanner.route)
        }) {
            Icon(
                Icons.Filled.Search,
                contentDescription = "Localized description"
            )
        }

        Text(
            text = "${state.value.data?.data?.message}!",
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = " ${state.value.data?.data?.location?.big_location}!",
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = " ${state.value.data?.data?.location?.small_location}!",
            modifier = Modifier.fillMaxWidth()
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp() {
    Scaffold(
        /*topBar = {
            TopNavBar(
                stringResource(id = appState.getTitle(appState.currentRoute)),
                appState.currentRoute,
                onNavigateBack = { appState.navigateBack() },
            )
        },
        bottomBar = {
            BottomNavBar(
                currentRoute = appState.currentRoute,
                showBottomNav = appState.showBottomNav,
                navigateToRoute = appState::navigateToBottomBarRoute
            )
        }*/
    ) { innerPadding ->
        AppNavHost(
            navController = rememberNavController(),
            innerPadding
        )
    }
}