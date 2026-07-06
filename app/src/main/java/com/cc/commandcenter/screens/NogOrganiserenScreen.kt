package com.cc.commandcenter.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cc.commandcenter.data.QuickNoteRepository
import com.cc.commandcenter.ui.theme.CcMidnight

@Composable
fun NogOrganiserenScreen() {

    val notes = QuickNoteRepository.notes

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CcMidnight)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text("Nog organiseren")

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            items(notes) { note ->

                Column {

                    Text(note.createdAt)

                    Text(note.text)

                }

            }

        }

    }

}