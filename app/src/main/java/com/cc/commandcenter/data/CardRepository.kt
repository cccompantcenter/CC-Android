package com.cc.commandcenter.data

import com.cc.commandcenter.model.Card
import com.cc.commandcenter.model.CardCategory
import com.cc.commandcenter.model.CardPriority
import com.cc.commandcenter.model.CardStatus

object CardRepository {

    fun focusCards(): List<Card> {
        return listOf(
            Card(
                id = 1L,
                title = "Eerste echte CC Card",
                description = "Deze Card komt nu uit de CardRepository. Daarmee is Focus niet meer hardcoded op het scherm.",
                category = CardCategory.FOCUS,
                priority = CardPriority.HIGH,
                status = CardStatus.OPEN,
                createdLabel = "Vandaag"
            )
        )
    }
}