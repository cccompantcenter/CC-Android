package com.cc.commandcenter.screens

import androidx.compose.runtime.Composable
import com.cc.commandcenter.components.UniversalCardEditor
import com.cc.commandcenter.model.Card

@Composable
fun CardDetailScreen(
    card: Card,
    onBack: () -> Unit,
    onDelete: () -> Unit,
    onSave: (Card) -> Unit
) {
    UniversalCardEditor(
        cardId = card.id,
        onBack = onBack,
        onDelete = onDelete,
        onSave = onSave
    )
}