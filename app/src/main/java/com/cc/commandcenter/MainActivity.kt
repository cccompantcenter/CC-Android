package com.cc.commandcenter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.cc.commandcenter.model.Screen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CCApp()
        }
    }
}

fun subtitleFor(screen: Screen): String {
    return when (screen) {
        Screen.TODAY -> "Je dagelijkse startpunt. Eerst overzicht, daarna pas details."
        Screen.NOG_ORGANISEREN -> "Leg gedachten direct vast en organiseer ze later."
        Screen.FOCUS -> "De belangrijkste Card van dit moment."
        Screen.MY_TASKS -> "Alles wat jij zelf moet oppakken."
        Screen.WAITING -> "Alles waar je nog reactie op verwacht."
        Screen.OTHERS -> "Taken die bij anderen liggen."
        Screen.IDEAS -> "Losse gedachten, plannen en mogelijkheden."
        Screen.ARCHIVE -> "Rustig bewaard, niet meer actief."
    }
}
