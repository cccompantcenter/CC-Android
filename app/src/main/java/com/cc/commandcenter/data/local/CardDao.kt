package com.cc.commandcenter.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CardDao {
    @Query("SELECT * FROM cards ORDER BY id ASC")
    fun getAllCards(): List<CardEntity>

    @Query("SELECT * FROM cards WHERE id = :cardId LIMIT 1")
    fun getCardById(cardId: Long): CardEntity?

    @Query("SELECT * FROM cards WHERE sourceGedachteId = :sourceGedachteId OR originalGedachteId = :sourceGedachteId LIMIT 1")
    fun getCardBySourceGedachteId(sourceGedachteId: Long): CardEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertCard(card: CardEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertCards(cards: List<CardEntity>)

    @Query("DELETE FROM cards WHERE id = :cardId")
    fun deleteCardById(cardId: Long): Int

    @Query("SELECT COUNT(*) FROM cards")
    fun countCards(): Int

    @Query("SELECT MAX(id) FROM cards")
    fun maxCardId(): Long?
}
