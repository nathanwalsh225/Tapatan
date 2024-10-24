package com.example.tapatannwv1.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Help
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tapatannwv1.R
import com.example.tapatannwv1.model.GameViewModel

@Composable
fun GameScreen(
    player1Name: String,
    player2Name: String,
    onHelpClicked: () -> Unit,
    onBackClicked: () -> Unit,
    gameViewModel: GameViewModel = viewModel()
) {
    val gameWinner = gameViewModel.winningPlayer.value?.name

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp),
        verticalArrangement = Arrangement.Top
    ) {
        //'Nav' bar
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
    }
    //END OF NAV

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Column(modifier = Modifier
            .padding(16.dp)
            .rotate(180f)
        ) {
            when {
                gameViewModel.stalemate.value == true -> {
                    Text(
                        text = "Draw!",
                        style = MaterialTheme.typography.headlineLarge
                    )
                }
                gameWinner != null -> {
                    if (gameWinner.value == player2Name) {
                        Text(
                            text = "You Win!",
                            style = MaterialTheme.typography.headlineLarge
                        )
                    } else {
                        Text(
                            text = "You Lose!",
                            style = MaterialTheme.typography.headlineLarge
                        )
                    }
                    Text(
                        text = "${gameWinner.value} Wins!",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
                else -> {
                    Text(
                        text = "${gameViewModel.currentPlayer.value.name.value}'s turn",
                        style = MaterialTheme.typography.headlineLarge
                    )
                }
            }
        }


        Spacer(modifier = Modifier.height(20.dp))




        Box(
            modifier = Modifier
                .size(400.dp) // Adjust size of the board
                .padding(16.dp)
        ) {

            // Background Image of Tapatan Board
            Image(
                painter = painterResource(id = R.drawable.tapatanboard),
                contentDescription = "Tapatan Board",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            )

            // Overlay 3x3 Cells on Top of Board
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                for (row in 0 until 3) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(), //Border for the boxes too see easier
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        for (col in 0 until 3) {
                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .background(color = Color.LightGray) //Background for the boxes
                                    .clickable {

                                        Log.d("GameViewModel", "(GameSCreen) Player 1 value : ${gameViewModel.player1.name.value}")
                                        Log.d("GameViewModel", "(GameSCreen) Player 1 image.value : ${gameViewModel.player1.pieceImage.value}")
                                        gameViewModel.onCellClicked(row, col)
                                    },
                                contentAlignment = Alignment.Center,

                                ) {
                                val cellValue = gameViewModel.board[row][col]
                                if (cellValue != null) {
                                    Image(
                                        painter = painterResource(id = cellValue),
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }



        Spacer(modifier = Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = gameViewModel.player1.pieceImage.value),
                contentDescription = "Tapatan Board",
                modifier = Modifier
                    .padding(12.dp)
            )

            Image(
                painter = painterResource(id = gameViewModel.player1.pieceImage.value),
                contentDescription = "Tapatan Board",
                modifier = Modifier
                    .padding(12.dp)
            )

            Image(
                painter = painterResource(id =  gameViewModel.player1.pieceImage.value),
                contentDescription = "Tapatan Board",
                modifier = Modifier
                    .padding(12.dp)
            )
        }

        Column(modifier = Modifier.padding(16.dp)) {
            when {
                gameViewModel.stalemate.value == true -> {
                    Text(
                        text = "Draw!",
                        style = MaterialTheme.typography.headlineLarge
                    )
                }
                gameWinner != null -> {
                    if (gameWinner.value == player1Name) {
                        Text(
                            text = "You Win!",
                            style = MaterialTheme.typography.headlineLarge
                        )
                    } else {
                        Text(
                            text = "You Lose!",
                            style = MaterialTheme.typography.headlineLarge
                        )
                    }
                    Text(
                        text = "${gameWinner.value} Wins!",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
                else -> {
                    Text(
                        text = "${gameViewModel.currentPlayer.value.name.value}'s turn",
                        style = MaterialTheme.typography.headlineLarge
                    )
                }
            }
        }


        Button(
            onClick = { gameViewModel.resetGame() },
            modifier = Modifier
                .fillMaxWidth(0.9f)
        ) {
            Text(text = "Reset Game")
        }

    }
}

@Preview
@Composable
fun GameScreenPreview() {
    GameScreen(player1Name = "Nathan", player2Name = "Player 2", {}, {})
}