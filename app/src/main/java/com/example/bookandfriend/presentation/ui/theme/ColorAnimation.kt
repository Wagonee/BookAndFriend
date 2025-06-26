package com.example.bookandfriend.presentation.ui.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

@Composable
fun animatedCustomColors(target: CustomColors): CustomColors {
    val textColor by animateColorAsState(target.textColor, label = "textColor")
    val background by animateColorAsState(target.background, label = "background")
    val secondBackground by animateColorAsState(target.secondBackground, label = "secondBackground")
    val thirdBackground by animateColorAsState(target.thirdBackground, label = "thirdBackground")
    val shadowColor by animateColorAsState(target.shadowColor, label = "shadowColor")
    val switchUnactive by animateColorAsState(target.switchInactive, label = "switchUnactive")
    val switchActive by animateColorAsState(target.switchActive, label = "switchActive")
    val switchCircle by animateColorAsState(target.switchCircle, label = "switchCircle")
    val heartUnactive by animateColorAsState(target.heartInactive, label = "heartUnactive")
    val heartActive by animateColorAsState(target.heartActive, label = "heartActive")
    val diceColor by animateColorAsState(target.diceColor, label = "diceColor")

    return CustomColors(
        textColor = textColor,
        background = background,
        secondBackground = secondBackground,
        thirdBackground = thirdBackground,
        shadowColor = shadowColor,
        switchInactive = switchUnactive,
        switchActive = switchActive,
        switchCircle = switchCircle,
        heartInactive = heartUnactive,
        heartActive = heartActive,
        diceColor = diceColor
    )
}