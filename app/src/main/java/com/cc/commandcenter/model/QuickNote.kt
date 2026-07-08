package com.cc.commandcenter.model

data class QuickNote(
    val id: Long,
    val text: String,
    val createdAt: String,
    val source: QuickNoteSource = QuickNoteSource.GEDACHTEN_INBOX,
    val updatedAt: String? = null,
    val inputType: GedachteInputType = GedachteInputType.UNKNOWN,
    val processingStatus: GedachteProcessingStatus = GedachteProcessingStatus.NEW,
    val sourceContext: GedachteSource = GedachteSource.UNKNOWN,
    val originalInput: String? = null,
    val aiInterpretation: String? = null,
    val aiConfidence: Double? = null
)

enum class QuickNoteSource {
    GEDACHTEN_INBOX,
    NOG_ORGANISEREN
}

enum class GedachteInputType {
    SPEN,
    KEYBOARD,
    VOICE,
    AI,
    UNKNOWN
}

enum class GedachteProcessingStatus {
    NEW,
    PROCESSING,
    CONVERTED,
    ARCHIVED,
    DELETED
}

enum class GedachteSource {
    START_SCREEN,
    DASHBOARD,
    CARD,
    PROJECT,
    CALENDAR,
    UNKNOWN
}