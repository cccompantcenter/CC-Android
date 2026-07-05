package com.cc.commandcenter.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.Composable
import com.cc.commandcenter.model.Card
import com.cc.commandcenter.ui.theme.CcMuted
import com.cc.commandcenter.ui.theme.CcText

@Composable
fun CardDetailScreen(
    card: Card
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Text(
            text = card.title,
            color = CcText,
            fontSize = 30.sp
        )

        Text(
            text = card.description,
            color = CcMuted,
            fontSize = 18.sp
        )

        Text(
            text = "Status: ${card.status}"
        )

        Text(
            text = "Prioriteit: ${card.priority}"
        )

        Text(
            text = "Categorie: ${card.category}"
        )

        Text(
            text = "Tags: ${card.tags.joinToString()}"
        )

        Text(
            text = "Notities"

        )

        Text(
            text = if (card.notes.isBlank())
                "Nog geen notities."
            else
                card.notes,
            color = CcMuted
        )
    }
}