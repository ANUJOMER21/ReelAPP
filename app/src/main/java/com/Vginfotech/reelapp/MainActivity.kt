package com.Vginfotech.reelapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.Vginfotech.reelapp.API.ViewModel.ApiViewModel
import com.Vginfotech.reelapp.Navigation.NavigationPage
import com.Vginfotech.reelapp.ui.theme.ReelAPPTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainActivity : ComponentActivity(),KoinComponent {
    private val sharedPrefManager: SharedPrefManager by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ReelAPPTheme {
                   val navController= rememberNavController()
                    NavigationPage(sharedPrefManager.isLogin())

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ReelAPPTheme {
        Greeting("Android")
    }
}