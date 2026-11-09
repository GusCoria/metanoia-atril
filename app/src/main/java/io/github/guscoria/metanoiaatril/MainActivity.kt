package io.github.guscoria.metanoiaatril

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import io.github.guscoria.metanoiaatril.ui.home.HomeScreen
import io.github.guscoria.metanoiaatril.ui.theme.MetanoiaAtrilTheme

/** Solo aloja la UI; no contiene lógica de negocio. */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MetanoiaAtrilTheme {
                HomeScreen()
            }
        }
    }
}
