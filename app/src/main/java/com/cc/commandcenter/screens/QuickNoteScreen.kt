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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.cc.commandcenter.components.CcActionBar
import com.cc.commandcenter.data.QuickNoteRepository
import com.cc.commandcenter.ink.DefaultCCInkCanvas
import com.cc.commandcenter.ui.theme.CcMidnight
import com.cc.commandcenter.ui.theme.CcMuted
import com.cc.commandcenter.ui.theme.CcText

@Composable
fun QuickNoteScreen(
    initialNote: String = "",
    onBack: () -> Unit,
    onSave: (String) -> Unit = { note -> QuickNoteRepository.add(note) },
    onClear: () -> Unit = {}
) {
    var note by remember(initialNote) { mutableStateOf(initialNote) }
    val inkCanvas = remember { DefaultCCInkCanvas() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CcMidnight)
            .padding(horizontal = 24.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.widthIn(max = 960.dp)
        ) {
            Text(
                text = "Nieuwe gedachte",
                color = CcText,
                fontSize = 34.sp,
                fontWeight = FontWeight.Light
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Schrijf je gedachte op alsof je het op papier zet.",
                color = CcMuted,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(28.dp))
                    .background(Color(0xFF1A180F))
                    .border(
                        width = 1.dp,
                        color = Color(0xFFC8A75D).copy(alpha = 0.22f),
                        shape = RoundedCornerShape(28.dp)
                    )
                    .padding(24.dp)
            ) {
                if (note.isBlank()) {
                    Text(
                        text = "Plaats je handschrift of typ een eerste notitie…",
                        color = CcMuted,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Light
                    )
                } else {
                    Text(
                        text = note,
                        color = CcText,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Light
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            CcActionBar(
                onBack = onBack,
                onDelete = {
                    note = ""
                    onClear()
                },
                onSave = {
                    onSave(note)
                    onBack()
                }
            )
        }
    }
}