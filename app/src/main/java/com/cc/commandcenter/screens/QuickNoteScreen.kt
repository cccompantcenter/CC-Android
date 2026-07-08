package com.cc.commandcenter.screens
import com.cc.commandcenter.data.QuickNoteRepository
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cc.commandcenter.components.CcActionBar
import com.cc.commandcenter.ui.theme.CcMidnight

/**
 * Directe notitie
 *
 * Kernprincipe:
 * CC is ontworpen als S Pen-first schrijfervaring.
 *
 * De gebruiker moet een gedachte kunnen opschrijven alsof hij op papier schrijft.
 * Handschrift moet later feilloos herkend kunnen worden en daarna als tekst
 * in Nog organiseren verschijnen.
 *
 * Nu:
 * - toetsenbordinput
 *
 * Later:
 * - S Pen handschrift
 * - handschriftherkenning
 * - omzetting naar QuickNote
 * - AI doet voorstellen
 * - gebruiker beslist
 */

@Composable
fun QuickNoteScreen(
    initialNote: String = "",
    onBack: () -> Unit,
    onSave: (String) -> Unit = { note -> QuickNoteRepository.add(note) },
    onClear: () -> Unit = {}
) {
    var note by remember(initialNote) { mutableStateOf(initialNote) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CcMidnight)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        Text("Directe notitie")

        OutlinedTextField(
            value = note,
            onValueChange = { note = it },
            modifier = Modifier.fillMaxSize(),
            placeholder = {
                Text("Schrijf direct je gedachte op…")
            }
        )

        CcActionBar(
            onBack = {
                onBack()
            },
            onDelete = {
                note = ""
                onClear()
            },
            onSave = {
                onSave(note)
                onBack()
            }
        )
    }
}