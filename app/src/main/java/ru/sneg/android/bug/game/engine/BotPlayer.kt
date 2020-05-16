package ru.sneg.android.bug.game.engine

import ru.sneg.android.bug.credentials.game.gameOfflineBot.GameOfflineBotFragment
import ru.sneg.android.bug.game.gameObjects.Bugs
import kotlin.random.Random

class BotPlayer () {
// в конструкторе был netPlayer: NetworkPlayer


     fun botMove(): Pair<Float,Float> {
        val random = Random(System.nanoTime())
       var x = random.nextInt(1,11).toFloat()
        var y = random.nextInt(1,11).toFloat()

        return Pair(x,y)
    }

    fun onClickGameFieldByBot(x: Float, y: Float, bug: Bugs) {


        val x: Int = x.toInt()
        val y: Int = y.toInt()

        val i: Int = y * 10 + x

        if (bug.takes[y * 10 + x].state == 1){   //bug_part
            bug.takes[y * 10 + x].state = 3      //explode

            if (bug.killCheck(bug.identBug(i))){ // если все элементы жука подбиты
                bug.killedBugSurrounding() // обводка клеток вокруг всех убитых жуков
            }
            GameOfflineBotFragment.botMiss = false
        }

        if (bug.takes[y * 10 + x].state == 0 || bug.takes[y * 10 + x].state == 4){ //undefined
            bug.takes[y * 10 + x].state = 2  //miss
            // смена хода, блокировка первого поля, разблокировка второго поля
            GameOfflineBotFragment.botMiss = true

        }
    }


}