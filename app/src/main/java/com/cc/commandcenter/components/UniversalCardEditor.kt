package com.cc.commandcenter.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.cc.commandcenter.data.CardRepository
import com.cc.commandcenter.model.Card
import com.cc.commandcenter.model.CardCategory
import com.cc.commandcenter.model.CardDestination
import com.cc.commandcenter.model.CardPriority
import com.cc.commandcenter.model.CardStatus
import com.cc.commandcenter.ui.theme.CcGold
import com.cc.commandcenter.ui.theme.CcMuted
import com.cc.commandcenter.ui.theme.CcText
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@Composable
fun UniversalCardEditor(
    cardId: Long,
    autoFocusPrimaryInput: Boolean = false,
    onBack: () -> Unit,
    onDelete: () -> Unit,
    onSave: (Card) -> Unit
) {
    val card = remember(cardId) {
        CardRepository.getCardById(cardId)
    }

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

    var title by remember(card.id) {
        mutableStateOf(card.title)
    }

    var description by remember(card.id) {
        mutableStateOf(card.description)
    }

    var priority by remember(card.id) {
        mutableStateOf(card.priority)
    }

    var status by remember(card.id) {
        mutableStateOf(card.status)
    }

    var favorite by remember(card.id) {
        mutableStateOf(card.favorite)
    }

    var notes by remember(card.id) {
        mutableStateOf(card.notes)
    }

    var destination by remember(card.id) {
        mutableStateOf(
            card.destination.toUserFacingDestination(card.category)
        )
    }

    var dueDateText by remember(card.id) {
        mutableStateOf(card.dueDate.orEmpty())
    }

    var tagsText by remember(card.id) {
        mutableStateOf(card.tags.joinToString(", "))
    }

    var detailsExpanded by remember(card.id) {
        mutableStateOf(false)
    }

    var shouldRequestTitleFocus by remember(
        card.id,
        autoFocusPrimaryInput
    ) {
        mutableStateOf(autoFocusPrimaryInput)
    }

    LaunchedEffect(card.id, autoFocusPrimaryInput) {
        shouldRequestTitleFocus = autoFocusPrimaryInput
    }

    fun saveCard() {
        val normalizedDueDate = normalizeDueDateInput(
            input = dueDateText,
            fallback = card.dueDate
        )

        val updatedCard = card.copy(
            title = title,
            description = description,
            notes = notes,
            category = destination.toCardCategory(card.category),
            priority = priority,
            status = status,
            favorite = favorite,
            destination = destination,
            dueDate = normalizedDueDate,
            tags = tagsText.toTagList()
        )

        CardRepository.updateCard(updatedCard)
        onSave(updatedCard)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF16130F))
                    .padding(
                        horizontal = 24.dp,
                        vertical = 16.dp
                    )
            ) {
                CcActionBar(
                    onBack = onBack,
                    onDelete = {
                        CardRepository.deleteCard(card.id)
                        onDelete()
                    },
                    onSave = {
                        saveCard()
                    }
                )
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(
                    horizontal = 32.dp,
                    vertical = 28.dp
                ),
            verticalArrangement = Arrangement.spacedBy(22.dp)
        ) {
            CcHeader(
                title = title.ifBlank { "Nieuwe Card" },
                subtitle = "Ruimte om te schrijven, ordenen en verder te denken."
            )

            if (card.originalGedachteId != null) {
                OriginalThoughtSection(
                    gedachteId = card.originalGedachteId,
                    preview = card.originalGedachtePreview
                )
            }

            SectionTitle("Titel")

            SpenInputField(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp),
                value = title,
                onValueChange = {
                    title = it
                },
                placeholder = "Geef je gedachte een korte kop",
                minLines = 2,
                singleLine = false,
                autofocus = shouldRequestTitleFocus,
                onAutofocusApplied = {
                    shouldRequestTitleFocus = false
                }
            )

            SectionTitle("Schrijfruimte")

            SpenInputField(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 520.dp),
                value = notes,
                onValueChange = {
                    notes = it
                },
                placeholder = "Schrijf hier verder met je S Pen of toetsenbord",
                minLines = 20,
                minHeight = 520.dp,
                singleLine = false
            )

            DetailsHeader(
                expanded = detailsExpanded,
                onClick = {
                    detailsExpanded = !detailsExpanded
                }
            )

            AnimatedVisibility(
                visible = detailsExpanded
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(22.dp)
                ) {
                    SectionTitle("Categorie")

                    ChoiceGroup {
                        userFacingDestinations.forEach { item ->
                            ChoiceChip(
                                text = item.label(),
                                selected = destination == item,
                                onClick = {
                                    destination = item
                                }
                            )
                        }
                    }

                    SectionTitle("Status")

                    ChoiceGroup {
                        ChoiceChip(
                            text = "Open",
                            selected = status == CardStatus.OPEN,
                            onClick = {
                                status = CardStatus.OPEN
                            }
                        )

                        ChoiceChip(
                            text = "Voltooid",
                            selected = status == CardStatus.COMPLETED,
                            onClick = {
                                status = CardStatus.COMPLETED
                            }
                        )
                    }

                    SectionTitle("Prioriteit")

                    ChoiceGroup {
                        ChoiceChip(
                            text = "Laag",
                            selected = priority == CardPriority.LOW,
                            onClick = {
                                priority = CardPriority.LOW
                            }
                        )

                        ChoiceChip(
                            text = "Normaal",
                            selected = priority == CardPriority.NORMAL,
                            onClick = {
                                priority = CardPriority.NORMAL
                            }
                        )

                        ChoiceChip(
                            text = "Hoog",
                            selected = priority == CardPriority.HIGH,
                            onClick = {
                                priority = CardPriority.HIGH
                            }
                        )
                    }

                    SectionTitle("Aandachtsdatum")

                    SpenInputField(
                        modifier = Modifier.fillMaxWidth(),
                        value = dueDateText,
                        onValueChange = {
                            dueDateText = it
                        },
                        placeholder = "jjjj-mm-dd of dd-mm-jjjj",
                        minLines = 1,
                        singleLine = true
                    )

                    SectionTitle("Tags")

                    SpenInputField(
                        modifier = Modifier.fillMaxWidth(),
                        value = tagsText,
                        onValueChange = {
                            tagsText = it
                        },
                        placeholder = "Werk, overleg",
                        minLines = 1,
                        singleLine = true
                    )

                    Text(
                        text = "Gebruik korte tags, gescheiden door komma’s.",
                        color = CcMuted,
                        fontSize = 14.sp
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                favorite = !favorite
                            },
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Checkbox(
                            checked = favorite,
                            onCheckedChange = {
                                favorite = it
                            }
                        )

                        Text(
                            text = "Favoriet",
                            color = CcText,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(top = 11.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun OriginalThoughtSection(
    gedachteId: Long,
    preview: String?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Color(0xFF211E18))
            .border(
                width = 1.dp,
                color = CcGold.copy(alpha = 0.45f),
                shape = RoundedCornerShape(18.dp)
            )
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Oorspronkelijke gedachte",
            color = CcGold,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Gedachte #$gedachteId",
            color = CcText,
            fontSize = 17.sp
        )

        if (!preview.isNullOrBlank()) {
            Text(
                text = preview,
                color = CcMuted,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
private fun DetailsHeader(
    expanded: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF211E18))
            .border(
                width = 1.dp,
                color = CcMuted.copy(alpha = 0.4f),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable {
                onClick()
            }
            .padding(
                horizontal = 20.dp,
                vertical = 16.dp
            )
    ) {
        Text(
            text = if (expanded) {
                "▼ Details verbergen"
            } else {
                "▶ Details tonen"
            },
            color = CcGold,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun SectionTitle(
    text: String
) {
    Text(
        text = text,
        color = CcGold,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ChoiceGroup(
    content: @Composable () -> Unit
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
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
        text = if (selected) {
            "● $text"
        } else {
            "○ $text"
        },
        color = CcText,
        fontSize = 17.sp,
        fontWeight = if (selected) {
            FontWeight.Bold
        } else {
            FontWeight.Normal
        },
        modifier = Modifier
            .clip(RoundedCornerShape(999.dp))
            .background(
                if (selected) {
                    CcGold.copy(alpha = 0.28f)
                } else {
                    Color(0xFF211E18)
                }
            )
            .border(
                width = 1.dp,
                color = if (selected) {
                    CcGold
                } else {
                    CcMuted.copy(alpha = 0.55f)
                },
                shape = RoundedCornerShape(999.dp)
            )
            .clickable {
                onClick()
            }
            .padding(
                horizontal = 18.dp,
                vertical = 10.dp
            )
    )
}

private fun normalizeDueDateInput(
    input: String,
    fallback: String?
): String? {
    val trimmed = input.trim()

    if (trimmed.isEmpty()) {
        return null
    }

    return parseIsoDate(trimmed)
        ?: parseDutchDate(trimmed)
        ?: fallback
}

private fun parseIsoDate(
    value: String
): String? = try {
    LocalDate.parse(value).toString()
} catch (_: DateTimeParseException) {
    null
}

private fun parseDutchDate(
    value: String
): String? = try {
    LocalDate.parse(
        value,
        DateTimeFormatter.ofPattern("dd-MM-yyyy")
    ).toString()
} catch (_: DateTimeParseException) {
    null
}

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

private fun CardDestination.toUserFacingDestination(
    category: CardCategory
): CardDestination = when (this) {
    CardDestination.FOCUS,
    CardDestination.MY_TASKS,
    CardDestination.WAITING,
    CardDestination.OTHERS,
    CardDestination.IDEAS,
    CardDestination.ARCHIVE -> this

    CardDestination.TODAY,
    CardDestination.PROJECT,
    CardDestination.CALENDAR,
    CardDestination.CONTACT,
    CardDestination.INBOX -> when (category) {
        CardCategory.FOCUS -> CardDestination.FOCUS
        CardCategory.MY_TASKS -> CardDestination.MY_TASKS
        CardCategory.WAITING -> CardDestination.WAITING
        CardCategory.OTHERS -> CardDestination.OTHERS
        CardCategory.IDEAS -> CardDestination.IDEAS
        CardCategory.ARCHIVE -> CardDestination.ARCHIVE
    }
}

private fun CardDestination.toCardCategory(
    currentCategory: CardCategory
): CardCategory = when (this) {
    CardDestination.FOCUS -> CardCategory.FOCUS
    CardDestination.MY_TASKS -> CardCategory.MY_TASKS
    CardDestination.WAITING -> CardCategory.WAITING
    CardDestination.OTHERS -> CardCategory.OTHERS
    CardDestination.IDEAS -> CardCategory.IDEAS
    CardDestination.ARCHIVE -> CardCategory.ARCHIVE
    CardDestination.TODAY -> CardCategory.FOCUS

    CardDestination.PROJECT,
    CardDestination.CALENDAR,
    CardDestination.CONTACT,
    CardDestination.INBOX -> currentCategory
}

private fun String.toTagList(): List<String> =
    split(",")
        .map {
            it.trim()
        }
        .filter {
            it.isNotEmpty()
        }