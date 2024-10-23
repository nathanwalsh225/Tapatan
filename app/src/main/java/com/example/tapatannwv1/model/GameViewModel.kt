package com.example.tapatannwv1.model

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.tapatannwv1.R

class GameViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    var board = List(3) { mutableStateListOf<Int?>(null, null, null) }

    val player1Name = mutableStateOf(savedStateHandle.get<String>("player1Name") ?: "Player 1")
    val player2Name = mutableStateOf(savedStateHandle.get<String>("player2Name") ?: "Player 2")
    val currentPlayer = mutableStateOf(player1Name.value)

    val winningPlayer = mutableStateOf<String?>(null)


    fun setPlayerNames(name1: String, name2: String) {
        player1Name.value = name1
        player2Name.value = name2
        savedStateHandle["player1Name"] = name1
        savedStateHandle["player2Name"] = name2

        currentPlayer.value = player1Name.value
    }

    fun setPlayerImage(player: Int, image: Int) {
        if (player == 1) {
            savedStateHandle["player1Image"] = image
        } else {
            savedStateHandle["player2Image"] = image
        }
    }

    fun onCellClicked( //There is some issue possibly here with the logic to swap player names if left default
        row: Int,
        col: Int
    ) {
        if (board[row][col] == null) {

            board[row][col] =
                if (currentPlayer.value == player1Name.value) R.drawable.player1_image else R.drawable.player2_image

            if(checkWin()){

                winningPlayer.value = currentPlayer.value
                Log.d("GameViewModel", "Winner detected: ${winningPlayer.value}")
            } else {
                swapPlayers()
            }

        }
    }


    private fun checkWin(): Boolean {
        val flatBoard = board.flatten() //flatting the board to check it easier

        val winningCombinations = listOf(
            listOf(0, 1, 2),
            listOf(3, 4, 5),
            listOf(6, 7, 8),
            listOf(0, 3, 6),
            listOf(1, 4, 7),
            listOf(2, 5, 8),
            listOf(0, 4, 8),
            listOf(2, 4, 6)
        )

        val currentPlayerPiece = if (currentPlayer.value == player1Name.value) R.drawable.player1_image else R.drawable.player2_image

        ///ADDITIONAL FEATURE
        ///HIGHLIGHT WINNING SECTION?

        for (combination in winningCombinations) {
            if (combination.all { flatBoard[it] == currentPlayerPiece }) {
                return true // A winner has been detected
            }
        }
        return false //No winner, game continues
    }


    private fun swapPlayers() {
        currentPlayer.value =
            if (currentPlayer.value == player1Name.value) player2Name.value else player1Name.value
    }

    fun resetGame() {
        for (r in 0..2) {
            for (c in 0..2) {
                board[r][c] = null
            }
        }
        winningPlayer.value = null
        currentPlayer.value = player1Name.value
    }


}


