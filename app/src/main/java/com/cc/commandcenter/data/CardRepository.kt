package com.cc.commandcenter.data

import com.cc.commandcenter.model.Card
import com.cc.commandcenter.model.CardCategory
import com.cc.commandcenter.model.CardDestination
import com.cc.commandcenter.model.CardPriority
import com.cc.commandcenter.model.CardStatus

object CardRepository {

    // In-memory repository boundary for future persistence replacement.
    private val cards = mutableListOf(

        // ---------- VERLOPEN ----------

        Card(
            id = 1L,
            title = "Werkoverleg voorbereiden",
            description = "Controleer agenda, bespreekpunten en open acties.",
            category = CardCategory.FOCUS,
            priority = CardPriority.HIGH,
            status = CardStatus.OPEN,
            createdLabel = "Vorige week",
            dueDate = "2026-07-04",
            tags = listOf("Werk", "Overleg"),
            favorite = true
        ),

        Card(
            id = 2L,
            title = "Reactie HR opvolgen",
            description = "Nog geen reactie ontvangen op de personeelsvraag.",
            category = CardCategory.WAITING,
            priority = CardPriority.NORMAL,
            status = CardStatus.OPEN,
            createdLabel = "3 dagen geleden",
            dueDate = "2026-07-05"
        ),

        // ---------- VANDAAG ----------

        Card(
            id = 3L,
            title = "Linda bellen",
            description = "Terugkoppeling geven over de planning.",
            category = CardCategory.MY_TASKS,
            priority = CardPriority.NORMAL,
            status = CardStatus.OPEN,
            createdLabel = "Vandaag",
            dueDate = "2026-07-06",
            tags = listOf("Telefoon")
        ),

        Card(
            id = 4L,
            title = "Werkrooster controleren",
            description = "Controleer de open diensten voor volgende week.",
            category = CardCategory.MY_TASKS,
            priority = CardPriority.HIGH,
            status = CardStatus.OPEN,
            createdLabel = "Vandaag",
            dueDate = "2026-07-06"
        ),

        // ---------- LATER ----------

        Card(
            id = 5L,
            title = "Functioneringsgesprek Wendy",
            description = "Voorbereiden en stukken verzamelen.",
            category = CardCategory.MY_TASKS,
            priority = CardPriority.NORMAL,
            status = CardStatus.OPEN,
            createdLabel = "Vandaag",
            dueDate = "2026-07-10"
        ),

        // ---------- GEEN AANDACHTSDATUM ----------

        Card(
            id = 6L,
            title = "Nieuw idee",
            description = "Cards automatisch laten ordenen met AI.",
            category = CardCategory.IDEAS,
            priority = CardPriority.LOW,
            status = CardStatus.OPEN,
            createdLabel = "Vandaag",
            dueDate = null
        ),

        // ---------- ARCHIEF ----------

        Card(
            id = 7L,
            title = "Audit afgerond",
            description = "Alle actiepunten verwerkt.",
            category = CardCategory.ARCHIVE,
            priority = CardPriority.LOW,
            status = CardStatus.COMPLETED,
            createdLabel = "Vorige week",
            dueDate = "2026-07-01"
        )
    )

    fun getAllCards(): List<Card> = cards.toList()

    fun allCards(): List<Card> = getAllCards()

    fun getCardById(cardId: Long): Card? =
        cards.firstOrNull { it.id == cardId }

    fun createCard(
        title: String,
        category: CardCategory,
        priority: CardPriority,
        dueDate: String,
        originalGedachteId: Long? = null,
        originalGedachtePreview: String? = null
    ): Card {
        val newCard = Card(
            id = (cards.maxOfOrNull { it.id } ?: 0L) + 1L,
            title = title,
            description = "",
            category = category,
            priority = priority,
            status = CardStatus.OPEN,
            createdLabel = "Vandaag",
            dueDate = dueDate,
            originalGedachteId = originalGedachteId,
            originalGedachtePreview = originalGedachtePreview
        )

        cards.add(0, newCard)
        return newCard
    }

    fun updateCard(updatedCard: Card): Card {
        val index = cards.indexOfFirst { it.id == updatedCard.id }
        if (index != -1) {
            cards[index] = updatedCard
        }
        return updatedCard
    }

    fun archiveCard(cardId: Long): Card? {
        val card = getCardById(cardId) ?: return null
        val archivedCard = card.copy(
            category = CardCategory.ARCHIVE,
            status = CardStatus.COMPLETED,
            destination = CardDestination.ARCHIVE
        )
        updateCard(archivedCard)
        return archivedCard
    }

    fun deleteCard(cardId: Long): Boolean =
        cards.removeAll { it.id == cardId }

    fun linkCards(cardId: Long, linkedCardId: Long): Card? {
        val card = getCardById(cardId) ?: return null
        if (cardId == linkedCardId) return card

        val linkedIds = (card.linkedCardIds + linkedCardId).distinct()
        val linkedCard = card.copy(linkedCardIds = linkedIds)
        updateCard(linkedCard)
        return linkedCard
    }

    fun cardsByDestination(destination: CardDestination): List<Card> =
        cards.filter { it.destination == destination }

    fun cardsByStatus(status: CardStatus): List<Card> =
        cards.filter { it.status == status }

    fun focusCards(): List<Card> =
        cards.filter { it.category == CardCategory.FOCUS }

    fun cardsByCategory(category: CardCategory): List<Card> =
        cards.filter { it.category == category }
}