package com.cc.commandcenter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material.icons.outlined.PeopleAlt
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material.icons.outlined.Today
import androidx.compose.material.icons.outlined.WorkOutline
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Timeline
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.FolderOpen
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { CCApp() }
    }
}

private object CCColor {
    val Midnight = Color(0xFF050506)
    val Surface = Color(0xFF101112)
    val SurfaceSoft = Color(0xFF171717)
    val Border = Color(0xFF2D2A25)
    val Gold = Color(0xFFE5AF55)
    val GoldSoft = Color(0xFFB98A3E)
    val Text = Color(0xFFF1E9DD)
    val Muted = Color(0xFFA7A09A)
    val Red = Color(0xFFFF5148)
    val Yellow = Color(0xFFFFC83D)
    val Orange = Color(0xFFFF8A22)
    val Purple = Color(0xFFB864FF)
    val Green = Color(0xFF68D879)
    val Blue = Color(0xFF63B8FF)
    val Teal = Color(0xFF42D2C8)
    val Paper = Color(0xFFF4ECDE)
    val Ink = Color(0xFF231B14)
}

enum class Screen { Focus, Waiting, Delegated, Ideas, Archive, Settings, Write, MasterCard, Proposal }

data class CCard(
    val title: String,
    val person: String,
    val location: String,
    val category: String,
    val color: Color,
    val dateText: String,
    val priority: String = "Normaal"
)

@Composable
fun CCApp() {
    var screen by remember { mutableStateOf(Screen.Focus) }
    val selectedCard = remember {
        CCard(
            title = "Teamoverleg voorbereiden",
            person = "Wendy Jansen",
            location = "Vergaderruimte 2B",
            category = "Focus",
            color = CCColor.Red,
            dateText = "Vandaag 09:30",
            priority = "Hoog"
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(Color(0xFF1A1510), CCColor.Midnight),
                    radius = 1400f
                )
            )
    ) {
        when (screen) {
            Screen.Write -> WriteScreen(onBack = { screen = Screen.Focus }, onOrganize = { screen = Screen.Proposal })
            Screen.Proposal -> ProposalScreen(onBack = { screen = Screen.Write }, onAccept = { screen = Screen.MasterCard })
            Screen.MasterCard -> AppShell(screen, onNavigate = { screen = it }) { MasterCardPanel(selectedCard) }
            else -> AppShell(screen, onNavigate = { screen = it }) { contentScreen ->
                when (contentScreen) {
                    Screen.Focus -> FocusScreen(onWrite = { screen = Screen.Write }, onOpenCard = { screen = Screen.MasterCard })
                    Screen.Waiting -> CategoryScreen("Reactie afwachten", "Taken waarbij jij wacht op reactie", CCColor.Yellow, onWrite = { screen = Screen.Write })
                    Screen.Delegated -> CategoryScreen("Taak van anderen", "Taken die jij volgt", CCColor.Orange, onWrite = { screen = Screen.Write })
                    Screen.Ideas -> CategoryScreen("Ideeën", "Gedachten die nog mogen rijpen", CCColor.Purple, onWrite = { screen = Screen.Write })
                    Screen.Archive -> CategoryScreen("Archief", "Afgeronde kaarten", CCColor.Green, onWrite = { screen = Screen.Write })
                    Screen.Settings -> SettingsScreen()
                    else -> FocusScreen(onWrite = { screen = Screen.Write }, onOpenCard = { screen = Screen.MasterCard })
                }
            }
        }
    }
}

@Composable
fun AppShell(current: Screen, onNavigate: (Screen) -> Unit, content: @Composable (Screen) -> Unit) {
    Row(Modifier.fillMaxSize().padding(10.dp)) {
        Sidebar(current, onNavigate)
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .clip(RoundedCornerShape(22.dp))
                .border(1.dp, Color.White.copy(alpha = 0.10f), RoundedCornerShape(22.dp))
                .background(Color.Black.copy(alpha = 0.18f))
        ) {
            content(current)
        }
    }
}

