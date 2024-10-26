package com.example.tapatannwv1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.core.app.ActivityCompat.startActivityForResult
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

    //Activity result launcher for choosing an image from gallery
    private lateinit var pickImageLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Log.d("ImageCheck", "Hello")
        // Initialize the ActivityResultLauncher
        pickImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri = result.data?.data
                Log.d("ImageCheck", "Image URI: $imageUri")
                if (imageUri != null) {
                    //settingsViewModel.setSelectedImageUri(imageUri) // Assuming you want to store this in your ViewModel
                    Log.d("ImageCheck", "Image URI: $imageUri")
                    gameViewModel.addCustomImage(imageUri)
                }
            }
        }

        setContent {
            TapatanApp(settingsViewModel, gameViewModel, ::openGallery)
        }
    }

    // Function to open the gallery
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImageLauncher.launch(intent)
    }
}

@Composable
fun TapatanApp(
    settingsViewModel: SettingsViewModel,
    gameViewModel: GameViewModel,
    onChooseImageClicked: () -> Unit
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
                    onStartNewMatch = {
                        gameViewModel.resetGame()
                        navController.navigate("game")
                                      },
                    onSettingsClicked = { navController.navigate("settings") },
                    onHelpClicked = { navController.navigate("help") }
                )
            }

            composable("game") {
                GameScreen(
                    player1Name = gameViewModel.player1.name.value,
                    player2Name = gameViewModel.player2.name.value,
                    onBackClicked = { navController.popBackStack() },
                    onHelpClicked = { navController.navigate("help")},
                    gameViewModel = gameViewModel
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
                    },
                    onChooseImageClicked = onChooseImageClicked
                )
            }
        }
    }
}




