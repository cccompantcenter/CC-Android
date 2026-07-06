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

@Composable
fun QuickNoteScreen(
    onBack: () -> Unit
) {
    var note by remember { mutableStateOf("") }

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
            onBack = onBack,
            onDelete = {
                // niet nodig voor directe notitie
            },
            onSave = {
                QuickNoteRepository.add(note)
                onBack()
            }
        )
    }
}