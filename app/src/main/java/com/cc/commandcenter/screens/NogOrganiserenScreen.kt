package com.cc.commandcenter.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cc.commandcenter.ui.theme.CcMidnight

/**
 * Nog Organiseren
 *
 * Workflow Command Center
 *
 * Gedachte
 *      ↓
 * Directe notitie
 *      ↓
 * Nog organiseren
 *      ↓
 * AI doet voorstellen (later)
 *      ↓
 * Gebruiker beslist
 *      ↓
 * Card
 *      ↓
 * Vandaag / Focus / Wachten / Archief
 *
 * Ontwerpprincipes
 * - Eerst schrijven, daarna organiseren.
 * - AI ondersteunt, beslist nooit.
 * - Schrijven moet altijd sneller zijn dan organiseren.
 * - Ontworpen voor toetsenbord, S Pen, spraak en toekomstige invoervormen.
 */
@Composable
fun NogOrganiserenScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CcMidnight)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text("Nog organiseren")

        Text(
            "Hier verschijnen alle directe notities totdat je besluit wat ermee gebeurt."
        )

        Text(
            "Binnenkort kun je hier notities bekijken, bewerken, samenvoegen of omzetten naar een Card."
        )
    }
}