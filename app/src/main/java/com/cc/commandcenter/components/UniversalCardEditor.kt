package com.cc.commandcenter.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.cc.commandcenter.data.CardRepository
import com.cc.commandcenter.model.Card
import com.cc.commandcenter.model.CardDestination
import com.cc.commandcenter.model.CardPriority
import com.cc.commandcenter.model.CardStatus
import com.cc.commandcenter.ui.theme.CcGold
import com.cc.commandcenter.ui.theme.CcMuted
import com.cc.commandcenter.ui.theme.CcText
import com.cc.commandcenter.util.formatDueDate

@Composable
fun UniversalCardEditor(
    cardId: Long,
    onBack: () -> Unit,
    onDelete: () -> Unit,
    onSave: (Card) -> Unit
) {
    val card = remember(cardId) { CardRepository.getCardById(cardId) }

    if (card == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            CcHeader(
                title = "Card niet gevonden",
                subtitle = "Deze Card is niet meer beschikbaar."
            )

            CcActionBar(
                onBack = onBack,
                onDelete = onBack,
                onSave = onBack,
                deleteLabel = "Sluiten"
            )
        }
        return
    }

    var title by remember(card.id) { mutableStateOf(card.title) }
    var description by remember(card.id) { mutableStateOf(card.description) }
    var priority by remember(card.id) { mutableStateOf(card.priority) }
    var status by remember(card.id) { mutableStateOf(card.status) }
    var favorite by remember(card.id) { mutableStateOf(card.favorite) }
    var destination by remember(card.id) { mutableStateOf(card.destination) }
    var tagsText by remember(card.id) { mutableStateOf(card.tags.joinToString(", ")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(22.dp)
    ) {
        CcPrimaryButton(
            text = "← Terug",
            onClick = onBack
        )

        CcHeader(
            title = title.ifBlank { "Nieuwe Card" },
            subtitle = "${card.category.label()} • ${priority.label()} • ${status.label()}"
        )

        Text(
            text = "S Pen eerst: schrijf of annoteer waar mogelijk met de pen. Toetsenbord blijft beschikbaar voor snelle correcties.",
            color = CcMuted,
            fontSize = 16.sp
        )

        SectionTitle("Titel of handgeschreven kop")

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = { title = it },
            singleLine = true,
            placeholder = {
                Text(
                    text = "Schrijf eerst met S Pen of vul later aan",
                    color = CcMuted
                )
            },
            colors = cardTextFieldColors()
        )

        SectionTitle("Aandachtsdatum")

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = formatDueDate(card.dueDate),
            onValueChange = { },
            singleLine = true,
            readOnly = true,
            placeholder = {
                Text(
                    text = "dd-MM-jjjj",
                    color = CcMuted
                )
            },
            colors = cardTextFieldColors()
        )

        SectionTitle("Beschrijving of handgeschreven uitwerking")

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = description,
            onValueChange = { description = it },
            minLines = 5,
            placeholder = {
                Text(
                    text = "Werk de Card uit met S Pen of typ een aanvulling",
                    color = CcMuted
                )
            },
            colors = cardTextFieldColors()
        )

        SectionTitle("Tags")

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = tagsText,
            onValueChange = { tagsText = it },
            singleLine = true,
            placeholder = {
                Text(
                    text = "Werk, Overleg",
                    color = CcMuted
                )
            },
            colors = cardTextFieldColors()
        )

        SectionTitle("Bestemming in workflow")

        ChoiceRow {
            userFacingDestinations.forEach { item ->
                ChoiceChip(item.label(), destination == item) {
                    destination = item
                }
            }
        }

        SectionTitle("Handgeschreven notities")

        Text(
            text = "+ Nieuwe notitie",
            color = CcGold,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = if (card.notes.isBlank()) "Nog geen notities." else card.notes,
            color = CcText,
            fontSize = 18.sp
        )

        if (card.originalGedachteId != null) {
            SectionTitle("Oorspronkelijke gedachte")

            Text(
                text = "Gedachte #${card.originalGedachteId}",
                color = CcText,
                fontSize = 18.sp
            )

            if (!card.originalGedachtePreview.isNullOrBlank()) {
                Text(
                    text = card.originalGedachtePreview,
                    color = CcMuted,
                    fontSize = 16.sp
                )
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Checkbox(
                checked = favorite,
                onCheckedChange = { favorite = it }
            )

            Text(
                text = "Favoriet",
                color = CcText,
                fontSize = 18.sp
            )
        }

        SectionTitle("Prioriteit")

        ChoiceRow {
            ChoiceChip("Laag", priority == CardPriority.LOW) { priority = CardPriority.LOW }
            ChoiceChip("Normaal", priority == CardPriority.NORMAL) { priority = CardPriority.NORMAL }
            ChoiceChip("Hoog", priority == CardPriority.HIGH) { priority = CardPriority.HIGH }
        }

        SectionTitle("Status")

        ChoiceRow {
            ChoiceChip("Open", status == CardStatus.OPEN) { status = CardStatus.OPEN }
            ChoiceChip("Voltooid", status == CardStatus.COMPLETED) { status = CardStatus.COMPLETED }
        }

        Spacer(modifier = Modifier.height(24.dp))

        CcActionBar(
            onBack = onBack,
            onDelete = {
                CardRepository.deleteCard(card.id)
                onDelete()
            },
            onSave = {
                val updatedCard = card.copy(
                    title = title,
                    description = description,
                    priority = priority,
                    status = status,
                    favorite = favorite,
                    destination = destination,
                    tags = tagsText.toTagList()
                )
                CardRepository.updateCard(updatedCard)
                onSave(updatedCard)
            }
        )
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        color = CcGold,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun ChoiceRow(content: @Composable () -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        content()
    }
}

