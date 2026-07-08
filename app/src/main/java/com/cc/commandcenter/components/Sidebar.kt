package com.cc.commandcenter.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cc.commandcenter.R
import com.cc.commandcenter.model.Screen
import com.cc.commandcenter.ui.theme.CcGraphite
import com.cc.commandcenter.ui.theme.CcMuted

@Composable
fun Sidebar(
    currentScreen: Screen,
    onScreenSelected: (Screen) -> Unit
) {
    Column(
        modifier = Modifier
            .width(260.dp)
            .fillMaxHeight()
            .clip(RoundedCornerShape(32.dp))
            .background(CcGraphite)
            .padding(24.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.cc_signature),
            contentDescription = "Nieuwe gedachte",
            modifier = Modifier
                .height(72.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        Screen.values().forEach { screen ->
            NavigationItem(
                screen = screen,
                selected = screen == currentScreen,
                onClick = { onScreenSelected(screen) }
            )

            Spacer(modifier = Modifier.height(10.dp))
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Alpha 0.2.1",
            color = CcMuted,
            fontSize = 13.sp
        )
    }
}