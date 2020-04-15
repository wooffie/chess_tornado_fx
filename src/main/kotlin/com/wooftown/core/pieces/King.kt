package com.wooftown.core.pieces

/**
 * King
 * Extends Piece
 * @param color - color of piece
 */
class King(color: PieceColor) : Piece(color) {

    /**
     * @param x - x cords of piece
     * @param y - y cords of piece
     * @return list of possible movies without finding for check
     */
    override fun getPossibleMovies(x: Int, y: Int): List<Pair<Int, Int>> {

        val result = mutableListOf(x to y)
        val board = this.getBoard()!!
        for ((directionX, directionY) in listOf(1 to 1, 1 to 0, 1 to -1, 0 to -1, -1 to -1, -1 to 0, -1 to 1, 0 to 1)) {
            val newX = x + directionX
            val newY = y + directionY
            if (newX in 0..7 && newY in 0..7 && isOpposite(board[newX, newY])) {
                result.add(Pair(newX, newY))
            }
        }

        return result + castling(x, y)
    }

    /**
     * @param x - x cords of piece
     * @param y - y cords of piece
     * @return list of possible movies with castling
     */
    private fun castling(x: Int, y: Int): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        val board = this.getBoard()!!
        val canCastle = if (color == PieceColor.WHITE) {
            x == 7 && y == 4
        } else {
            x == 0 && y == 4
        }
        if (canCastle) {
            if (board[x, 5] == null && board[x, 6] == null && board[x, 7] is Rook) {
                result.add(x to 6)
            }
            if (board[x, 3] == null && board[x, 2] == null && board[x, 1] == null && board[x, 0] is Rook) {
                result.add(x to 2)
            }
        }

        return result
    }

    /**
     * @return string with color and name of piece in format: color_piece
     * use in view for take images from resources
     */
    override fun toString(): String = if (this.color == PieceColor.WHITE) {
        "white_king"
    } else {
        "black_king"
    }
}