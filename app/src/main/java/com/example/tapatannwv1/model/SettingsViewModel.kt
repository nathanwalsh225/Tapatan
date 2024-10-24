package com.example.tapatannwv1.model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class SettingsViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
///TODO
    ///Very possibly unnecessary
//    val player1Name = mutableStateOf(savedStateHandle.get<String>("player1Name") ?: "Player 1")
//    val player2Name = mutableStateOf(savedStateHandle.get<String>("player2Name") ?: "Player 2")
//    val boardTheme = mutableStateOf(savedStateHandle.get<String>("boardTheme") ?: "Classic")
//
//    fun updatePlayerName(player: Int, name: String) {
//        if (player == 1) {
//            player1Name.value = name
//            savedStateHandle["player1Name"] = name
//        } else {
//            player2Name.value = name
//            savedStateHandle["player2Name"] = name
//        }
//    }
//
//    fun updateBoardTheme(theme: String) {
//        boardTheme.value = theme
//        savedStateHandle["boardTheme"] = theme
//    }


}