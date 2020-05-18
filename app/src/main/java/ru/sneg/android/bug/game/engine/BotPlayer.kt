package ru.sneg.android.bug.game.engine

import ru.sneg.android.bug.credentials.game.gameOfflineBot.GameOfflineBotFragment
import ru.sneg.android.bug.game.gameObjects.Bugs
import kotlin.random.Random

class BotPlayer () {
// в конструкторе был netPlayer: NetworkPlayer


        var firstGoodShoot = 0

        var nextShootX = 0
        var nextShootY = 0


     fun botNewShot(): Pair<Float,Float> {
        val random = Random(System.nanoTime())
       var x = random.nextInt(0,10).toFloat()
        var y = random.nextInt(0,10).toFloat()


        return Pair(x,y)
    }



    fun onClickGameFieldByBot(x: Float, y: Float, bug: Bugs) {

        val x: Int = x.toInt()
        val y: Int = y.toInt()

        var i: Int = y * 10 + x

        //если клетка в которую ткнул бот уже сыграна, рандомно меняем клетку пока не попадем на свободную
        if ((bug.takes[i].state == 2 || bug.takes[i].state == 3))
            do {
                i = Random(System.nanoTime()).nextInt(0, 100)
            }
                while ((bug.takes[i].state == 2 || bug.takes[i].state == 3))


        if (bug.takes[i].state == 1){   //bug_part
            bug.takes[i].state = 3      //explode

            firstGoodShoot = i

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

                // если поле сверху UNDEFINED, BUGSURROUNDIG или BUGPART
                upShoot(x,y,i,bug)

                // если поле снизу UNDEFINED, BUGSURROUNDIG или BUGPART
                downShoot(x,y,i,bug)

                // если поле сплева UNDEFINED, BUGSURROUNDIG или BUGPART
                leftShoot(x,y,i,bug)

                // если поле справа UNDEFINED, BUGSURROUNDIG или BUGPART
                rightShoot(x,y,i,bug)

    }

    fun upShoot(x: Int, y: Int, i: Int, bug: Bugs){
        if (i !in 0..9) {
            if (bug.takes[i - 10].state == 0 || bug.takes[i - 10].state == 4 || bug.takes[i - 10].state == 1 ){
                if(y - 1 > -1) {
                    // bug.takes[i - 10].state = 2
                    nextShootX = x
                    nextShootY = y - 1
                }
            }
        }
    }

    fun downShoot(x: Int, y: Int, i: Int, bug: Bugs) {
        if (i !in 90..99 && i != 0) {
            if (bug.takes[i + 10].state == 0 || bug.takes[i + 10].state == 4 || bug.takes[i + 10].state == 1) {
                if (y + 1 < 10) {
                    // bug.takes[i + 10].state = 2
                    nextShootX = x
                    nextShootY = y + 1
                }
            }
        }
    }

    fun leftShoot(x: Int, y: Int, i: Int, bug: Bugs) {
        val left = listOf<Int>(0, 10, 20, 30, 40, 50, 60, 70, 80, 90)
        if (i !in left) {
            if (bug.takes[i - 1].state == 0 || bug.takes[i - 1].state == 4 || bug.takes[i - 1].state == 1) {
                if (x - 1 > -1) {
                    nextShootX = x - 1
                    nextShootY = y
                }
            }
        }
    }

        fun rightShoot(x: Int, y: Int, i: Int, bug: Bugs) {
            val right = listOf<Int>(9,19,29,39,49,59,69,79,89,99)
            if (i !in right) {
                if (bug.takes[i + 1].state == 0 || bug.takes[i + 1].state == 4 || bug.takes[i + 1].state == 1) {
                    // bug.takes[i + 1].state = 2
                    if(x + 1 < 10) {
                        nextShootX = x + 1
                        nextShootY = y
                    }
                }
            }
        }


}