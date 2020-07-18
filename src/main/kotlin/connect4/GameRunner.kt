package connect4

enum class Modes { DEFAULT, PVP, AIONLY }

class GameRunner(mode: Modes, boardCols: Int, boardRows: Int, lengthToWin: Int = 4, popOut: Boolean = false) {
    private val board = GameState(boardCols, boardRows, lengthToWin, popOut)
    private val player1: AI
    private val player2: AI

    init {
        when (mode) {
            Modes.DEFAULT -> {
                player1 = PlayerAgent()
                player2 = AIAgent()
            }
            Modes.PVP -> {
                player1 = PlayerAgent()
                player2 = PlayerAgent()
            }
            Modes.AIONLY -> {
                player1 = AIAgent()
                player2 = AIAgent()
            }
        }
    }

    fun run(): Players {
        var turn = 1

        while (true) {
            val move = when (turn % 2) {
                1 -> {
                    println("Player 1's Turn")
                    player1.getMove()
                }
                0 -> {
                    println("Player 2's Turn")
                    player2.getMove()
                }
                else -> throw SomethingWentWrong("Modulo 2 should only give 1 or 0")
            }
            try {
                when (move.moveType) {
                    MoveType.DROP -> board.drop(move.columnIndex)
                    MoveType.POP -> board.pop(move.columnIndex)
                }
            } catch (e: InvalidMoveException) {
                println("Invalid move: ${e.message}")
            }
            board.displayBoard()
            println("")
            val (hasWinner, winner) = board.checkWinner()
            if (hasWinner) return winner
            turn = turn % 2 + 1
        }
    }
}