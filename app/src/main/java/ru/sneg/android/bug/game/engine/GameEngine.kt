package ru.sneg.android.bug.game.engine

import java.util.*

//игровой движок на стороне клиента
/*class GameEngine {

    var player1: IPlayer? = null
    var player2: IPlayer? = null


    // Инициализируем начало игры
    private fun onStartGame() {
        // Определяем кто будет первым ходить
        // Случайным образом
            val random = Random(System.currentTimeMillis())
            if (random.nextBoolean()) {
                gameState.firstWay = 1
                gameState.player1.initAsFirst()
                gameState.player2.initAsSecond()
            } else {
                gameState.firstWay = 2
                gameState.player1.initAsSecond()
                gameState.player2.initAsFirst()
            }
        render()
    }


    // Переход хода
    private fun onChangeTurn() {
        if (gameState.player1.action) {
            gameState.player1.action = false
            gameState.player2.action = true
        } else if (gameState.player2.action) {
            gameState.player2.action = false
            gameState.player1.action = true
        }
    }
}*/