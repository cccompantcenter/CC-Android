package com.cc.commandcenter.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cc.commandcenter.CardPlaceholder

@Composable
fun TodayScreen() {
    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            CardPlaceholder(
                title = "Focus",
                body = "Vandaag begint met één duidelijke focus. Deze Card wordt straks klikbaar en krijgt dezelfde Master Card-structuur als alle andere Cards."
            )

            Spacer(modifier = Modifier.height(20.dp))

            CardPlaceholder(
                title = "Vandaag vraagt aandacht",
                body = "Hier komen straks de Cards die vandaag belangrijk zijn."
            )
        }

        Spacer(modifier = Modifier.width(24.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            CardPlaceholder(
                title = "Snelle notitie",
                body = "Hier komt straks het eerste schrijfvlak voor snelle invoer met de S Pen."
            )

            Spacer(modifier = Modifier.height(20.dp))

            CardPlaceholder(
                title = "Later slim maken",
                body = "AI, herkenning en automatische voorstellen komen pas nadat de basis betrouwbaar werkt."
            )
        }
    }
}