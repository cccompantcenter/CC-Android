package com.cc.commandcenter.data

import androidx.compose.runtime.mutableStateListOf
import com.cc.commandcenter.model.QuickNote
import com.cc.commandcenter.model.QuickNoteSource
import java.time.LocalTime

object QuickNoteRepository {

    val notes = mutableStateListOf<QuickNote>()

    fun add(
        text: String,
        source: QuickNoteSource = QuickNoteSource.GEDACHTEN_INBOX
    ) {
        if (text.isBlank()) return

        notes.add(
            0,
            QuickNote(
                id = System.currentTimeMillis(),
                text = text.trim(),
                createdAt = LocalTime.now().withSecond(0).withNano(0).toString(),
                source = source
            )
        )
    }

    fun inboxNotes(): List<QuickNote> = notes.filter { it.source == QuickNoteSource.GEDACHTEN_INBOX }

    fun organizationNotes(): List<QuickNote> = notes.filter { it.source == QuickNoteSource.NOG_ORGANISEREN }
}