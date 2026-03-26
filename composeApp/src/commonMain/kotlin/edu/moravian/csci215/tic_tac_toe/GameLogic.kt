package edu.moravian.csci215.tic_tac_toe

object GameLogic {

    fun checkGameResult(board: List<String>): String? {
        val winLines = listOf(
            listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8), // Rows
            listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8), // Cols
            listOf(0, 4, 8), listOf(2, 4, 6)                 // Diagonals
        )

        for (line in winLines) {
            if (board[line[0]].isNotEmpty() &&
                board[line[0]] == board[line[1]] &&
                board[line[0]] == board[line[2]]
            ) {
                return board[line[0]]
            }
        }
        if (board.none { it.isEmpty() }) return "Tie"
        return null
    }

    // easy AI, 100% random selection
    fun getEasyAiMove(board: List<String>): Int {
        val emptyIndices = board.indices.filter { board[it].isEmpty() }
        return if (emptyIndices.isNotEmpty()) emptyIndices.random() else -1
    }

    // medium AI, more strategic
    fun getMediumAiMove(board: List<String>, aiPiece: String): Int {
        // trying to find a winning move for itself
        for (i in board.indices) {
            if (board[i].isEmpty()) {
                val testBoard = board.toMutableList()
                testBoard[i] = aiPiece
                if (checkGameResult(testBoard) == aiPiece) return i
            }
        }
        // random
        return getEasyAiMove(board)
    }

    // hard AI, actively looks for where they can block opponent
    fun getHardAiMove(board: List<String>, aiPiece: String): Int {
        // 1. Try to win first
        for (i in board.indices) {
            if (board[i].isEmpty()) {
                val testBoard = board.toMutableList()
                testBoard[i] = aiPiece
                if (checkGameResult(testBoard) == aiPiece) return i
            }
        }

        // 2. Block the opponent
        val humanPiece = if (aiPiece == "Strawberry") "Orange" else "Strawberry"
        for (i in board.indices) {
            if (board[i].isEmpty()) {
                val testBoard = board.toMutableList()
                testBoard[i] = humanPiece
                if (checkGameResult(testBoard) == humanPiece) return i
            }
        }
        return getEasyAiMove(board)
        }
    }