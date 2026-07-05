package com.cc.commandcenter.model

data class Card(
    val id: Long,
    val title: String,
    val description: String,
    val category: CardCategory,
    val priority: CardPriority,
    val status: CardStatus,
    val createdLabel: String
)

enum class CardCategory {
    TODAY,
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