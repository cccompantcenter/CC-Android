package com.cc.commandcenter.data

import com.cc.commandcenter.model.Card
import com.cc.commandcenter.model.CardCategory
import com.cc.commandcenter.model.CardPriority
import com.cc.commandcenter.model.CardStatus

object CardRepository {

    private val cards = listOf(

        Card(
            id = 1L,
            title = "Werkoverleg voorbereiden",
            description = "Controleer agenda en bespreekpunten.",
            category = CardCategory.FOCUS,
            priority = CardPriority.HIGH,
            status = CardStatus.OPEN,
            createdLabel = "Vandaag",
            tags = listOf("Werk", "Overleg"),
            favorite = true
        ),

        Card(
            id = 2L,
            title = "Linda bellen",
            description = "Terugkoppeling geven over de planning.",
            category = CardCategory.MY_TASKS,
            priority = CardPriority.NORMAL,
            status = CardStatus.OPEN,
            createdLabel = "Vandaag",
            tags = listOf("Telefoon")
        ),

        Card(
            id = 3L,
            title = "Reactie HR",
            description = "Wachten op antwoord van HR.",
            category = CardCategory.WAITING,
            priority = CardPriority.NORMAL,
            status = CardStatus.OPEN,
            createdLabel = "Gisteren"
        ),

        Card(
            id = 4L,
            title = "Nieuw idee",
            description = "Cards automatisch laten ordenen met AI.",
            category = CardCategory.IDEAS,
            priority = CardPriority.LOW,
            status = CardStatus.OPEN,
            createdLabel = "Vandaag"
        ),

        Card(
            id = 5L,
            title = "Audit afgerond",
            description = "Alle actiepunten verwerkt.",
            category = CardCategory.ARCHIVE,
            priority = CardPriority.LOW,
            status = CardStatus.COMPLETED,
            createdLabel = "Vorige week"
        )
    )

    fun allCards(): List<Card> = cards

    fun todayCards(): List<Card> =
        cards.filter { it.status == CardStatus.OPEN }

    fun focusCards(): List<Card> =
        cards.filter { it.category == CardCategory.FOCUS }

    fun cardsByCategory(category: CardCategory): List<Card> =
        cards.filter { it.category == category }
}