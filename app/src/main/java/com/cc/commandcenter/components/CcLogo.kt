package com.cc.commandcenter.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cc.commandcenter.R

@Composable
fun CcLogo(
    modifier: Modifier = Modifier,
    size: Dp = 42.dp
) {
    Image(
        painter = painterResource(id = R.drawable.cc_signature),
        contentDescription = "Nieuwe gedachte",
        modifier = modifier.size(size),
        contentScale = ContentScale.Fit
    )
}