package ir.mhira.donow.ui.theme


import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = appPrimary_dark,
    onPrimary = appOnPrimary_dark,

    primaryContainer = appPrimaryContainer_dark,
    onPrimaryContainer = appOnPrimaryContainer_dark,

    secondary = appSecondary_dark,
    onSecondary = appOnSecondary_dark,

    background = appBackground_dark,
    onBackground = appOnBackground_dark,

    surface = appSurface_dark,
    onSurface = appOnSurface_dark,

    surfaceVariant = appSurfaceVariant_dark,
    onSurfaceVariant = appOnSurfaceVariant_dark,

    outline = appOutline_dark,

    error = appError_dark,
)

private val LightColorScheme = lightColorScheme(
    primary = appPrimary_light,
    onPrimary = appOnPrimary_light,

    primaryContainer = appPrimaryContainer_light,
    onPrimaryContainer = appOnPrimaryContainer_light,

    secondary = appSecondary_light,
    onSecondary = appOnSecondary_light,

    background = appBackground_light,
    onBackground = appOnBackground_light,

    surface = appSurface_light,
    onSurface = appOnSurface_light,

    surfaceVariant = appSurfaceVariant_light,
    onSurfaceVariant = appOnSurfaceVariant_light,

    outline = appOutline_light,

    error = appError_light,


)

@Composable
fun DoNowTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}