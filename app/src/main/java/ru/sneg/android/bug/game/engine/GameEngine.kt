package ru.sneg.android.bug.game.engine

import ru.sneg.android.bug.credentials.game.gameOfflineBot.GameOfflineBotFragment
import ru.sneg.android.bug.credentials.game.gameOfflineBot.GameOfflineBotFragment.Companion.different
import ru.sneg.android.bug.credentials.game.gameOfflineBot.ResultBotFragment
import ru.sneg.android.bug.credentials.game.gameOfflinePvp.GameOfflinePvpFragment
import ru.sneg.android.bug.game.gameObjects.Bugs
import java.util.*

//игровой движок на стороне клиента
class GameEngine {

    var player1: IPlayer? = null
    var player2: IPlayer? = null


    //реакции на попадания
    fun onClickGameField(x: Int, y: Int, bug: Bugs) {

        val i: Int = y * 10 + x
        //если нажать на сыгранную клетку в игре с ботом, переход хода не произойдет *******
        if ((bug.takes[i].state == 2 || bug.takes[i].state == 3)) {
            GameOfflineBotFragment.different = false
            return
        } else different = true
        //если нажать на сыгранную клетку в игре с ботом, переход хода не произойдет *******

        if (bug.takes[i].state == 1) {   //bug_part
            bug.takes[i].state = 3      //explode

            if (bug.killCheck(bug.identBug(i))) { // если все элементы жука подбиты
                bug.killedBugSurrounding() // обводка клеток вокруг всех убитых жуков

                //конец игры, все жуки бота убиты!!!
                if (bug.checkSum(bug) == 20) {
                    GameOfflineBotFragment.gameWithBotIsOver = true
                    ResultBotFragment.winnerIs = "Player"
                }
            }
            GameOfflineBotFragment.playerMiss = false
        }

        if (bug.takes[i].state == 0 || bug.takes[i].state == 4) { //undefined
            bug.takes[i].state = 2  //miss
            // смена хода, блокировка первого поля, разблокировка второго поля
            GameOfflinePvpFragment.changeMove = true
            GameOfflineBotFragment.playerMiss = true
        }
    }
}