@Composable
fun Sidebar(current: Screen, onNavigate: (Screen) -> Unit) {
    val items = listOf(
        Triple(Screen.Focus, "Focus", Icons.Outlined.Home),
        Triple(Screen.Waiting, "Reactie afwachten", Icons.Outlined.MailOutline),
        Triple(Screen.Delegated, "Taak van anderen", Icons.Outlined.PeopleAlt),
        Triple(Screen.Ideas, "Ideeën", Icons.Outlined.Lightbulb),
        Triple(Screen.Archive, "Archief", Icons.Outlined.Archive)
    )
    Column(
        modifier = Modifier
            .width(178.dp)
            .fillMaxHeight()
            .clip(RoundedCornerShape(22.dp))
            .background(Color.Black.copy(alpha = 0.28f))
            .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(22.dp))
            .padding(20.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.cc_logo_reference),
            contentDescription = "CC logo",
            modifier = Modifier.fillMaxWidth().height(92.dp),
            contentScale = ContentScale.Fit
        )
        Spacer(Modifier.height(28.dp))
        items.forEach { (screen, label, icon) ->
            NavItem(label, icon = { Icon(icon, null, tint = if (current == screen) CCColor.Gold else CCColor.Text.copy(alpha = 0.72f), modifier = Modifier.size(19.dp)) }, active = current == screen) { onNavigate(screen) }
            Spacer(Modifier.height(10.dp))
        }
        Spacer(Modifier.weight(1f))
        ThinLine()
        Spacer(Modifier.height(18.dp))
        NavItem("Instellingen", icon = { Icon(Icons.Outlined.Settings, null, tint = CCColor.Text.copy(alpha = 0.72f), modifier = Modifier.size(19.dp)) }, active = current == Screen.Settings) { onNavigate(Screen.Settings) }
    }
}

@Composable
fun NavItem(label: String, icon: @Composable () -> Unit, active: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(46.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(if (active) CCColor.Gold.copy(alpha = 0.10f) else Color.Transparent)
            .border(if (active) 1.dp else 0.dp, if (active) CCColor.Gold.copy(alpha = 0.30f) else Color.Transparent, RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon()
        Spacer(Modifier.width(12.dp))
        Text(label, color = if (active) CCColor.Gold else CCColor.Text.copy(alpha = 0.80f), fontSize = 13.sp)
    }
}

@Composable
fun FocusScreen(onWrite: () -> Unit, onOpenCard: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(30.dp)) {
        TopActions()
        Text("Waar wil je vandaag mee beginnen?", color = CCColor.Text, fontFamily = FontFamily.Serif, fontSize = 28.sp)
        GoldAccentLine()
        Spacer(Modifier.height(20.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(18.dp), modifier = Modifier.fillMaxWidth()) {
            FocusSummary("Focus", "4", "Vandaag aandacht", CCColor.Red, Modifier.weight(1f))
            FocusSummary("Reactie afwachten", "3", "Controleren", CCColor.Yellow, Modifier.weight(1f))
            FocusSummary("Taak van anderen", "2", "Opvolgen", CCColor.Orange, Modifier.weight(1f))
        }
        Spacer(Modifier.height(22.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Column(Modifier.weight(1.1f)) {
                Text("Focus", color = CCColor.Text, fontFamily = FontFamily.Serif, fontSize = 24.sp)
                Spacer(Modifier.height(12.dp))
                TaskRow("Teamoverleg voorbereiden", "Wendy Jansen  •  Vergaderruimte 2B", "Vandaag 09:30", CCColor.Red, onClick = onOpenCard)
                TaskRow("Offerte controleren", "Marco de Wit", "Vandaag", CCColor.Yellow, onClick = {})
                TaskRow("Update kwartaalrapport", "Financiën", "Morgen", CCColor.Blue, onClick = {})
            }
            Column(Modifier.weight(0.9f)) {
                MasterCardPanel(
                    CCard("Teamoverleg voorbereiden", "Wendy Jansen", "Vergaderruimte 2B", "Focus", CCColor.Red, "Vandaag 09:30", "Hoog"),
                    compact = true
                )
            }
        }
        Spacer(Modifier.weight(1f))
        WriteButton(onWrite)
        BrandRule()
    }
}

@Composable
fun TopActions() {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Outlined.Search, null, tint = CCColor.Text.copy(alpha = 0.70f), modifier = Modifier.size(24.dp))
        Spacer(Modifier.width(18.dp))
        Box {
            Icon(Icons.Outlined.NotificationsNone, null, tint = CCColor.Text.copy(alpha = 0.70f), modifier = Modifier.size(24.dp))
            Box(Modifier.size(8.dp).clip(CircleShape).background(CCColor.Gold).align(Alignment.TopEnd))
        }
    }
}

