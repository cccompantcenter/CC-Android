package com.cc.commandcenter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cc.commandcenter.model.Screen
import com.cc.commandcenter.ui.theme.CcMidnight

@Composable
fun CCApp() {
    var currentScreen by remember { mutableStateOf(Screen.TODAY) }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(CcMidnight)
            .padding(24.dp)
    ) {
        Sidebar(
            currentScreen = currentScreen,
            onScreenSelected = { currentScreen = it }
        )

        Spacer(modifier = Modifier.width(24.dp))

        MainContent(currentScreen)
    }
}