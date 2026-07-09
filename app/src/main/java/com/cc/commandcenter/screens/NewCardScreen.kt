package com.cc.commandcenter.screens
import androidx.compose.material3.OutlinedTextFieldDefaults
import com.cc.commandcenter.ui.theme.CcText
import com.cc.commandcenter.ui.theme.CcMuted
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
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
            subtitle = "Schrijf eerst vrij met je S Pen. Daarna kun je ordenen."
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 180.dp),
            value = title,
            onValueChange = { title = it },
            minLines = 6,
            placeholder = {
                Text(
                    text = "Schrijf hier je nieuwe card met S Pen...",
                    color = CcMuted
                )
            },
            colors = newCardTextFieldColors()
        )

        Text("Categorie")
        CategoryOption("Mijn taken", CardCategory.MY_TASKS, category) { category = it }
        CategoryOption("Reactie afwachten", CardCategory.WAITING, category) { category = it }
        CategoryOption("Focus", CardCategory.FOCUS, category) { category = it }
        CategoryOption("Taken van anderen", CardCategory.OTHERS, category) { category = it }
        CategoryOption("Idee", CardCategory.IDEAS, category) { category = it }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = dueDate,
            onValueChange = { dueDate = it },
            label = { Text("Aandachtsdatum") },
            singleLine = true,
            colors = newCardTextFieldColors()
        )

        Text("Prioriteit")
        PriorityOption("Laag", CardPriority.LOW, priority) { priority = it }
        PriorityOption("Normaal", CardPriority.NORMAL, priority) { priority = it }
        PriorityOption("Hoog", CardPriority.HIGH, priority) { priority = it }

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CcPrimaryButton(
                text = "Terug",
                onClick = onCancel
            )

            CcPrimaryButton(
                text = "Wissen",
                onClick = {
                    title = ""
                    priority = CardPriority.NORMAL
                    category = CardCategory.MY_TASKS
                    dueDate = LocalDate.now().toString()
                }
            )

            CcPrimaryButton(
                text = "Opslaan",
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
@Composable
private fun newCardTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = CcText,
    unfocusedTextColor = CcText,
    focusedContainerColor = androidx.compose.ui.graphics.Color.Transparent,
    unfocusedContainerColor = androidx.compose.ui.graphics.Color.Transparent,
    focusedBorderColor = CcText,
    unfocusedBorderColor = CcMuted,
    cursorColor = CcText,
    focusedPlaceholderColor = CcMuted,
    unfocusedPlaceholderColor = CcMuted
)