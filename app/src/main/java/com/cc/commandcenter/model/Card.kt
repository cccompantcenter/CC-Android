package com.cc.commandcenter.model

data class Card(
    val id: Long,
    val title: String,
    val description: String,
    val category: CardCategory,
    val priority: CardPriority,
    val status: CardStatus,
    val createdLabel: String,

    val notes: String = "",
    val dueDate: String? = null,
    val tags: List<String> = emptyList(),
    val favorite: Boolean = false,
    val originalGedachteId: Long? = null,
    val originalGedachtePreview: String? = null,

    // CC-040 Universal Card foundation fields.
    val sourceGedachteId: Long? = originalGedachteId,
    val type: CardType = category.toCardType(),
    val destination: CardDestination = category.toCardDestination(),
    val createdAt: String = createdLabel,
    val updatedAt: String = createdLabel,
    val linkedCardIds: List<Long> = emptyList(),
    val projectId: Long? = null,
    val calendarItemId: Long? = null,
    val contactId: Long? = null
)

enum class CardCategory {
    FOCUS,
    MY_TASKS,
    WAITING,
    OTHERS,
    IDEAS,
    ARCHIVE
}

enum class CardPriority {
    LOW,
    NORMAL,
    HIGH
}

enum class CardStatus {
    OPEN,
    COMPLETED
}

enum class CardType {
    TASK,
    PROJECT,
    NOTE,
    MEETING,
    CONTACT,
    IDEA,
    WAITING_FOR,
    ARCHIVE
}

enum class CardDestination {
    FOCUS,
    MY_TASKS,
    WAITING,
    OTHERS,
    IDEAS,
    ARCHIVE,

    // Legacy values kept for backward compatibility during the transition.
    TODAY,
    PROJECT,
    CALENDAR,
    CONTACT,
    INBOX
}

private fun CardCategory.toCardType(): CardType = when (this) {
    CardCategory.FOCUS,
    CardCategory.MY_TASKS,
    CardCategory.OTHERS -> CardType.TASK
    CardCategory.WAITING -> CardType.WAITING_FOR
    CardCategory.IDEAS -> CardType.IDEA
    CardCategory.ARCHIVE -> CardType.ARCHIVE
}

private fun CardCategory.toCardDestination(): CardDestination = when (this) {
    CardCategory.FOCUS -> CardDestination.FOCUS
    CardCategory.MY_TASKS -> CardDestination.MY_TASKS
    CardCategory.WAITING -> CardDestination.WAITING
    CardCategory.OTHERS -> CardDestination.OTHERS
    CardCategory.IDEAS -> CardDestination.IDEAS
    CardCategory.ARCHIVE -> CardDestination.ARCHIVE
}