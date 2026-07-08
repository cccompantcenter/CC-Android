package com.cc.commandcenter.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cards")
data class CardEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val description: String,
    val category: String,
    val priority: String,
    val status: String,
    val createdLabel: String,
    val notes: String,
    val dueDate: String?,
    val tags: String,
    val favorite: Boolean,
    val originalGedachteId: Long?,
    val originalGedachtePreview: String?,
    val sourceGedachteId: Long?,
    val type: String,
    val destination: String,
    val createdAt: String,
    val updatedAt: String,
    val linkedCardIds: String,
    val projectId: Long?,
    val calendarItemId: Long?,
    val contactId: Long?
)
