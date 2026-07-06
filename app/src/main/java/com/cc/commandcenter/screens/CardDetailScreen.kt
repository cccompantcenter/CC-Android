package com.cc.commandcenter.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cc.commandcenter.components.CcHeader
import com.cc.commandcenter.components.CcPrimaryButton
import com.cc.commandcenter.model.Card
import com.cc.commandcenter.model.CardPriority
import com.cc.commandcenter.model.CardStatus
import com.cc.commandcenter.ui.theme.CcText

@Composable
fun CardDetailScreen(
    card: Card,
    onSave: (Card) -> Unit
) {
    var title by remember { mutableStateOf(card.title) }
    var description by remember { mutableStateOf(card.description) }
    var priority by remember { mutableStateOf(card.priority.toString()) }
    var status by remember { mutableStateOf(card.status.toString()) }
    var favorite by remember { mutableStateOf(card.favorite) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        CcHeader(
            title = "Card Detail",
            subtitle = card.category.toString()
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = { title = it },
            label = { Text("Titel") }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = description,
            onValueChange = { description = it },
            label = { Text("Beschrijving") },
            minLines = 5
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = priority,
                onValueChange = { priority = it },
                label = { Text("Prioriteit") }
            )

            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = status,
                onValueChange = { status = it },
                label = { Text("Status") }
            )
        }

        Row {
            Checkbox(
                checked = favorite,
                onCheckedChange = { favorite = it }
            )

            Text(
                text = "Favoriet",
                color = CcText
            )
        }

        Text(
            text = "Tags: ${card.tags.joinToString()}",
            color = CcText
        )

        Text(
            text = "Notities",
            color = CcText,
            fontSize = 20.sp
        )

        Text(
            text = if (card.notes.isBlank()) "Nog geen notities." else card.notes,
            color = CcText
        )

        CcPrimaryButton(
            text = "Opslaan",
            onClick = {
                val updatedCard = card.copy(
                    title = title,
                    description = description,
                    priority = CardPriority.valueOf(priority),
                    status = CardStatus.valueOf(status),
                    favorite = favorite
                )

                onSave(updatedCard)
            }
        )
    }
}