@Composable
fun FocusSummary(title: String, number: String, caption: String, color: Color, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .height(145.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(CCColor.Surface.copy(alpha = 0.82f))
            .border(1.dp, Color.White.copy(alpha = 0.10f), RoundedCornerShape(24.dp))
            .padding(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(Modifier.size(42.dp).clip(CircleShape).border(1.5.dp, color, CircleShape), contentAlignment = Alignment.Center) {
            Icon(Icons.Outlined.WorkOutline, null, tint = color, modifier = Modifier.size(22.dp))
        }
        Spacer(Modifier.height(12.dp))
        Text(title, color = CCColor.Text, fontSize = 17.sp)
        Text(number, color = CCColor.Text, fontSize = 42.sp, fontFamily = FontFamily.Serif)
        Text(caption, color = CCColor.Muted, fontSize = 12.sp)
    }
}

@Composable
fun TaskRow(title: String, subtitle: String, date: String, color: Color, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .padding(bottom = 10.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(CCColor.Surface.copy(alpha = 0.86f))
            .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(18.dp))
            .clickable(onClick = onClick)
    ) {
        Box(Modifier.width(4.dp).fillMaxHeight().background(color))
        Row(Modifier.fillMaxSize().padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(38.dp).clip(CircleShape).border(1.dp, color, CircleShape), contentAlignment = Alignment.Center) {
                Icon(Icons.Outlined.WorkOutline, null, tint = color, modifier = Modifier.size(19.dp))
            }
            Spacer(Modifier.width(16.dp))
            Column(Modifier.weight(1f)) {
                Text(title, color = CCColor.Text, fontSize = 17.sp, fontFamily = FontFamily.Serif)
                Text(subtitle, color = CCColor.Muted, fontSize = 12.sp)
            }
            Text(date, color = if (date == "Morgen") CCColor.Blue else CCColor.Gold, fontSize = 12.sp)
            Spacer(Modifier.width(16.dp))
            Icon(Icons.Outlined.MoreHoriz, null, tint = CCColor.Text.copy(alpha = 0.70f))
        }
    }
}

