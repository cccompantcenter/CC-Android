package com.cc.commandcenter
import com.cc.commandcenter.ui.theme.CcGraphite
import com.cc.commandcenter.ui.theme.CcMidnight
import com.cc.commandcenter.ui.theme.CcMuted
import com.cc.commandcenter.ui.theme.CcText
import com.cc.commandcenter.components.NavigationItem
import com.cc.commandcenter.components.CardPlaceholder
import com.cc.commandcenter.model.Screen
import com.cc.commandcenter.screens.TodayScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CCApp()
        }
    }
}

@Composable
fun Sidebar(
    currentScreen: Screen,
    onScreenSelected: (Screen) -> Unit
) {
    Column(
        modifier = Modifier
            .width(260.dp)
            .fillMaxHeight()
            .clip(RoundedCornerShape(32.dp))
            .background(CcGraphite)
            .padding(24.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.cc_logo_reference),
            contentDescription = "CC logo",
            modifier = Modifier
                .height(72.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        Screen.values().forEach { screen ->
            NavigationItem(
                screen = screen,
                selected = screen == currentScreen,
                onClick = { onScreenSelected(screen) }
            )
            Spacer(modifier = Modifier.height(10.dp))
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Alpha 0.2.1",
            color = CcMuted,
            fontSize = 13.sp
        )
    }
}

@Composable
fun MainContent(screen: Screen) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(36.dp))
            .background(Color(0xFF15130F))
            .padding(32.dp)
    ) {
        Text(
            text = screen.title,
            color = CcText,
            fontSize = 38.sp,
            fontWeight = FontWeight.Light
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = subtitleFor(screen),
            color = CcMuted,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        when (screen) {
            Screen.TODAY -> TodayScreen()
            Screen.FOCUS -> CardPlaceholder(
                title = "Focus",
                body = "Dit wordt de eerste actieve Card achter Vandaag."
            )
            Screen.MY_TASKS -> CardPlaceholder(
                title = "Mijn taken",
                body = "Hier komen straks jouw eigen Cards."
            )
            Screen.WAITING -> CardPlaceholder(
                title = "Reactie afwachten",
                body = "Hier komen Cards waarbij je wacht op iemand anders."
            )
            Screen.OTHERS -> CardPlaceholder(
                title = "Taken van anderen",
                body = "Hier komen Cards die bij een ander liggen, maar wel jouw aandacht vragen."
            )
            Screen.IDEAS -> CardPlaceholder(
                title = "Ideeën",
                body = "Hier bewaar je losse gedachten voordat ze actie worden."
            )
            Screen.ARCHIVE -> CardPlaceholder(
                title = "Archief",
                body = "Hier komen afgeronde of bewaarde Cards."
            )
        }
    }
}

fun subtitleFor(screen: Screen): String {
    return when (screen) {
        Screen.TODAY -> "Je dagelijkse startpunt. Eerst overzicht, daarna pas details."
        Screen.FOCUS -> "De belangrijkste Card van dit moment."
        Screen.MY_TASKS -> "Alles wat jij zelf moet oppakken."
        Screen.WAITING -> "Alles waar je nog reactie op verwacht."
        Screen.OTHERS -> "Taken die bij anderen liggen."
        Screen.IDEAS -> "Losse gedachten, plannen en mogelijkheden."
        Screen.ARCHIVE -> "Rustig bewaard, niet meer actief."
    }
}
