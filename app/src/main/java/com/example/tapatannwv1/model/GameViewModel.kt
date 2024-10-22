package com.example.tapatannwv1.model

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tapatannwv1.R

class GameViewModel (private val savedStateHandle: SavedStateHandle) : ViewModel() {

    var board = List(3) { mutableStateListOf<Int?>(null, null, null) }

    val player1Name = mutableStateOf(savedStateHandle.get<String>("player1Name") ?: "Player 1")
    val player2Name = mutableStateOf(savedStateHandle.get<String>("player2Name") ?: "Player 2")
    val currentPlayer = mutableStateOf(player1Name.value)

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

    fun onCellClicked(row: Int, col: Int) { //There is some issue possibly here with the logic to swap player names if left default
        if (board[row][col] == null) {

            board[row][col] = if (currentPlayer.value == player1Name.value) R.drawable.player1_image else R.drawable.player2_image
            swapPlayers()
        }
    }

    private fun swapPlayers() {
        
        Log.d("GameViewModel", "Swapping players from ${currentPlayer.value}")
        currentPlayer.value = if (currentPlayer.value == player1Name.value) player2Name.value else player1Name.value
        Log.d("GameViewModel", "To ${currentPlayer.value}")
    }

    fun resetGame() {
        for (r in 0..2) {
            for (c in 0..2) {
                board[r][c] = null
            }
        }
        currentPlayer.value = player1Name.value
    }


}


