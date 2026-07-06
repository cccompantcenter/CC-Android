package com.cc.commandcenter.data

import com.cc.commandcenter.model.Card
import com.cc.commandcenter.model.CardCategory
import com.cc.commandcenter.model.CardPriority
import com.cc.commandcenter.model.CardStatus

object CardRepository {

    private val cards = listOf(

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

    fun allCards(): List<Card> = cards

    fun focusCards(): List<Card> =
        cards.filter { it.category == CardCategory.FOCUS }

    fun cardsByCategory(category: CardCategory): List<Card> =
        cards.filter { it.category == category }
}