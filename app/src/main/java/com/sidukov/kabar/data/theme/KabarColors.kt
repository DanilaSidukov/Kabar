package com.sidukov.kabar.data.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

val black = Color(0xFF050505)
val black_2 = Color(0xFF000000)
val white = Color(0xFFFFFFFF)
val blue = Color(0xFF1877F2)
val anthracite = Color(0xFF4E4B66)
val red = Color(0xFFC30052)
val blue_2 = Color(0xFF5890FF)
val grey_background = Color(0xFFEEF1F4)
val grey_text = Color(0xFF667080)
val like_color = Color(0xFFED2E7E)
val transparent = Color(0x00FFFFFF)
val dark_color_black = Color(0xFFB0B3B8)
val black_background = Color(0xFF1C1E21)
val title_white = Color(0xFFE4E6EB)

data class KabarColors(
    val material: ColorScheme,
    val black : Color,
    val black_2 : Color,
    val white : Color,
    val blue : Color,
    val anthracite : Color,
    val red : Color,
    val blue_2 : Color,
    val grey_background : Color,
    val grey_text : Color,
    val like_color : Color,
    val rippleColor: Color
) {
    val primary: Color get() = material.primary
    val secondary: Color get() = black_2
    val background: Color get() = black
    val surface: Color get() = material.surface
    val error: Color get() = material.error
    val onPrimary: Color get() = material.onPrimary
    val onSecondary: Color get() = material.onSecondary
    val onBackground: Color get() = material.onBackground
    val onSurface: Color get() = material.onSurface
    val onError: Color get() = material.onError
}

val LightColors = KabarColors(
    material = lightColorScheme() ,
    black = black,
    black_2 = black_2,
    white = white,
    blue = blue,
    anthracite = anthracite,
    red = red,
    blue_2 = blue_2,
    grey_background = grey_background,
    grey_text = grey_text,
    like_color = like_color,
    rippleColor = transparent
)

val DarkColors = KabarColors(
    material = darkColorScheme() ,
    blue = blue,
    blue_2 = blue_2,
    anthracite = dark_color_black,
    white = black_background,
    grey_background = grey_background,
    grey_text = grey_text,
    red = red,
    black_2 = white,
    black = title_white,
    like_color = like_color,
    rippleColor = transparent
)

object KabarTheme {
    val colors: KabarColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

}

internal val LocalColors = staticCompositionLocalOf { LightColors }

object RippleEffect : RippleTheme {
    @Composable
    override fun defaultColor() = KabarTheme.colors.rippleColor

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleTheme.defaultRippleAlpha(
        KabarTheme.colors.rippleColor,
        lightTheme = !isSystemInDarkTheme(),
    )
}

@Composable
fun KabarTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) DarkColors else LightColors

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(Color.Transparent)
    }

    androidx.compose.material3.MaterialTheme(
        colorScheme = colors.material
    ) {
        CompositionLocalProvider(
            LocalColors provides colors,
            LocalRippleTheme provides RippleEffect,
            content = content,
        )
    }
}