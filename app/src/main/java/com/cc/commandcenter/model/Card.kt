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
    val originalGedachtePreview: String? = null
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