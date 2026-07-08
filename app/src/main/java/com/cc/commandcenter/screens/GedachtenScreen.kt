package com.cc.commandcenter.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cc.commandcenter.components.CcLogo
import com.cc.commandcenter.data.QuickNoteRepository
import com.cc.commandcenter.model.GedachteProcessingStatus
import com.cc.commandcenter.ui.theme.CcMuted
import com.cc.commandcenter.ui.theme.CcText

@Composable
fun GedachtenScreen(
    onOpenGedachte: (com.cc.commandcenter.model.QuickNote) -> Unit = {}
) {
    val thoughts = QuickNoteRepository.inboxNotes()
        .sortedByDescending { it.id }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (thoughts.isEmpty()) {
            EmptyGedachtenState()
        } else {
            LazyColumn(
                modifier = Modifier.widthIn(max = 980.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(thoughts) { thought ->
                    GedachteCard(
                        thought = thought,
                        onClick = { onOpenGedachte(thought) }
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyGedachtenState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CcLogo(size = 72.dp)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Nog geen gedachten",
            color = CcText,
            fontSize = 24.sp,
            fontWeight = FontWeight.Light
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Je eerste gedachte verschijnt hier zodra je hem vastlegt.",
            color = CcMuted,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun GedachteCard(
    thought: com.cc.commandcenter.model.QuickNote,
    onClick: () -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .pointerInput(Unit) {
                detectTapGestures(onTap = { onClick() }, onLongPress = { menuExpanded = true })
            }
            .background(Color(0xFF211E18))
            .border(
                width = 1.dp,
                color = Color(0xFFC8A75D).copy(alpha = 0.22f),
                shape = RoundedCornerShape(24.dp)
            )
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            CcLogo(modifier = Modifier.size(28.dp))

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = thought.displayTitle(),
                color = CcText,
                fontSize = 18.sp,
                fontWeight = FontWeight.Light
            )
        }

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

        DropdownMenu(
            expanded = menuExpanded,
            onDismissRequest = { menuExpanded = false },
            modifier = Modifier.widthIn(min = 180.dp),
            shape = RoundedCornerShape(20.dp),
            containerColor = Color(0xFF231F17),
            tonalElevation = 8.dp
        ) {
            DropdownMenuItem(text = { Text("Open", color = CcText) }, onClick = {
                menuExpanded = false
                onClick()
            })

            DropdownMenuItem(text = { Text("Verplaatsen", color = CcMuted) }, onClick = {
                menuExpanded = false
                // placeholder
            })

            DropdownMenuItem(text = { Text("Archiveer", color = Color(0xFFE8C067)) }, onClick = {
                menuExpanded = false
                QuickNoteRepository.archive(thought.id)
            })
        }
    }
}

private fun com.cc.commandcenter.model.QuickNote.displayTitle(): String {
    return text.lines()
        .map { it.trim() }
        .firstOrNull { it.isNotEmpty() }
        ?: "Nieuwe gedachte"
}

private fun GedachteProcessingStatus.displayName(): String = when (this) {
    GedachteProcessingStatus.NEW -> "Nieuw"
    GedachteProcessingStatus.PROCESSING -> "Bezig"
    GedachteProcessingStatus.CONVERTED -> "Omgezet"
    GedachteProcessingStatus.ARCHIVED -> "Gearchiveerd"
    GedachteProcessingStatus.DELETED -> "Verwijderd"
}