@Composable
fun WriteButton(onWrite: () -> Unit) {
    Row(
        modifier = Modifier
            .width(380.dp)
            .height(64.dp)
            .clip(RoundedCornerShape(32.dp))
            .background(Brush.horizontalGradient(listOf(CCColor.Gold.copy(alpha = 0.95f), CCColor.GoldSoft.copy(alpha = 0.22f))))
            .border(1.dp, CCColor.Gold, RoundedCornerShape(32.dp))
            .clickable(onClick = onWrite)
            .padding(horizontal = 22.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(Modifier.size(48.dp).clip(CircleShape).background(Color.Black.copy(alpha = 0.78f)), contentAlignment = Alignment.Center) {
            Icon(Icons.Outlined.Create, null, tint = CCColor.Gold, modifier = Modifier.size(25.dp))
        }
        Spacer(Modifier.width(18.dp))
        Column(Modifier.weight(1f)) {
            Text("Schrijven", color = Color.Black.copy(alpha = 0.90f), fontSize = 19.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 2.sp)
            Text("Nieuwe gedachte vastleggen", color = Color.Black.copy(alpha = 0.65f), fontSize = 12.sp)
        }
        Text("→", color = Color.Black.copy(alpha = 0.80f), fontSize = 32.sp)
    }
}

@Composable
fun WriteScreen(onBack: () -> Unit, onOrganize: () -> Unit) {
    val strokes = remember { mutableStateListOf<List<Offset>>() }
    var currentStroke by remember { mutableStateOf<List<Offset>>(emptyList()) }
    Box(Modifier.fillMaxSize().background(CCColor.Midnight)) {
        Column(Modifier.fillMaxSize().padding(18.dp)) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text("‹  Terug naar Focus", color = CCColor.Gold, fontSize = 14.sp, modifier = Modifier.clickable(onClick = onBack))
                Spacer(Modifier.weight(1f))
                Text("Schrijven", color = CCColor.Gold, fontSize = 18.sp)
                Spacer(Modifier.weight(1f))
                Text("Automatisch opgeslagen", color = CCColor.Muted, fontSize = 12.sp)
            }
            Spacer(Modifier.height(18.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(28.dp))
                    .background(CCColor.Paper)
                    .border(1.dp, CCColor.Gold.copy(alpha = 0.22f), RoundedCornerShape(28.dp))
                    .padding(26.dp)
            ) {
                Text("Schrijf hier je gedachte...", color = CCColor.Ink.copy(alpha = 0.25f), fontSize = 20.sp)
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragStart = { offset -> currentStroke = listOf(offset) },
                                onDrag = { change, _ -> currentStroke = currentStroke + change.position },
                                onDragEnd = {
                                    if (currentStroke.isNotEmpty()) strokes.add(currentStroke)
                                    currentStroke = emptyList()
                                }
                            )
                        }
                ) {
                    fun drawStroke(points: List<Offset>) {
                        if (points.size < 2) return
                        val path = Path().apply {
                            moveTo(points.first().x, points.first().y)
                            points.drop(1).forEach { lineTo(it.x, it.y) }
                        }
                        drawPath(path, CCColor.Ink, style = Stroke(width = 4.5f, cap = StrokeCap.Round))
                    }
                    strokes.forEach { drawStroke(it) }
                    drawStroke(currentStroke)
                }
                Text("CC", color = CCColor.Ink.copy(alpha = 0.035f), fontSize = 140.sp, fontFamily = FontFamily.Serif, modifier = Modifier.align(Alignment.Center))
            }
            Spacer(Modifier.height(14.dp))
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                BrandRule(modifier = Modifier.weight(1f))
                Spacer(Modifier.width(16.dp))
                Row(
                    modifier = Modifier
                        .height(54.dp)
                        .clip(RoundedCornerShape(28.dp))
                        .background(CCColor.Gold)
                        .clickable(onClick = onOrganize)
                        .padding(horizontal = 24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Outlined.Check, null, tint = Color.Black)
                    Spacer(Modifier.width(10.dp))
                    Text("CC begrijpt mijn notitie", color = Color.Black, fontSize = 15.sp)
                }
            }
        }
    }
}

