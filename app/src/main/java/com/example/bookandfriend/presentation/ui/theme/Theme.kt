package com.example.bookandfriend.presentation.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

data class CustomColors(
    val textColor: Color,
    val background: Color,
    val secondBackground: Color,
    val thirdBackground: Color,
    val shadowColor: Color,
    val switchInactive: Color,
    val switchActive: Color,
    val switchCircle: Color,
    val heartInactive: Color,
    val heartActive: Color,
    val diceColor: Color
)

val LightCustomColors = CustomColors(
    textColor = LT_textColor,
    background = LT_background,
    secondBackground = LT_secondBackground,
    thirdBackground = LT_thirdBackground,
    shadowColor = LT_shadowColor,
    switchInactive = LT_switch_inactive,
    switchActive = LT_switch_active,
    switchCircle = LT_switch_circle,
    heartInactive = HeartInactive,
    heartActive = HeartActive,
    diceColor = DiceColor
)

val DarkCustomColors = CustomColors(
    textColor = DT_textColor,
    background = DT_background,
    secondBackground = DT_secondBackground,
    thirdBackground = DT_secondBackground,
    shadowColor = DT_shadowColor,
    switchInactive = DT_switch_inactive,
    switchActive = DT_switch_active,
    switchCircle = DT_switch_circle,
    heartInactive = HeartInactive,
    heartActive = HeartActive,
    diceColor = DiceColor
)

val LocalCustomColors = staticCompositionLocalOf { LightCustomColors }

@Composable
fun BookAndFriendTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val customColors = if (darkTheme) DarkCustomColors else LightCustomColors
    val materialColors = if (darkTheme) darkColorScheme() else lightColorScheme()

    CompositionLocalProvider(LocalCustomColors provides customColors) {
        MaterialTheme(
            colorScheme = materialColors,
            typography = Typography,
            content = content
        )
    }
}