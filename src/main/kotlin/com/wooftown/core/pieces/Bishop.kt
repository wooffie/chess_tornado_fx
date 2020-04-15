package com.wooftown.core.pieces


/**
 * Bishop
 * Extends Piece
 * @param color - color of piece
 */
class Bishop(color: PieceColor) : Piece(color) {

    /**
     * @param x - x cords of piece
     * @param y - y cords of piece
     * @return list of possible movies without finding for check
     */
    override fun getPossibleMovies(x: Int, y: Int): List<Pair<Int, Int>> {
        val result = mutableListOf(x to y)
        val board = this.getBoard()!!
        for ((directionX, directionY) in listOf(Pair(1, 1), Pair(-1, -1), Pair(-1, 1), Pair(1, -1))) {
            var newX = x + directionX
            var newY = y + directionY
            while (newX in 0..7 && newY in 0..7 && isOpposite(board[newX, newY])) {
                result.add(Pair(newX, newY))
                if (board[newX, newY] is Piece) {
                    break
                }
                newX += directionX
                newY += directionY
            }
        }
        return result
    }

    /**
     * @return string with color and name of piece in format: color_piece
     * use in view for take images from resources
     */
    override fun toString(): String = if (this.color == PieceColor.WHITE) {
        "white_bishop"
    } else {
        "black_Bishop"
    }

}