@Composable
fun ProposalScreen(onBack: () -> Unit, onAccept: () -> Unit) {
    Box(Modifier.fillMaxSize().background(CCColor.Midnight).padding(28.dp)) {
        Row(Modifier.fillMaxSize(), horizontalArrangement = Arrangement.spacedBy(22.dp)) {
            Column(Modifier.weight(1f).fillMaxHeight()) {
                Text("‹  Terug naar schrijven", color = CCColor.Gold, fontSize = 14.sp, modifier = Modifier.clickable(onClick = onBack))
                Spacer(Modifier.height(18.dp))
                Box(Modifier.fillMaxSize().clip(RoundedCornerShape(28.dp)).background(CCColor.Paper).padding(32.dp)) {
                    Column {
                        Text("Teamoverleg voorbereiden", color = CCColor.Ink, fontFamily = FontFamily.Serif, fontSize = 24.sp)
                        Spacer(Modifier.height(18.dp))
                        Text("Dinsdag 9.30", color = CCColor.Ink, fontSize = 18.sp)
                        Text("Bespreken Q3 doelen", color = CCColor.Ink, fontSize = 18.sp)
                        Text("Actiepunten verdelen", color = CCColor.Ink, fontSize = 18.sp)
                        Text("Wendy uitnodigen", color = CCColor.Ink, fontSize = 18.sp)
                        Text("Locatie: Vergaderruimte 2B", color = CCColor.Ink, fontSize = 18.sp)
                    }
                }
            }
            Column(Modifier.width(390.dp).fillMaxHeight().clip(RoundedCornerShape(26.dp)).background(CCColor.Surface.copy(alpha = 0.92f)).border(1.dp, CCColor.Gold.copy(alpha = 0.30f), RoundedCornerShape(26.dp)).padding(24.dp)) {
                Text("Voorstel van CC", color = CCColor.Text, fontFamily = FontFamily.Serif, fontSize = 26.sp)
                Text("Ik heb deze informatie gevonden. Jij beslist.", color = CCColor.Muted, fontSize = 13.sp)
                Spacer(Modifier.height(22.dp))
                ProposalLine("Categorie", "Focus", Icons.Outlined.WorkOutline)
                ProposalLine("Persoon", "Wendy Jansen", Icons.Outlined.PersonOutline)
                ProposalLine("Datum", "Dinsdag 6 juli 2026", Icons.Outlined.CalendarMonth)
                ProposalLine("Tijd", "09:30 – 10:30", Icons.Outlined.Schedule)
                ProposalLine("Locatie", "Vergaderruimte 2B", Icons.Outlined.Place)
                ProposalLine("Herinnering", "15 minuten vooraf", Icons.Outlined.NotificationsNone)
                Spacer(Modifier.weight(1f))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    SecondaryButton("Aanpassen", Modifier.weight(1f)) {}
                    PrimaryButton("Akkoord", Modifier.weight(1f), onAccept)
                }
            }
        }
    }
}

@Composable
fun ProposalLine(label: String, value: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Row(Modifier.fillMaxWidth().padding(vertical = 10.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, tint = CCColor.Gold.copy(alpha = 0.85f), modifier = Modifier.size(22.dp))
        Spacer(Modifier.width(14.dp))
        Column {
            Text(label, color = CCColor.Muted, fontSize = 12.sp)
            Text(value, color = CCColor.Text, fontSize = 15.sp)
        }
    }
}

