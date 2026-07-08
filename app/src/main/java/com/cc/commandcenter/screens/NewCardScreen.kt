package com.cc.commandcenter.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cc.commandcenter.components.CcHeader
import com.cc.commandcenter.components.CcPrimaryButton
import com.cc.commandcenter.model.CardCategory
import com.cc.commandcenter.model.CardPriority
import java.time.LocalDate

@Composable
fun NewCardScreen(
    initialTitle: String = "",
    initialCategory: CardCategory = CardCategory.MY_TASKS,
    initialPriority: CardPriority = CardPriority.NORMAL,
    initialDueDate: String = LocalDate.now().toString(),
    onCancel: () -> Unit,
    onCreateCard: (
        title: String,
        category: CardCategory,
        priority: CardPriority,
        dueDate: String
    ) -> Unit
) {
    var title by remember(initialTitle) { mutableStateOf(initialTitle) }
    var category by remember(initialCategory) { mutableStateOf(initialCategory) }
    var priority by remember(initialPriority) { mutableStateOf(initialPriority) }
    var dueDate by remember(initialDueDate) { mutableStateOf(initialDueDate) }

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        CcHeader(
            title = "Nieuwe Card",
            subtitle = "Leg eerst de basis vast."
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = { title = it },
            label = { Text("Titel") },
            singleLine = true
        )

        Text("Categorie")
        CategoryOption("Focus", CardCategory.FOCUS, category) { category = it }
        CategoryOption("Mijn taken", CardCategory.MY_TASKS, category) { category = it }
        CategoryOption("Reactie afwachten", CardCategory.WAITING, category) { category = it }
        CategoryOption("Taken van anderen", CardCategory.OTHERS, category) { category = it }
        CategoryOption("Idee", CardCategory.IDEAS, category) { category = it }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = dueDate,
            onValueChange = { dueDate = it },
            label = { Text("Aandachtsdatum") },
            singleLine = true
        )

        Text("Prioriteit")
        PriorityOption("Laag", CardPriority.LOW, priority) { priority = it }
        PriorityOption("Normaal", CardPriority.NORMAL, priority) { priority = it }
        PriorityOption("Hoog", CardPriority.HIGH, priority) { priority = it }

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CcPrimaryButton(
                text = "Annuleren",
                onClick = onCancel
            )

            CcPrimaryButton(
                text = "Aanmaken",
                onClick = {
                    if (title.isNotBlank()) {
                        onCreateCard(
                            title,
                            category,
                            priority,
                            dueDate
                        )
                    }
                }
            )
        }
    }
}

@Composable
private fun CategoryOption(
    label: String,
    value: CardCategory,
    selected: CardCategory,
    onSelect: (CardCategory) -> Unit
) {
    CcPrimaryButton(
        text = if (value == selected) "● $label" else "○ $label",
        onClick = { onSelect(value) }
    )
}

@Composable
private fun PriorityOption(
    label: String,
    value: CardPriority,
    selected: CardPriority,
    onSelect: (CardPriority) -> Unit
) {
    CcPrimaryButton(
        text = if (value == selected) "● $label" else "○ $label",
        onClick = { onSelect(value) }
    )
}