package com.cc.commandcenter.model

data class QuickNote(
    val id: Long,
    val text: String,
    val createdAt: String,
    val source: QuickNoteSource = QuickNoteSource.GEDACHTEN_INBOX
)

enum class QuickNoteSource {
    GEDACHTEN_INBOX,
    NOG_ORGANISEREN
}