@Composable
fun MasterCardPanel(card: CCard, compact: Boolean = false) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(24.dp))
            .background(CCColor.Surface.copy(alpha = 0.82f))
            .border(1.dp, Color.White.copy(alpha = 0.10f), RoundedCornerShape(24.dp))
            .padding(if (compact) 18.dp else 28.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.clip(RoundedCornerShape(16.dp)).background(card.color.copy(alpha = 0.16f)).border(1.dp, card.color.copy(alpha = 0.55f), RoundedCornerShape(16.dp)).padding(horizontal = 12.dp, vertical = 7.dp)) {
                Text(card.category, color = card.color, fontSize = 12.sp)
            }
            Spacer(Modifier.weight(1f))
            Icon(Icons.Outlined.Edit, null, tint = CCColor.Text.copy(alpha = 0.70f), modifier = Modifier.size(21.dp))
            Spacer(Modifier.width(14.dp))
            Icon(Icons.Outlined.MoreHoriz, null, tint = CCColor.Text.copy(alpha = 0.70f), modifier = Modifier.size(21.dp))
        }
        Spacer(Modifier.height(14.dp))
        Text(card.title, color = CCColor.Text, fontFamily = FontFamily.Serif, fontSize = if (compact) 24.sp else 30.sp)
        Spacer(Modifier.height(18.dp))
        LifeLine(card.color)
        Spacer(Modifier.height(18.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxWidth()) {
            InfoColumn(card, Modifier.weight(1f))
            Column(Modifier.weight(1f)) {
                Text("Notities", color = CCColor.Gold, fontSize = 16.sp)
                GoldAccentLine()
                NoteItem("3 juli 2026", "Wendy heeft de agenda aangeleverd.")
                NoteItem("5 juli 2026", "Actiepunten besproken met Marco.")
                AnimatedVisibility(!compact, enter = fadeIn(), exit = fadeOut()) {
                    Row(Modifier.fillMaxWidth().height(44.dp).clip(RoundedCornerShape(14.dp)).border(1.dp, CCColor.Gold.copy(alpha = 0.45f), RoundedCornerShape(14.dp)).padding(horizontal = 14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text("+  Notitie toevoegen", color = CCColor.Gold, fontSize = 14.sp)
                    }
                }
            }
        }
        if (!compact) {
            Spacer(Modifier.weight(1f))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                ActionButton("Gedaan", Icons.Outlined.CheckCircle, CCColor.Green, Modifier.weight(1f))
                ActionButton("Later", Icons.Outlined.Schedule, CCColor.Yellow, Modifier.weight(1f))
                ActionButton("Notitie", Icons.Outlined.Description, CCColor.Blue, Modifier.weight(1f))
                ActionButton("Nieuwe datum", Icons.Outlined.CalendarMonth, CCColor.Purple, Modifier.weight(1f))
                ActionButton("Verplaatsen", Icons.Outlined.FolderOpen, CCColor.Teal, Modifier.weight(1f))
                ActionButton("Prullenbak", Icons.Outlined.DeleteOutline, CCColor.Red, Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun LifeLine(color: Color) {
    Column {
        Text("Levensloop", color = CCColor.Muted, fontSize = 12.sp)
        Spacer(Modifier.height(8.dp))
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            LifeStep("Idee", CCColor.Purple, false)
            Arrow()
            LifeStep("Focus", color, true)
            Arrow()
            LifeStep("Reactie", CCColor.Yellow, false)
            Arrow()
            LifeStep("Archief", CCColor.Green, false)
        }
    }
}

@Composable
fun LifeStep(label: String, color: Color, active: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(Modifier.size(36.dp).clip(CircleShape).border(1.5.dp, color, CircleShape).background(if (active) color.copy(alpha = 0.12f) else Color.Transparent), contentAlignment = Alignment.Center) {
            Box(Modifier.size(7.dp).clip(CircleShape).background(color))
        }
        Text(label, color = if (active) color else CCColor.Muted, fontSize = 10.sp)
    }
}

@Composable fun Arrow() { Text("  →  ", color = CCColor.Muted, fontSize = 18.sp) }

@Composable
fun InfoColumn(card: CCard, modifier: Modifier) {
    Column(modifier.clip(RoundedCornerShape(18.dp)).background(Color.Black.copy(alpha = 0.12f)).border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(18.dp)).padding(16.dp)) {
        InfoLine("Persoon", card.person, Icons.Outlined.PersonOutline)
        InfoLine("Datum & tijd", "Dinsdag 6 juli 2026\n09:30 – 10:30", Icons.Outlined.CalendarMonth)
        InfoLine("Locatie", card.location, Icons.Outlined.Place)
        InfoLine("Herinnering", "Iedere 2 weken\ntot 31 december 2026", Icons.Outlined.NotificationsNone)
        InfoLine("Prioriteit", card.priority, Icons.Outlined.Flag, CCColor.Red)
    }
}

@Composable
fun InfoLine(label: String, value: String, icon: androidx.compose.ui.graphics.vector.ImageVector, tint: Color = CCColor.Muted) {
    Row(Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.Top) {
        Icon(icon, null, tint = tint, modifier = Modifier.size(20.dp))
        Spacer(Modifier.width(12.dp))
        Column {
            Text(label, color = CCColor.Muted, fontSize = 12.sp)
            Text(value, color = if (tint == CCColor.Red) CCColor.Red else CCColor.Text, fontSize = 14.sp)
        }
    }
}

@Composable
fun NoteItem(date: String, text: String) {
    Column(Modifier.fillMaxWidth().padding(vertical = 7.dp).clip(RoundedCornerShape(14.dp)).background(Color.Black.copy(alpha = 0.16f)).padding(12.dp)) {
        Text(date, color = CCColor.Muted, fontSize = 11.sp)
        Spacer(Modifier.height(3.dp))
        Text(text, color = CCColor.Text, fontSize = 13.sp)
    }
}

@Composable
fun ActionButton(label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: Color, modifier: Modifier = Modifier) {
    Column(modifier.height(62.dp).clip(RoundedCornerShape(16.dp)).background(CCColor.SurfaceSoft.copy(alpha = 0.70f)).border(1.dp, Color.White.copy(alpha = 0.09f), RoundedCornerShape(16.dp)).padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Icon(icon, null, tint = color, modifier = Modifier.size(20.dp))
        Text(label, color = CCColor.Text, fontSize = 10.sp)
    }
}

@Composable
fun CategoryScreen(title: String, subtitle: String, color: Color, onWrite: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(30.dp)) {
        TopActions()
        Text(title, color = CCColor.Text, fontFamily = FontFamily.Serif, fontSize = 30.sp)
        Text(subtitle, color = CCColor.Muted, fontSize = 13.sp)
        GoldAccentLine()
        Spacer(Modifier.height(24.dp))
        TaskRow("Voorstel nieuwe leverancier", "Marco de Wit", "Vandaag", color, onClick = {})
        TaskRow("Goedkeuring campagneplan", "Marketingteam", "Deze week", color, onClick = {})
        TaskRow("Budgetaanvraag Q3", "Directie", "Later", color, onClick = {})
        Spacer(Modifier.weight(1f))
        WriteButton(onWrite)
        BrandRule()
    }
}

