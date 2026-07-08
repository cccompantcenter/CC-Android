package com.cc.commandcenter.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cc.commandcenter.data.QuickNoteRepository
import com.cc.commandcenter.model.GedachteProcessingStatus
import com.cc.commandcenter.ui.theme.CcMuted
import com.cc.commandcenter.ui.theme.CcText

@Composable
fun GedachtenScreen() {
    val thoughts = QuickNoteRepository.inboxNotes()
        .sortedByDescending { it.id }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(thoughts) { thought ->
                GedachteCard(thought = thought)
            }
        }
    }
}

@Composable
private fun GedachteCard(thought: com.cc.commandcenter.model.QuickNote) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0xFF211E18))
            .border(
                width = 1.dp,
                color = Color(0xFFC8A75D).copy(alpha = 0.22f),
                shape = RoundedCornerShape(24.dp)
            )
            .padding(20.dp)
    ) {
        Text(
            text = thought.text,
            color = CcText,
            fontSize = 18.sp,
            fontWeight = FontWeight.Light
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = thought.createdAt,
            color = CcMuted,
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = thought.processingStatus.displayName(),
            color = Color(0xFFC8A75D),
            fontSize = 13.sp
        )
    }
}

private fun GedachteProcessingStatus.displayName(): String = when (this) {
    GedachteProcessingStatus.NEW -> "Nieuw"
    GedachteProcessingStatus.PROCESSING -> "Bezig"
    GedachteProcessingStatus.CONVERTED -> "Omgezet"
    GedachteProcessingStatus.ARCHIVED -> "Gearchiveerd"
    GedachteProcessingStatus.DELETED -> "Verwijderd"
}
