package com.cc.commandcenter.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cc.commandcenter.model.Card
import com.cc.commandcenter.model.CardCategory
import com.cc.commandcenter.model.CardPriority
import com.cc.commandcenter.model.CardStatus
import com.cc.commandcenter.ui.theme.CcMuted
import com.cc.commandcenter.ui.theme.CcText

@Composable
fun FocusScreen() {
    var status by remember { mutableStateOf(CardStatus.OPEN) }

    val card = Card(
        id = 1L,
        title = "Eerste echte CC Card",
        description = "Deze Card gebruikt nu het centrale Card-model. Daarmee bouwen we niet meer losse schermtekst, maar het hart van CC.",
        category = CardCategory.FOCUS,
        priority = CardPriority.HIGH,
        status = status,
        createdLabel = "Vandaag"
    )

    FocusCard(
        card = card,
        onToggleStatus = {
            status = if (status == CardStatus.OPEN) {
                CardStatus.COMPLETED
            } else {
                CardStatus.OPEN
            }
        }
    )
}

@Composable
private fun FocusCard(
    card: Card,
    onToggleStatus: () -> Unit
) {
    val isCompleted = card.status == CardStatus.COMPLETED
    val statusText = if (isCompleted) "Voltooid" else "Open"
    val buttonText = if (isCompleted) "Opnieuw openen" else "Voltooien"
    val cardBackground = if (isCompleted) Color(0xFF1E241C) else Color(0xFF211E18)
    val accentColor = Color(0xFFC8A75D)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .background(cardBackground)
            .border(
                width = 1.dp,
                color = accentColor.copy(alpha = if (isCompleted) 0.45f else 0.22f),
                shape = RoundedCornerShape(28.dp)
            )
            .padding(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column {
                Text(
                    text = card.category.label(),
                    color = accentColor,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = card.title,
                    color = CcText,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Light
                )
            }

            Text(
                text = statusText,
                color = if (isCompleted) accentColor else CcMuted,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = card.description,
            color = CcMuted,
            fontSize = 16.sp,
            lineHeight = 24.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CardMetaChip(text = "Prioriteit: ${card.priority.label()}")
            CardMetaChip(text = "Categorie: ${card.category.label()}")
            CardMetaChip(text = card.createdLabel)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onToggleStatus,
            colors = ButtonDefaults.buttonColors(
                containerColor = accentColor,
                contentColor = Color(0xFF15130F)
            )
        ) {
            Text(text = buttonText)
        }
    }
}

@Composable
private fun CardMetaChip(text: String) {
    Text(
        text = text,
        color = CcMuted,
        fontSize = 13.sp,
        modifier = Modifier
            .clip(RoundedCornerShape(999.dp))
            .background(Color(0xFF181612))
            .padding(horizontal = 12.dp, vertical = 7.dp)
    )
}

private fun CardCategory.label(): String {
    return when (this) {
        CardCategory.TODAY -> "Vandaag"
        CardCategory.FOCUS -> "Focus"
        CardCategory.MY_TASKS -> "Mijn taken"
        CardCategory.WAITING -> "Reactie afwachten"
        CardCategory.OTHERS -> "Taken van anderen"
        CardCategory.IDEAS -> "Ideeën"
        CardCategory.ARCHIVE -> "Archief"
    }
}

private fun CardPriority.label(): String {
    return when (this) {
        CardPriority.LOW -> "Laag"
        CardPriority.NORMAL -> "Normaal"
        CardPriority.HIGH -> "Hoog"
    }
}