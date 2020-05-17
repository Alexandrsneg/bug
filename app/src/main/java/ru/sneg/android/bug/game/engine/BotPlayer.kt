package ru.sneg.android.bug.game.engine

import ru.sneg.android.bug.credentials.game.gameOfflineBot.GameOfflineBotFragment
import ru.sneg.android.bug.game.gameObjects.Bugs
import kotlin.random.Random

class BotPlayer () {
// в конструкторе был netPlayer: NetworkPlayer

    companion object {
        var nextShootX = 5
        var nextShootY = 5
    }


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

        if (bug.takes[i].state == 1){   //bug_part
            bug.takes[i].state = 3      //explode

            if (bug.killCheck(bug.identBug(i))){ // если все элементы жука подбиты
                bug.killedBugSurrounding() // обводка клеток вокруг всех убитых жуков
            }
            GameOfflineBotFragment.botMiss = false
            nextShoot(x,y,i,bug)

        }

        if (bug.takes[i].state == 0 || bug.takes[i].state == 4){ //undefined
            bug.takes[i].state = 2  //miss
            // смена хода
            GameOfflineBotFragment.botMiss = true

        }
    }

    private fun nextShoot(x: Int, y: Int, i: Int, bug: Bugs){

            if (bug.takes[i].state == 3) {
                // если поле вверху UNDEFINED
                if (i !in 0..9) {
                    if (bug.takes[i - 10].state == 0 || bug.takes[i - 10].state == 4){
                        if(y - 1 > -1) {
                            // bug.takes[i - 10].state = 2
                            nextShootX = x
                            nextShootY = y - 1
                        }
                    }
                }
                // если поле снизу UNDEFINED
                if(i !in 90..99) {
                    if (bug.takes[i - 10].state == 0 || bug.takes[i + 10].state == 4) {
                        if(y + 1 < 10) {
                            // bug.takes[i + 10].state = 2
                            nextShootX = x
                            nextShootY = y + 1
                        }
                    }
                }
                // если поле слева UNDEFINED
                val left = listOf<Int>(0,10,20,30,40,50,60,70,80,90)
                if (i !in left) {
                    if (bug.takes[i - 10].state == 0 || bug.takes[i - 1].state == 4) {
                        if(x -1 > -1) {
                            nextShootX = x - 1
                            nextShootY = y
                        }
                    }
                }
                // если поле справа UNDEFINED
                val right = listOf<Int>(9,19,29,39,49,59,69,79,89,99)
                if (i !in right) {
                    if (bug.takes[i - 10].state == 0 || bug.takes[i + 1].state == 4) {
                       // bug.takes[i + 1].state = 2
                        if(x + 1 < 10) {
                            nextShootX = x + 1
                            nextShootY = y
                        }
                    }
                }
            }
    }

}