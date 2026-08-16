package ir.mhira.donow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import ir.mhira.donow.screen.settings.SettingsViewModel
import ir.mhira.donow.ui.theme.DoNowTheme

class MainActivity : ComponentActivity() {
    private val viewModel by lazy { (application as ApplicationClass).sharedViewModel }
    private val settingsViewModel: SettingsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewModel.loadTaskFromDatabase()

        setContent {
            val isDarkTheme by settingsViewModel.isDarkTheme.observeAsState(initial = false)
            DisposableEffect(isDarkTheme) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        lightScrim = Color.Transparent.toArgb(),
                        darkScrim = Color.Transparent.toArgb(),
                        detectDarkMode = { isDarkTheme }
                    ),
                    navigationBarStyle = SystemBarStyle.auto(
                        lightScrim = Color.Transparent.toArgb(),
                        darkScrim = Color.Transparent.toArgb(),
                        detectDarkMode = { isDarkTheme }
                    )
                )
                onDispose {}
            }


            DoNowTheme(
                darkTheme = isDarkTheme
            ) {

                MainPage(
                    viewModel = viewModel,
                    darkTheme = isDarkTheme,
                    onThemeChange = {
                        settingsViewModel.setDarkTheme(!isDarkTheme)
                    }
                )
            }
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    DoNowTheme {
//        DoNowTheme {
//            MainPage(false, {})
//        }
//    }
//}