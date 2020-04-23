package com.wooftown.gui

import com.wooftown.controll.Controller
import com.wooftown.core.pieces.*
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.BorderPane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.text.Font
import tornadofx.*


class MainView : View("TornadoChess") {

    override val root = BorderPane()

    private val desk = DeskGUI()

    private val controller = Controller(desk)

    private var statusText = text("")

    init {
        currentStage!!.icons.add(Image("file:src\\main\\resources\\icon.png"))

        with(root) {
            top {
                vbox {
                    menubar {
                        menu("Game") {
                            item("Restart").action {
                                restartGame()
                            }
                            separator()
                            item("Exit").action {
                                this@MainView.close()
                            }
                        }
                    }
                    borderpane {
                        center {
                            statusText = text("White's turn") {
                                fill = Color.BLACK
                                font = Font(20.0)
                            }
                        }
                    }
                }
            }
            center {
                gridpane {
                    val setUpCells = MutableList(8) { MutableList(8) { Rectangle() } }
                    val setUpImages = MutableList(8) { MutableList(8) { ImageView() } }
                    for (row in 0..7) {
                        row {
                            for (column in 0..7) {
                                stackpane {
                                    val rectangle = rectangle {
                                        fill = if ((row + column) % 2 == 0) {
                                            Color.rgb(240, 217, 181)
                                        } else {
                                            Color.rgb(181, 136, 99)
                                        }
                                        width = 100.0
                                        height = 100.0
                                    }
                                    val image = imageview {
                                        image = null
                                    }
                                    setUpCells[row][column] = rectangle
                                    setUpImages[row][column] = image
                                    setOnMouseClicked {
                                        controller.handle(row, column)
                                        updateStatus()
                                    }
                                }
                            }
                        }
                    }
                    desk.setUp(setUpCells, setUpImages)
                }
            }
        }

        spawnAllPieces()

    }

    private fun updateStatus() {
        statusText.apply {
            text = if (controller.getTurn() == PieceColor.WHITE) {
                "White's turn"
            } else {
                "Black's turn"
            }
        }
        if (desk.checkLooser() != null) {
            statusText.apply {
                text = if (desk.checkLooser()!!.opposite() == PieceColor.WHITE) {
                    "White win"
                } else {
                    "Black win"
                }
            }
            WinnerDialog(desk.checkLooser()!!.opposite()).showAndWait()
        }
    }

    private fun restartGame() {
        controller.clear()
        spawnAllPieces()
    }


    private fun spawnAllPieces() {
        with(desk) {
            for (column in 0..7) {
                spawnPiece(Pawn(PieceColor.BLACK), 1, column)
                spawnPiece(Pawn(PieceColor.WHITE), 6, column)
            }
            spawnPiece(Rook(PieceColor.WHITE), 7, 0)
            spawnPiece(Rook(PieceColor.WHITE), 7, 7)
            spawnPiece(Rook(PieceColor.BLACK), 0, 0)
            spawnPiece(Rook(PieceColor.BLACK), 0, 7)
            spawnPiece(Queen(PieceColor.WHITE), 7, 3)
            spawnPiece(Queen(PieceColor.BLACK), 0, 3)
            spawnPiece(King(PieceColor.BLACK), 0, 4)
            spawnPiece(King(PieceColor.WHITE), 7, 4)
            spawnPiece(Knight(PieceColor.WHITE), 7, 1)
            spawnPiece(Knight(PieceColor.WHITE), 7, 6)
            spawnPiece(Knight(PieceColor.BLACK), 0, 1)
            spawnPiece(Knight(PieceColor.BLACK), 0, 6)
            spawnPiece(Bishop(PieceColor.BLACK), 0, 2)
            spawnPiece(Bishop(PieceColor.BLACK), 0, 5)
            spawnPiece(Bishop(PieceColor.WHITE), 7, 5)
            spawnPiece(Bishop(PieceColor.WHITE), 7, 2)
        }
    }


}


