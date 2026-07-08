package com.cc.commandcenter.data

import android.content.Context
import androidx.room.Room
import com.cc.commandcenter.data.local.CardDao
import com.cc.commandcenter.data.local.CommandCenterDatabase
import com.cc.commandcenter.data.local.toDomain
import com.cc.commandcenter.data.local.toEntity
import com.cc.commandcenter.model.Card
import com.cc.commandcenter.model.CardCategory
import com.cc.commandcenter.model.CardDestination
import com.cc.commandcenter.model.CardPriority
import com.cc.commandcenter.model.CardStatus

object CardRepository {

    private const val DATABASE_NAME = "command_center.db"

    private var dao: CardDao? = null
    private val cards = mutableListOf<Card>()

    fun init(context: Context) {
        if (dao != null) return

        val database = Room.databaseBuilder(
            context.applicationContext,
            CommandCenterDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

        dao = database.cardDao()

        seedIfEmpty()
        refreshCacheFromStorage()
    }

    fun getAllCards(): List<Card> {
        refreshCacheFromStorage()
        return cards.toList()
    }

    fun allCards(): List<Card> = getAllCards()

    fun getCardById(cardId: Long): Card? {
        val card = dao?.getCardById(cardId)?.toDomain()
        if (card != null) {
            upsertCache(card)
            return card
        }
        return cards.firstOrNull { it.id == cardId }
    }

    fun getCardBySourceGedachteId(sourceGedachteId: Long): Card? {
        val card = dao?.getCardBySourceGedachteId(sourceGedachteId)?.toDomain()
        if (card != null) {
            upsertCache(card)
            return card
        }
        return cards.firstOrNull {
            it.sourceGedachteId == sourceGedachteId || it.originalGedachteId == sourceGedachteId
        }
    }

    fun createCard(
        title: String,
        category: CardCategory,
        priority: CardPriority,
        dueDate: String?,
        originalGedachteId: Long? = null,
        originalGedachtePreview: String? = null
    ): Card {
        val newCard = Card(
            id = nextCardId(),
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

        dao?.upsertCard(newCard.toEntity())
        upsertCache(newCard)
        return newCard
    }

    fun createCardFromGedachte(
        gedachteId: Long,
        gedachteTitle: String,
        gedachtePreview: String,
        dueDate: String? = null
    ): Card {
        val existingCard = getCardBySourceGedachteId(gedachteId)
        if (existingCard != null) {
            return existingCard
        }

        return createCard(
            title = gedachteTitle,
            category = CardCategory.IDEAS,
            priority = CardPriority.NORMAL,
            dueDate = dueDate,
            originalGedachteId = gedachteId,
            originalGedachtePreview = gedachtePreview
        )
    }

    fun updateCard(updatedCard: Card): Card {
        dao?.upsertCard(updatedCard.toEntity())
        upsertCache(updatedCard)
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

    fun deleteCard(cardId: Long): Boolean {
        val deletedInDb = (dao?.deleteCardById(cardId) ?: 0) > 0
        val deletedInCache = cards.removeAll { it.id == cardId }
        return deletedInDb || deletedInCache
    }

    fun linkCards(cardId: Long, linkedCardId: Long): Card? {
        val card = getCardById(cardId) ?: return null
        if (cardId == linkedCardId) return card

        val linkedIds = (card.linkedCardIds + linkedCardId).distinct()
        val linkedCard = card.copy(linkedCardIds = linkedIds)
        updateCard(linkedCard)
        return linkedCard
    }

    fun cardsByDestination(destination: CardDestination): List<Card> =
        getAllCards().filter {
            it.destination == destination ||
                it.category == destination.toRepositoryCategoryOrNull()
        }

    fun cardsByStatus(status: CardStatus): List<Card> =
        getAllCards().filter { it.status == status }

    fun focusCards(): List<Card> =
        getAllCards().filter { it.category == CardCategory.FOCUS }

    fun cardsByCategory(category: CardCategory): List<Card> =
        getAllCards().filter { it.category == category }

    private fun nextCardId(): Long {
        val dbMax = dao?.maxCardId() ?: 0L
        val cacheMax = cards.maxOfOrNull { it.id } ?: 0L
        return maxOf(dbMax, cacheMax) + 1L
    }

    private fun refreshCacheFromStorage() {
        val currentDao = dao ?: return
        cards.clear()
        cards.addAll(currentDao.getAllCards().map { it.toDomain() })
    }

    private fun upsertCache(card: Card) {
        val index = cards.indexOfFirst { it.id == card.id }
        if (index == -1) {
            cards.add(0, card)
        } else {
            cards[index] = card
        }
    }

    private fun seedIfEmpty() {
        val currentDao = dao ?: return
        if (currentDao.countCards() > 0) return

        currentDao.upsertCards(sampleCards().map { it.toEntity() })
    }

    private fun sampleCards(): List<Card> = listOf(
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
}

private fun CardDestination.toRepositoryCategoryOrNull(): CardCategory? = when (this) {
    CardDestination.FOCUS,
    CardDestination.TODAY -> CardCategory.FOCUS
    CardDestination.MY_TASKS,
    CardDestination.PROJECT,
    CardDestination.CALENDAR -> CardCategory.MY_TASKS
    CardDestination.WAITING -> CardCategory.WAITING
    CardDestination.OTHERS,
    CardDestination.CONTACT -> CardCategory.OTHERS
    CardDestination.IDEAS,
    CardDestination.INBOX -> CardCategory.IDEAS
    CardDestination.ARCHIVE -> CardCategory.ARCHIVE
}