@Composable
private fun ChoiceChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Text(
        text = if (selected) "● $text" else "○ $text",
        color = CcText,
        fontSize = 17.sp,
        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
        modifier = Modifier
            .clip(RoundedCornerShape(999.dp))
            .background(if (selected) CcGold.copy(alpha = 0.28f) else Color(0xFF211E18))
            .border(
                width = 1.dp,
                color = if (selected) CcGold else CcMuted.copy(alpha = 0.55f),
                shape = RoundedCornerShape(999.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 18.dp, vertical = 10.dp)
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
    unfocusedBorderColor = CcText.copy(alpha = 0.65f)
)

private fun com.cc.commandcenter.model.CardCategory.label() = when (this) {
    com.cc.commandcenter.model.CardCategory.FOCUS -> "Focus"
    com.cc.commandcenter.model.CardCategory.MY_TASKS -> "Mijn taken"
    com.cc.commandcenter.model.CardCategory.WAITING -> "Reactie afwachten"
    com.cc.commandcenter.model.CardCategory.OTHERS -> "Taken van anderen"
    com.cc.commandcenter.model.CardCategory.IDEAS -> "Ideeën"
    com.cc.commandcenter.model.CardCategory.ARCHIVE -> "Archief"
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

private fun CardDestination.label() = when (this) {
    CardDestination.FOCUS -> "Focus"
    CardDestination.MY_TASKS -> "Mijn taken"
    CardDestination.WAITING -> "Reactie afwachten"
    CardDestination.OTHERS -> "Taken van anderen"
    CardDestination.IDEAS -> "Ideeën"
    CardDestination.ARCHIVE -> "Archief"
    CardDestination.TODAY -> "Focus"
    CardDestination.PROJECT -> "Project"
    CardDestination.CALENDAR -> "Agenda"
    CardDestination.CONTACT -> "Contact"
    CardDestination.INBOX -> "Ideeën"
}

private val userFacingDestinations = listOf(
    CardDestination.FOCUS,
    CardDestination.MY_TASKS,
    CardDestination.WAITING,
    CardDestination.OTHERS,
    CardDestination.IDEAS,
    CardDestination.ARCHIVE
)

private fun String.toTagList(): List<String> = split(",")
    .map { it.trim() }
    .filter { it.isNotEmpty() }