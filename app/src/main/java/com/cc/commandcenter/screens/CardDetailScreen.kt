package com.cc.commandcenter.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cc.commandcenter.components.CcPrimaryButton
import com.cc.commandcenter.model.Card
import com.cc.commandcenter.model.CardCategory
import com.cc.commandcenter.model.CardPriority
import com.cc.commandcenter.model.CardStatus
import com.cc.commandcenter.ui.theme.CcGold
import com.cc.commandcenter.ui.theme.CcMuted
import com.cc.commandcenter.ui.theme.CcText

@Composable
fun CardDetailScreen(
    card: Card,
    onBack: () -> Unit,
    onDelete: () -> Unit,
    onSave: (Card) -> Unit
) {
    var title by remember { mutableStateOf(card.title) }
    var description by remember { mutableStateOf(card.description) }
    var priority by remember { mutableStateOf(card.priority) }
    var status by remember { mutableStateOf(card.status) }
    var favorite by remember { mutableStateOf(card.favorite) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(22.dp)
    ) {
        CardDetailHeader(
            title = title,
            category = card.category,
            priority = priority,
            status = status
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = { title = it },
            label = { Text("Titel") },
            singleLine = true,
            colors = cardTextFieldColors()
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = description,
            onValueChange = { description = it },
            label = { Text("Beschrijving") },
            minLines = 5,
            colors = cardTextFieldColors()
        )

        SectionTitle("Prioriteit")

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            ChoiceChip("Laag", priority == CardPriority.LOW) { priority = CardPriority.LOW }
            ChoiceChip("Normaal", priority == CardPriority.NORMAL) { priority = CardPriority.NORMAL }
            ChoiceChip("Hoog", priority == CardPriority.HIGH) { priority = CardPriority.HIGH }
        }

        SectionTitle("Status")

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            ChoiceChip("Open", status == CardStatus.OPEN) { status = CardStatus.OPEN }
            ChoiceChip("Voltooid", status == CardStatus.COMPLETED) { status = CardStatus.COMPLETED }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Checkbox(
                checked = favorite,
                onCheckedChange = { favorite = it }
            )

            Text(
                text = "Favoriet",
                color = CcText,
                fontSize = 17.sp
            )
        }

        SectionTitle("Tags")

        Text(
            text = if (card.tags.isEmpty()) "Nog geen tags." else card.tags.joinToString("   ") { "🏷 $it" },
            color = CcText,
            fontSize = 17.sp
        )

        SectionTitle("Notities")

        Text(
            text = "+ Nieuwe notitie",
            color = CcGold,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )

        if (card.notes.isNotBlank()) {
            Text(
                text = card.notes,
                color = CcText,
                fontSize = 16.sp
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CcPrimaryButton(
                text = "Terug",
                onClick = onBack
            )

            CcPrimaryButton(
                text = "Verwijderen",
                onClick = onDelete
            )

            CcPrimaryButton(
                text = "Opslaan",
                onClick = {
                    val updatedCard = card.copy(
                        title = title,
                        description = description,
                        priority = priority,
                        status = status,
                        favorite = favorite
                    )

                    onSave(updatedCard)
                }
            )
        }
    }
}

@Composable
private fun CardDetailHeader(
    title: String,
    category: CardCategory,
    priority: CardPriority,
    status: CardStatus
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Command Center",
            color = CcGold,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            text = title.ifBlank { "Nieuwe Card" },
            color = CcText,
            fontSize = 38.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 44.sp
        )

        Text(
            text = "${category.label()} • ${priority.label()} • ${status.label()}",
            color = CcText,
            fontSize = 17.sp
        )
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        color = CcText,
        fontSize = 21.sp,
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
private fun ChoiceChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Text(
        text = if (selected) "● $text" else "○ $text",
        color = if (selected) CcText else CcMuted,
        fontSize = 16.sp,
        modifier = Modifier
            .clip(RoundedCornerShape(999.dp))
            .background(if (selected) CcGold.copy(alpha = 0.22f) else Color(0xFF181612))
            .border(
                width = 1.dp,
                color = if (selected) CcGold.copy(alpha = 0.75f) else CcMuted.copy(alpha = 0.30f),
                shape = RoundedCornerShape(999.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 9.dp)
    )
}

@Composable
private fun cardTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = CcText,
    unfocusedTextColor = CcText,
    focusedLabelColor = CcGold,
    unfocusedLabelColor = CcText,
    cursorColor = CcGold,
    focusedBorderColor = CcGold,
    unfocusedBorderColor = CcMuted.copy(alpha = 0.65f)
)

private fun CardCategory.label() = when (this) {
    CardCategory.FOCUS -> "Focus"
    CardCategory.MY_TASKS -> "Mijn taken"
    CardCategory.WAITING -> "Reactie afwachten"
    CardCategory.OTHERS -> "Taken van anderen"
    CardCategory.IDEAS -> "Ideeën"
    CardCategory.ARCHIVE -> "Archief"
}

private fun CardPriority.label() = when (this) {
    CardPriority.LOW -> "Laag"
    CardPriority.NORMAL -> "Normaal"
    CardPriority.HIGH -> "Hoog"
}

private fun CardStatus.label() = when (this) {
    CardStatus.OPEN -> "Open"
    CardStatus.COMPLETED -> "Voltooid"
}