package com.example.tapatannwv1.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Help
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tapatannwv1.R
import com.example.tapatannwv1.model.GameViewModel

@SuppressLint("UnrememberedMutableState")
@Composable
fun GameScreen(
    player1Name: String,
    player2Name: String,
    onHelpClicked: () -> Unit,
    onBackClicked: () -> Unit,
    gameViewModel: GameViewModel = viewModel()
) {
    val gameWinner = gameViewModel.winningPlayer.value?.name
    val focusManager = LocalFocusManager.current

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 24.dp)
    ) {
        val boxWidth = maxWidth
        val boxHeight = maxHeight

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
                    modifier = Modifier.size(50.dp)
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
            Spacer(modifier = Modifier.height(boxHeight * 0.05f))

            Column(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .rotate(180f)
            ) {
                when {
                    gameViewModel.stalemate.value == true -> {
                        Text(
                            text = "Draw!",
                            style = MaterialTheme.typography.headlineLarge,
                            fontSize = (boxWidth * 0.07f).value.sp
                        )
                    }

                    gameWinner != null -> {
                        if (gameWinner.value == player2Name) {
                            Text(
                                text = "You Win!",
                                style = MaterialTheme.typography.headlineLarge,
                                fontSize = (boxWidth * 0.07f).value.sp
                            )
                        } else {
                            Text(
                                text = "You Lose!",
                                style = MaterialTheme.typography.headlineLarge,
                                fontSize = (boxWidth * 0.07f).value.sp
                            )
                        }
                        Text(
                            text = "${gameWinner.value} Wins!",
                            style = MaterialTheme.typography.headlineSmall,
                            fontSize = (boxWidth * 0.07f).value.sp
                        )
                    }

                    else -> {
                        Text(
                            text = "${gameViewModel.currentPlayer.value.name.value}'s turn",
                            style = MaterialTheme.typography.headlineLarge,
                            fontSize = (boxWidth * 0.07f).value.sp

                        )
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (i in 0..2) {
                    if (gameViewModel.player1PiecesMovable[i]) {
                        // Check if player2 still has pieces that can be moved
                        Image(
                            painter = painterResource(id = gameViewModel.player1.pieceImage.value),
                            contentDescription = "Player 1 Piece $i",
                            modifier = Modifier
                                .size(boxWidth * 0.2f)
                                .padding(12.dp)
                        )
                    } else {
                        //outline for the pieces that cannot be moved
                        Box(
                            modifier = Modifier
                                .size(boxWidth * 0.2f)
                                .padding(12.dp)
                                .background(Color.Transparent)
                                .border(2.dp, Color.Gray, shape = CircleShape)
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .size(boxWidth * 0.9f)
                    .wrapContentHeight()
            ) {
                // Background Image of Tapatan Board
                Image(
                    painter = painterResource(id = R.drawable.tapatanboard),
                    contentDescription = "Tapatan Board",
                    modifier = Modifier
                        .size(boxWidth * 1f)
                        .padding(12.dp)
                )

                // Overlay 3x3 Cells on Top of Board-
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    for (row in 0 until 3) {
                        Row(
                            modifier = Modifier.fillMaxWidth(), //Border for the boxes too see easier
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            for (col in 0 until 3) {
                                Box(
                                    modifier = Modifier
                                        .size(boxWidth * 0.15f).background(Color.Gray)
                                        .clickable {
                                            focusManager.clearFocus()
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

            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (i in 0..2) {
                    if (gameViewModel.player2PiecesMovable[i]) {
                        // Check if player1 still has pieces to move
                        Image(
                            painter = painterResource(id = gameViewModel.player2.pieceImage.value),
                            contentDescription = "Player 2 Piece $i",
                            modifier = Modifier
                                .size(boxWidth * 0.2f)
                                .padding(12.dp)
                        )
                    } else {
                        // Show Circle Outline if the piece cannot be moved
                        Box(
                            modifier = Modifier
                                .size(boxWidth * 0.2f)
                                .padding(12.dp)
                                .background(Color.Transparent)
                                .border(2.dp, Color.Gray, shape = CircleShape)
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                when {
                    gameViewModel.stalemate.value == true -> {
                        Text(
                            text = "Draw!",
                            style = MaterialTheme.typography.headlineLarge,
                            fontSize = (boxWidth * 0.07f).value.sp
                        )
                    }

                    gameWinner != null -> {
                        if (gameWinner.value == player1Name) {
                            Text(
                                text = "You Win!",
                                style = MaterialTheme.typography.headlineLarge,
                                fontSize = (boxWidth * 0.07f).value.sp
                            )
                        } else {
                            Text(
                                text = "You Lose!",
                                style = MaterialTheme.typography.headlineLarge,
                                fontSize = (boxWidth * 0.07f).value.sp
                            )
                        }
                        Text(
                            text = "${gameWinner.value} Wins!",
                            style = MaterialTheme.typography.headlineSmall,
                            fontSize = (boxWidth * 0.07f).value.sp
                        )
                    }

                    else -> {
                        Text(
                            text = "${gameViewModel.currentPlayer.value.name.value}'s turn",
                            style = MaterialTheme.typography.headlineLarge,
                            fontSize = (boxWidth * 0.07f).value.sp
                        )
                    }
                }
            }


        }

        Button(
            onClick = { gameViewModel.resetGame() },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(0.9f),
            shape = MaterialTheme.shapes.medium
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