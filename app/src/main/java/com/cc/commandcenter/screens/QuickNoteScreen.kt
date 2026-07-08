package com.cc.commandcenter.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cc.commandcenter.components.CcActionBar
import com.cc.commandcenter.data.QuickNoteRepository
import com.cc.commandcenter.ink.DefaultCCInkCanvas
import com.cc.commandcenter.ink.InkPoint
import com.cc.commandcenter.ink.InkStroke
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
    var currentStroke by remember { mutableStateOf<List<InkPoint>>(emptyList()) }

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

            Box(
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
                androidx.compose.foundation.Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragStart = { startOffset ->
                                    currentStroke = listOf(InkPoint(startOffset.x, startOffset.y))
                                },
                                onDrag = { change, _ ->
                                    currentStroke = currentStroke + InkPoint(change.position.x, change.position.y)
                                },
                                onDragEnd = {
                                    if (currentStroke.isNotEmpty()) {
                                        inkCanvas.captureStroke(InkStroke(id = System.currentTimeMillis(), points = currentStroke))
                                    }
                                    currentStroke = emptyList()
                                }
                            )
                        }
                ) {
                    drawInkStrokes(inkCanvas.strokes(), currentStroke)
                }

                if (note.isBlank() && inkCanvas.strokes().isEmpty() && currentStroke.isEmpty()) {
                    Text(
                        text = "Plaats je handschrift of typ een eerste notitie…",
                        color = CcMuted,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Light
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            CcActionBar(
                onBack = onBack,
                onDelete = {
                    note = ""
                    inkCanvas.clearStrokes()
                    currentStroke = emptyList()
                    onClear()
                },
                onSave = {
                    onSave(note.ifBlank { inkCanvas.convertToText() })
                    onBack()
                }
            )
        }
    }
}

private fun DrawScope.drawInkStrokes(strokes: List<InkStroke>, currentStroke: List<InkPoint>) {
    val inkColor = Color(0xFFEAD7A1)
    val strokeWidth = 2.2.dp.toPx()

    for (stroke in strokes) {
        if (stroke.points.size >= 2) {
            for (index in 0 until stroke.points.size - 1) {
                val start = stroke.points[index]
                val end = stroke.points[index + 1]
                drawLine(
                    color = inkColor,
                    start = Offset(start.x, start.y),
                    end = Offset(end.x, end.y),
                    strokeWidth = strokeWidth
                )
            }
        }
    }

    if (currentStroke.size >= 2) {
        for (index in 0 until currentStroke.size - 1) {
            val start = currentStroke[index]
            val end = currentStroke[index + 1]
            drawLine(
                color = inkColor,
                start = Offset(start.x, start.y),
                end = Offset(end.x, end.y),
                strokeWidth = strokeWidth
            )
        }
    }
}