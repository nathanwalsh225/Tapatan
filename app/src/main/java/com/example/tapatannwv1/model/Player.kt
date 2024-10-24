package com.example.tapatannwv1.model

import androidx.compose.runtime.mutableStateOf

class Player(
    val id: Int,
    name: String,
    pieceImage: Int
) {
    var name = mutableStateOf(name)
    var pieceImage = mutableStateOf(pieceImage)
}