@Composable
fun SettingsScreen() {
    Column(Modifier.fillMaxSize().padding(30.dp)) {
        Text("Instellingen", color = CCColor.Text, fontFamily = FontFamily.Serif, fontSize = 30.sp)
        Text("CC Alpha 0.1", color = CCColor.Muted, fontSize = 13.sp)
    }
}

@Composable
fun PrimaryButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(modifier.height(48.dp).clip(RoundedCornerShape(24.dp)).background(CCColor.Gold).clickable(onClick = onClick), contentAlignment = Alignment.Center) {
        Text(text, color = Color.Black, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun SecondaryButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(modifier.height(48.dp).clip(RoundedCornerShape(24.dp)).border(1.dp, CCColor.Gold.copy(alpha = 0.65f), RoundedCornerShape(24.dp)).clickable(onClick = onClick), contentAlignment = Alignment.Center) {
        Text(text, color = CCColor.Gold)
    }
}

@Composable
fun GoldAccentLine() {
    Box(Modifier.padding(top = 10.dp).width(70.dp).height(2.dp).background(Brush.horizontalGradient(listOf(CCColor.Gold, Color.Transparent))))
}

@Composable
fun ThinLine() { Box(Modifier.fillMaxWidth().height(1.dp).background(Color.White.copy(alpha = 0.10f))) }

@Composable
fun BrandRule(modifier: Modifier = Modifier) {
    Row(modifier, verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
        Box(Modifier.width(110.dp).height(1.dp).background(Brush.horizontalGradient(listOf(Color.Transparent, CCColor.Gold.copy(alpha = 0.45f)))))
        Spacer(Modifier.width(16.dp))
        Text("Rust in je hoofd. Focus op wat vandaag telt.", color = CCColor.Text.copy(alpha = 0.36f), fontSize = 13.sp)
        Spacer(Modifier.width(16.dp))
        Box(Modifier.width(110.dp).height(1.dp).background(Brush.horizontalGradient(listOf(CCColor.Gold.copy(alpha = 0.45f), Color.Transparent))))
    }
}
