package com.example.tapatannwv1.model

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.tapatannwv1.R

class GameViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    var board = List(3) { mutableStateListOf<Int?>(null, null, null) }
    private val player1ImageIndex = savedStateHandle.get<Int>("player1ImageIndex") ?: 0
    private val player2ImageIndex = savedStateHandle.get<Int>("player2ImageIndex") ?: 1

    val availablePieces = mutableStateListOf(
        Piece(resourceId = R.drawable.blackchecker),
        Piece(resourceId = R.drawable.redchecker),
        Piece(resourceId = R.drawable.whitechecker),
        Piece(resourceId = R.drawable.yay),
        Piece(resourceId = R.drawable.xiaomimi)
    )

    val player1 = Player(
        1,
        savedStateHandle.get<String>("player1Name") ?: "Player 1",
        availablePieces[player1ImageIndex].resourceId ?: availablePieces[player1ImageIndex].uri.hashCode(),
        0
    )

    val player2 = Player(
        2,
        savedStateHandle.get<String>("player2Name") ?: "Player 2",
        availablePieces[player2ImageIndex].resourceId ?: availablePieces[player2ImageIndex].uri.hashCode(),
        0
    )

    val currentPlayer = mutableStateOf(player1)
    val winningPlayer = mutableStateOf<Player?>(null)
    val stalemate = mutableStateOf<Boolean?>(false)

    private var piecesPlaced = 0
    private val totalPieces = 6
    private var selectedPiece: Pair<Int, Int>? = null

    val player1PiecesMovable = mutableStateListOf(true, true, true) //maybe move?
    val player2PiecesMovable = mutableStateListOf(true, true, true)
    private val moveHistory = mutableListOf<List<Pair<Int, Int>>>()

    fun setPlayerNames(name1: String, name2: String) {
        player1.name.value = name1
        player2.name.value = name2
        savedStateHandle["player1Name"] = name1
        savedStateHandle["player2Name"] = name2

        currentPlayer.value = player1
    }

    fun setPlayerImage(image1: Int, image2: Int) { //TODO DO NOT ALLOW THE SAME IMAGE TO BE SELECTED BY BOTH PLAYERS
        player1.pieceImage.value = availablePieces[image1].resourceId ?: availablePieces[image1].uri.hashCode()
        player2.pieceImage.value = availablePieces[image2].resourceId ?: availablePieces[image2].uri.hashCode()

        savedStateHandle["player1ImageIndex"] = image1
        savedStateHandle["player2ImageIndex"] = image2
    }



    fun addCustomImage(uri: Uri) { //TODO NOT WORKING
        availablePieces.add(Piece(resourceId = uri.compareTo(uri)))

        availablePieces.forEachIndexed { index, piece ->
            Log.d("AvailablePieces", "Piece at $index: ${piece.resourceId}")
            Log.d("AvailablePieces", ":) Piece at $index: ${piece.uri.hashCode() ?: piece.resourceId}")
        }

        Log.d("ImageCheck", "${availablePieces.size}")
    }

    fun onCellClicked( //There is some issue possibly here with the logic to swap player names if left default
        row: Int,
        col: Int
    ) {
        //Drop Phase - players have to place 3 pieces each
        if (piecesPlaced < totalPieces) {
            if (board[row][col] == null) {
                board[row][col] = if (currentPlayer.component1().id == player1.id) player1.pieceImage.value else player2.pieceImage.value

                updatePieceMovability()
                piecesPlaced++

                if (checkWin()) {
                    winningPlayer.value = currentPlayer.value
                } else if (checkStalemate()) {
                    //TODO implement something I guess
                } else {
                    swapPlayers()
                }
            }
        } else {
            //Movement Phase - players can move already placed pieces to adjacent empty cells
            if (selectedPiece == null) {
                // Select a piece to move
                if (board[row][col] != null && isCurrentPlayerPiece(row, col)) {
                    selectedPiece = Pair(row, col)
                    Log.d("GameViewModel", "Piece selected at: ($row, $col)")
                }
            } else {
                Log.d("GameViewModel", "Entered other Phase")
                val (selectedRow, selectedCol) = selectedPiece!!
                if (board[row][col] == null && isValidMove(
                        selectedRow,
                        selectedCol,
                        row,
                        col
                    )
                ) {
                    val currentMove = listOf(Pair(selectedRow, selectedCol), Pair(row, col))
                    moveHistory.add(currentMove)

                    board[row][col] = board[selectedRow][selectedCol]
                    board[selectedRow][selectedCol] = null
                    selectedPiece = null

                    if (checkWin()) {
                        winningPlayer.value = currentPlayer.value
                        return
                    } else if (checkStalemate()) {

                        Log.d("GameViewModel", "Stalemate detected")
                        return
                    } else {
                        swapPlayers()
                    }
                } else {
                    Log.d("GameViewModel", "Invalid move. Must be an adjacent empty cell.")
                    selectedPiece = null // Deselect the piece if the move is invalid
                }
            }
        }
    }

    private fun isCurrentPlayerPiece(row: Int, col: Int): Boolean {
        // Check to see if the selected cell has the current players piece
        val currentPlayerPiece = currentPlayer.value.pieceImage
        return board[row][col] == currentPlayerPiece.value
    }

    private fun isValidMove(
        selectedRow: Int,
        selectedCol: Int,
        targetRow: Int,
        targetCol: Int
    ): Boolean {
        val adjacentCells = getAdjacentCells(selectedRow, selectedCol)
        return adjacentCells.contains(Pair(targetRow, targetCol))
    }

    //returns a list of board[row][col] that are adjacent to the cell selected
    private fun getAdjacentCells(row: Int, col: Int): List<Pair<Int, Int>> { //todo improve this
        return listOfNotNull(
            if (row > 0) Pair(row - 1, col) else null, // Up
            if (row < 2) Pair(row + 1, col) else null, // Down
            if (col > 0) Pair(row, col - 1) else null, // Left
            if (col < 2) Pair(row, col + 1) else null,  // Right
            if (row > 0 && col > 0) Pair(row - 1, col - 1) else null, // Top-left diagonal
            if (row > 0 && col < 2) Pair(row - 1, col + 1) else null, // Top-right diagonal
            if (row < 2 && col > 0) Pair(row + 1, col - 1) else null, // Bottom-left diagonal
            if (row < 2 && col < 2) Pair(row + 1, col + 1) else null  // Bottom-right diagonal
        )
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

        val currentPlayerPiece =
            if (currentPlayer.value == player1) player1.pieceImage else player2.pieceImage

        //TODO
        ///ADDITIONAL FEATURE
        ///HIGHLIGHT WINNING SECTION?

        for (combination in winningCombinations) {
            if (combination.all { flatBoard[it] == currentPlayerPiece.value }) {
                return true // A winner has been detected
            }
        }
        return false //No winner, game continues
    }

    private fun checkStalemate(): Boolean {
        if (moveHistory.size < 6) {
            return false // Not enough moves to check for repetition
        }
        //Getting the last three moves
        val lastThreeMoves = moveHistory.takeLast(3)
        Log.d("LastThreeMoves", "$lastThreeMoves")
        // Check if the last three moves are the same as the previous three moves
        val previousThreeMoves = moveHistory.dropLast(3).takeLast(3)
        if (lastThreeMoves == previousThreeMoves) {
            Log.d("LastThreeMoves", "true")
            stalemate.value = true
            return true
        }

        return false
    }

    private fun updatePieceMovability() {
        if (piecesPlaced % 2 == 0) {
            player1PiecesMovable[player1.pieceIndex] = false
            player1.pieceIndex++
        } else {
            player2PiecesMovable[player2.pieceIndex] = false
            player2.pieceIndex++
        }
    }

    private fun swapPlayers() {
        currentPlayer.value = if (currentPlayer.value == player1) player2 else player1
    }


    fun resetGame() {
        for (r in 0..2) {
            for (c in 0..2) {
                board[r][c] = null
            }
        }
        resetStats()
    }

    private fun resetStats() {
        piecesPlaced = 0
        winningPlayer.value = null
        stalemate.value = false
        currentPlayer.value = player1

        player1.pieceIndex = 0
        player2.pieceIndex = 0
        player1PiecesMovable.forEachIndexed { index, _ -> player1PiecesMovable[index] = true }
        player2PiecesMovable.forEachIndexed { index, _ -> player2PiecesMovable[index] = true }
    }

}





