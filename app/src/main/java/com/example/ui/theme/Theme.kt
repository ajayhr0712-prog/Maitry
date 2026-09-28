package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val MaitryLightColorScheme = lightColorScheme(
    primary = DeepTeal,
    onPrimary = Color.White,
    primaryContainer = DeepTealContainer,
    onPrimaryContainer = OnTealContainer,
    secondary = WarmCoral,
    onSecondary = Color.White,
    secondaryContainer = WarmCoralContainer,
    onSecondaryContainer = OnCoralContainer,
    tertiary = VerifiedGreen,
    onTertiary = Color.White,
    tertiaryContainer = VerifiedGreenContainer,
    background = CreamBackground,
    onBackground = TextPrimaryDark,
    surface = CreamSurface,
    onSurface = TextPrimaryDark,
    surfaceVariant = SurfaceVariantWarm,
    onSurfaceVariant = TextSecondaryMuted,
    outline = Color(0xFFC7D3D5),
    outlineVariant = Color(0xFFE2E9EA),
    error = SafeErrorRed,
    onError = Color.White,
    errorContainer = SafeErrorContainer
)

private val MaitryDarkColorScheme = darkColorScheme(
    primary = DarkTealPrimary,
    onPrimary = DeepTealDark,
    primaryContainer = DeepTeal,
    onPrimaryContainer = DeepTealContainer,
    secondary = DarkCoralSecondary,
    onSecondary = WarmCoralDark,
    secondaryContainer = Color(0xFF5D1F16),
    onSecondaryContainer = WarmCoralContainer,
    tertiary = Color(0xFF66DBB2),
    onTertiary = Color(0xFF003825),
    background = DarkBackground,
    onBackground = DarkTextPrimary,
    surface = DarkSurface,
    onSurface = DarkTextPrimary,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkTextSecondary,
    outline = Color(0xFF4C5D60),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005)
)

@Composable
fun MaitryTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) MaitryDarkColorScheme else MaitryLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

// Backward compatibility alias
@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaitryTheme(darkTheme = darkTheme, content = content)
}
