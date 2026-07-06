package com.cc.commandcenter.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.cc.commandcenter.R

@Composable
fun CcLogo(
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.cc_logo),
        contentDescription = "CC Command Center",
        modifier = modifier.height(72.dp)
    )
}