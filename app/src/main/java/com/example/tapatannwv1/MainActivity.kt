package com.example.tapatannwv1

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tapatannwv1.model.GameViewModel
import com.example.tapatannwv1.model.SettingsViewModel
import com.example.tapatannwv1.screens.GameScreen
import com.example.tapatannwv1.screens.HelpScreen
import com.example.tapatannwv1.screens.HomeScreen
import com.example.tapatannwv1.screens.SettingsScreen
import com.example.tapatannwv1.ui.theme.TapatanNWv1Theme

class MainActivity : ComponentActivity() {

    private val settingsViewModel: SettingsViewModel by viewModels()
    private val gameViewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TapatanApp(settingsViewModel, gameViewModel)
        }
    }
}

@Composable
fun TapatanApp(
    settingsViewModel: SettingsViewModel,
    gameViewModel: GameViewModel
) {
    TapatanNWv1Theme {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.background(color = colorResource(id = R.color.primaryColor))
        ) {

            composable("home") {
                HomeScreen(
                    onStartNewMatch = { navController.navigate("game") },
                    onSettingsClicked = { navController.navigate("settings") },
                    onHelpClicked = { navController.navigate("help") }
                )
            }

            composable("game") {
                GameScreen(
                    player1Name = gameViewModel.player1.name.value,
                    player2Name = gameViewModel.player2.name.value,
                    onBackClicked = { navController.popBackStack() },
                    onHelpClicked = { navController.navigate("help") }
                )
            }

            composable("help") {
                HelpScreen(
                    onBackClicked = { navController.popBackStack() }
                )
            }

            composable("settings") {
                SettingsScreen(
                    onHelpClicked = { navController.navigate("help") },
                    onBackClicked = { navController.popBackStack() },
                    onApplyClicked = { player1Name, player2Name, player1Piece, player2Piece ->
                        gameViewModel.setPlayerNames(player1Name, player2Name)
                        gameViewModel.setPlayerImage(player1Piece, player2Piece)

                        navController.navigate("home")
                    }
                )
            }
        }
    }
}




