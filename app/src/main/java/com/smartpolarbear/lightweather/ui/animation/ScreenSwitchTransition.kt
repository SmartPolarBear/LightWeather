package com.smartpolarbear.lightweather.ui.animation

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment


@ExperimentalAnimationApi
@Composable
public fun ScreenSwitchAnimation(content: @Composable () -> Unit) {
    AnimatedVisibility(
        initiallyVisible = false,
        visible = true,

        content = content,

        enter = slideInVertically(initialOffsetY = { -40 })
                + expandVertically(expandFrom = Alignment.Top)
                + fadeIn(initialAlpha = 0.3f),
        exit = slideOutVertically()
                + shrinkVertically()
                + fadeOut()
    )
}
