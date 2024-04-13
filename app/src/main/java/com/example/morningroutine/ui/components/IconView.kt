package com.example.morningroutine.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

data class IconResource(
    @DrawableRes val drawableRes: Int,
)

@Composable
fun IconView(
    modifier: Modifier = Modifier,
    icon: IconResource,
) {
    Icon(
        modifier = modifier,
        painter = painterResource(id = icon.drawableRes),
        contentDescription = null
    )
}