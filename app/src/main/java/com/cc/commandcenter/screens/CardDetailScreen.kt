package com.cc.commandcenter.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import com.cc.commandcenter.model.Card
import com.cc.commandcenter.model.CardPriority
import com.cc.commandcenter.model.CardStatus

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
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Titel") }
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Beschrijving") }
        )

        OutlinedTextField(
            value = priority,
            onValueChange = { priority = it },
            label = { Text("Prioriteit") }
        )

        OutlinedTextField(
            value = status,
            onValueChange = { status = it },
            label = { Text("Status") }
        )

        Row {
            Checkbox(
                checked = favorite,
                onCheckedChange = { favorite = it }
            )

            Text(
                text = "Favoriet"
            )
        }

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
            text = if (card.notes.isBlank()) "Nog geen notities." else card.notes
        )

        Button(
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
        ) {
            Text("Opslaan")
        }
    }
}