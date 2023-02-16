package com.sidukov.kabar.data.colors

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

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