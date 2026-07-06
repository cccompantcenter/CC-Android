package com.cc.commandcenter.data

import androidx.compose.runtime.mutableStateListOf
import com.cc.commandcenter.model.QuickNote
import java.time.LocalTime

object QuickNoteRepository {

    val notes = mutableStateListOf<QuickNote>()

    fun add(text: String) {
        if (text.isBlank()) return

        notes.add(
            0,
            QuickNote(
                id = System.currentTimeMillis(),
                text = text.trim(),
                createdAt = LocalTime.now().withSecond(0).withNano(0).toString()
            )
        )
    }
}