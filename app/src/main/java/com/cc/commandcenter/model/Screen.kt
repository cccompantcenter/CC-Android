package com.cc.commandcenter.model
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.PeopleAlt
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Today
import androidx.compose.ui.graphics.vector.ImageVector

enum class Screen(val title: String, val icon: ImageVector) {
    TODAY("Vandaag", Icons.Outlined.Today),
    NOG_ORGANISEREN("Nog organiseren", Icons.Outlined.EditNote),
    FOCUS("Focus", Icons.Outlined.Flag),
    MY_TASKS("Mijn taken", Icons.Outlined.CheckCircle),
    WAITING("Reactie afwachten", Icons.Outlined.Schedule),
    OTHERS("Taken van anderen", Icons.Outlined.PeopleAlt),
    IDEAS("Ideeën", Icons.Outlined.Lightbulb),
    ARCHIVE("Archief", Icons.Outlined.Archive)
}