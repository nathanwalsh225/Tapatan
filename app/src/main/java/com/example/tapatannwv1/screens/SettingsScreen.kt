package com.example.tapatannwv1.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tapatannwv1.R
import com.example.tapatannwv1.model.GameViewModel
import com.example.tapatannwv1.model.SettingsViewModel

@Composable
fun SettingsScreen(
    onHelpClicked: () -> Unit,
    onBackClicked: () -> Unit,
    onApplyClicked: (String, String, Int, Int) -> Unit,
    gameViewModel: GameViewModel = viewModel(),
) {
    val focusManager = LocalFocusManager.current
    val player1Piece = remember { mutableStateOf(0) }
    val player2Piece = remember { mutableStateOf(0) }




    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp, bottom = 32.dp)
            .clickable(
                onClick = { focusManager.clearFocus() },
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = colorResource(id = R.color.secondaryColor)),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,

                ) {
                IconButton(onClick = onBackClicked) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier
                            .size(50.dp),
                        tint = colorResource(id = R.color.tertiaryColor)
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.tapatanlogo),
                    contentDescription = "Tapatan Logo",
                    modifier = Modifier
                        .size(50.dp)
                )

                IconButton(
                    onClick = onHelpClicked,
                    modifier = Modifier.size(50.dp)
                ) {
                    Icon(
                        Icons.Default.Help,
                        contentDescription = "Help",
                        tint = colorResource(id = R.color.tertiaryColor)
                    )
                }
            }

            //END OF NAV
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally),
            ) {
                item {
                    Text(
                        text = "Enter Names:",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        fontSize = 30.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    Row {
                        Icon(
                            Icons.Default.Person2,
                            contentDescription = "Person",
                            modifier = Modifier.size(50.dp)
                        )

                        TextField(
                            value = gameViewModel.player1.name.value,
                            onValueChange = { gameViewModel.player1.name.value = it },
                            label = { Text("Player 1 Name") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row {
                        Icon(
                            Icons.Default.Person2,
                            contentDescription = "Person",
                            modifier = Modifier.size(50.dp)
                        )
                        TextField(
                            value = gameViewModel.player2.name.value,
                            onValueChange = { gameViewModel.player2.name.value = it },
                            label = { Text("Player 2 Name") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Player 1",
                style = MaterialTheme.typography.headlineLarge,
            )


            LazyRow(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                items(gameViewModel.availablePieces.size) { index ->
                    Image(
                        painter = painterResource(id = gameViewModel.availablePieces[index]),
                        contentDescription = "Piece $index",
                        modifier = Modifier
                            .size(100.dp)
                            .padding(8.dp)
                            .clickable {
                                player1Piece.value = index
                            }.border(
                                shape = CircleShape,
                                width = if (index == player1Piece.value) 4.dp else 0.dp,
                                color = if (index == player1Piece.value) Color.Green else Color.Transparent
                            )
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Player 2",
                style = MaterialTheme.typography.headlineLarge,
            )
            LazyRow(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                items(gameViewModel.availablePieces.size) { index ->
                    Image(
                        painter = painterResource(id = gameViewModel.availablePieces[index]),
                        contentDescription = "Piece $index",
                        modifier = Modifier
                            .size(100.dp)
                            .padding(8.dp)
                            .clickable {
                                player2Piece.value = index
                            }.border(
                                shape = CircleShape,
                                width = if (index == player2Piece.value) 4.dp else 0.dp,
                                color = if (index == player2Piece.value) Color.Green else Color.Transparent
                            )
                    )
                }
            }
        }



        //Button has been taken outside the Column element
        Button(
            onClick = {
                val player1 = gameViewModel.player1.name.value.ifBlank { "Player 1" }
                val player2 = gameViewModel.player2.name.value.ifBlank { "Player 2" }
                val image1 = player1Piece.value
                val image2 = player2Piece.value
                //gameViewModel.setPlayerNames(player1, player2)
                gameViewModel.setPlayerNames(player1, player2)
                gameViewModel.setPlayerImage(image1, image2)

                Log.d("SettingsScreen", "(SettingsScreen) $image1 + $image2")
                onApplyClicked(player1, player2, image1, image2)
            },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(75.dp)
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            shape = RectangleShape
        ) {
            Text(
                text = "APPLY",
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

//@Preview
//@Composable
//fun PreviewSettingsScreen() {
//    SettingsScreen({}, {}, {null, null})
//}