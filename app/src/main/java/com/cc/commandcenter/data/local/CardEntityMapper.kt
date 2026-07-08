package com.cc.commandcenter.data.local

import com.cc.commandcenter.model.Card
import com.cc.commandcenter.model.CardCategory
import com.cc.commandcenter.model.CardDestination
import com.cc.commandcenter.model.CardPriority
import com.cc.commandcenter.model.CardStatus
import com.cc.commandcenter.model.CardType

private const val LIST_DELIMITER = ";;"

fun CardEntity.toDomain(): Card {
    val categoryValue = category.toEnumOrDefault(CardCategory.MY_TASKS)

    return Card(
        id = id,
        title = title,
        description = description,
        category = categoryValue,
        priority = priority.toEnumOrDefault(CardPriority.NORMAL),
        status = status.toEnumOrDefault(CardStatus.OPEN),
        createdLabel = createdLabel,
        notes = notes,
        dueDate = dueDate,
        tags = tags.decodeStringList(),
        favorite = favorite,
        originalGedachteId = originalGedachteId,
        originalGedachtePreview = originalGedachtePreview,
        sourceGedachteId = sourceGedachteId,
        type = type.toEnumOrDefault(defaultTypeFor(categoryValue)),
        destination = destination.toEnumOrDefault(defaultDestinationFor(categoryValue)),
        createdAt = createdAt,
        updatedAt = updatedAt,
        linkedCardIds = linkedCardIds.decodeLongList(),
        projectId = projectId,
        calendarItemId = calendarItemId,
        contactId = contactId
    )
}

fun Card.toEntity(): CardEntity = CardEntity(
    id = id,
    title = title,
    description = description,
    category = category.name,
    priority = priority.name,
    status = status.name,
    createdLabel = createdLabel,
    notes = notes,
    dueDate = dueDate,
    tags = tags.encodeStringList(),
    favorite = favorite,
    originalGedachteId = originalGedachteId,
    originalGedachtePreview = originalGedachtePreview,
    sourceGedachteId = sourceGedachteId,
    type = type.name,
    destination = destination.name,
    createdAt = createdAt,
    updatedAt = updatedAt,
    linkedCardIds = linkedCardIds.encodeLongList(),
    projectId = projectId,
    calendarItemId = calendarItemId,
    contactId = contactId
)

private inline fun <reified T : Enum<T>> String.toEnumOrDefault(defaultValue: T): T =
    enumValues<T>().firstOrNull { it.name == this } ?: defaultValue

private fun List<String>.encodeStringList(): String =
    joinToString(LIST_DELIMITER)

private fun String.decodeStringList(): List<String> =
    split(LIST_DELIMITER).map { it.trim() }.filter { it.isNotEmpty() }

private fun List<Long>.encodeLongList(): String =
    joinToString(LIST_DELIMITER)

private fun String.decodeLongList(): List<Long> =
    split(LIST_DELIMITER)
        .mapNotNull { it.trim().toLongOrNull() }

private fun defaultTypeFor(category: CardCategory): CardType = when (category) {
    CardCategory.FOCUS,
    CardCategory.MY_TASKS,
    CardCategory.OTHERS -> CardType.TASK
    CardCategory.WAITING -> CardType.WAITING_FOR
    CardCategory.IDEAS -> CardType.IDEA
    CardCategory.ARCHIVE -> CardType.ARCHIVE
}

private fun defaultDestinationFor(category: CardCategory): CardDestination = when (category) {
    CardCategory.FOCUS -> CardDestination.FOCUS
    CardCategory.MY_TASKS -> CardDestination.MY_TASKS
    CardCategory.WAITING -> CardDestination.WAITING
    CardCategory.OTHERS -> CardDestination.OTHERS
    CardCategory.IDEAS -> CardDestination.IDEAS
    CardCategory.ARCHIVE -> CardDestination.ARCHIVE
}
