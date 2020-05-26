package ru.sneg.android.bug.game.engine

import ru.sneg.android.bug.credentials.game.gameOfflineBot.GameOfflineBotFragment
import ru.sneg.android.bug.credentials.game.gameOfflineBot.ResultBotFragment
import ru.sneg.android.bug.credentials.game.gameOfflinePvp.GameOfflinePvpFragment
import ru.sneg.android.bug.game.engine.players.BotPlayer
import ru.sneg.android.bug.game.engine.players.BotPlayer.Companion.differentCell
import ru.sneg.android.bug.game.engine.players.BotPlayer.Companion.playerMiss
import ru.sneg.android.bug.game.gameObjects.Bugs
import java.util.*

//игровой движок на стороне клиента
class GameEngine {

 var player1 : IPlayer? = null
    var player2 : IPlayer? = null





    //реакции на попадания
    fun onClickGameField(x: Int, y: Int, bug: Bugs) {
        //если сетевой, то

        val i: Int = y * 10 + x
        //если нажать на сыгранную клетку в игре с ботом, переход хода не произойдет *******
        if ((bug.takes[i].state == 2 || bug.takes[i].state == 3)) {
            differentCell = false
            return
        } else differentCell = true
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
            playerMiss = false
        }

        if (bug.takes[i].state == 0 || bug.takes[i].state == 4) { //undefined
            bug.takes[i].state = 2  //miss
            // смена хода, блокировка первого поля, разблокировка второго поля

            GameOfflinePvpFragment.changeMove = true
            playerMiss = true
        }
    }


}