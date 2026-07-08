package com.cc.commandcenter.data

import androidx.compose.runtime.mutableStateListOf
import com.cc.commandcenter.ink.InkStroke
import com.cc.commandcenter.model.QuickNote
import com.cc.commandcenter.model.QuickNoteSource
import java.time.LocalTime

object QuickNoteRepository {

    val notes = mutableStateListOf<QuickNote>()

    fun add(
        text: String,
        source: QuickNoteSource = QuickNoteSource.GEDACHTEN_INBOX,
        inkStrokes: List<InkStroke> = emptyList()
    ) {
        if (text.isBlank() && inkStrokes.isEmpty()) return

        notes.add(
            0,
            QuickNote(
                id = System.currentTimeMillis(),
                text = text.trim(),
                createdAt = LocalTime.now().withSecond(0).withNano(0).toString(),
                updatedAt = LocalTime.now().withSecond(0).withNano(0).toString(),
                source = source,
                inkStrokes = inkStrokes
            )
        )
    }

    fun update(
        id: Long,
        text: String,
        source: QuickNoteSource = QuickNoteSource.GEDACHTEN_INBOX,
        inkStrokes: List<InkStroke> = emptyList()
    ) {
        if (text.isBlank() && inkStrokes.isEmpty()) {
            delete(id)
            return
        }

        val index = notes.indexOfFirst { it.id == id }
        if (index == -1) return

        val current = notes[index]
        notes[index] = current.copy(
            text = text.trim(),
            updatedAt = LocalTime.now().withSecond(0).withNano(0).toString(),
            source = source,
            inkStrokes = inkStrokes
        )
    }

    fun delete(id: Long) {
        notes.removeAll { it.id == id }
    }

    fun inboxNotes(): List<QuickNote> = notes.filter { it.source == QuickNoteSource.GEDACHTEN_INBOX }

    fun organizationNotes(): List<QuickNote> = notes.filter { it.source == QuickNoteSource.NOG_ORGANISEREN }
}