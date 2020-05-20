package ru.sneg.android.bug.game.engine

import ru.sneg.android.bug.credentials.game.gameOfflineBot.GameOfflineBotFragment
import ru.sneg.android.bug.game.gameObjects.Bugs
import java.lang.Exception
import kotlin.random.Random

class BotPlayer () {
// в конструкторе был netPlayer: NetworkPlayer

    companion object {

        var firstGoodShoot = Pair(0, 0)
        var lastGoodShoot = Pair(0, 0)

        var nextShoot = Pair(0, 0)


        var firstBlood = false

        var botFindAndFinishingBug = false

        var goOnShootLeft = Pair(lastGoodShoot.first - 1, lastGoodShoot.second)
        var goOnShootRight = Pair(lastGoodShoot.first + 1, lastGoodShoot.second)
        var goOnShootUp = Pair(lastGoodShoot.first, lastGoodShoot.second - 1)
        var goOnShootDown = Pair(lastGoodShoot.first, lastGoodShoot.second + 1)

        var tryShootLeft = Pair(firstGoodShoot.first - 1, firstGoodShoot.second)
        var tryShootRight = Pair(firstGoodShoot.first + 1, firstGoodShoot.second)
        var tryShootUp = Pair(firstGoodShoot.first, firstGoodShoot.second - 1)
        var tryShootDown = Pair(firstGoodShoot.first, firstGoodShoot.second + 1)

    }


    fun botNewShot(): Pair<Float, Float> {
        val random = Random(System.nanoTime())
        var x = random.nextInt(0, 10).toFloat()
        var y = random.nextInt(0, 10).toFloat()

        return Pair(x, y)
    }


    fun onClickGameFieldByBot(x: Float, y: Float, bug: Bugs) {

        var x: Int = x.toInt()
        var y: Int = y.toInt()
        var i: Int = y * 10 + x

        //если клетка в которую ткнул бот уже сыграна, рандомно меняем клетку пока не попадем на свободную
        if ((bug.takes[i].state == 2 || bug.takes[i].state == 3))
            do {
                x = Random.nextInt(0,10)
                y = Random.nextInt(0,10)
                i = y*10 + x
            } while ((bug.takes[i].state == 2 || bug.takes[i].state == 3))


        if (bug.takes[i].state == 1) {   //bug_part
            bug.takes[i].state = 3      //explode

            lastGoodShoot = Pair(x, y)

            if (!firstBlood)
                firstGoodShoot = Pair(x, y)

            goOnShootLeft = Pair(lastGoodShoot.first - 1, lastGoodShoot.second)
            goOnShootRight = Pair(lastGoodShoot.first + 1, lastGoodShoot.second)
            goOnShootUp = Pair(lastGoodShoot.first, lastGoodShoot.second - 1)
            goOnShootDown = Pair(lastGoodShoot.first, lastGoodShoot.second + 1)

            tryShootLeft = Pair(firstGoodShoot.first - 1, firstGoodShoot.second)
            tryShootRight = Pair(firstGoodShoot.first + 1, firstGoodShoot.second)
            tryShootUp = Pair(firstGoodShoot.first, firstGoodShoot.second - 1)
            tryShootDown = Pair(firstGoodShoot.first, firstGoodShoot.second + 1)


            nextBotShoot(x,y,i,bug)

            firstBlood = true
            botFindAndFinishingBug = true


            if (bug.killCheck(bug.identBug(i))) { // если все элементы жука подбиты
                bug.killedBugSurrounding() // обводка клеток вокруг всех убитых жуков
                firstBlood = false
                botFindAndFinishingBug = false
            }
            GameOfflineBotFragment.botMiss = false

        }

        if (bug.takes[i].state == 0 || bug.takes[i].state == 4) { //undefined
            bug.takes[i].state = 2  //miss

            // если в режиме добивания и известна ориентация
            if (firstBlood && !bug.killCheck(bug.identBug(firstGoodShoot.second * 10 + firstGoodShoot.first))) {

                if(bug.takes[firstGoodShoot.second * 10 + firstGoodShoot.first -1].state !=2 && bug.takes[firstGoodShoot.second * 10 + firstGoodShoot.first -1].state !=3 )
                    nextShoot = tryShootLeft

                if(bug.takes[firstGoodShoot.second * 10 + firstGoodShoot.first +1].state !=2 && bug.takes[firstGoodShoot.second * 10 + firstGoodShoot.first +1].state !=3 )
                    nextShoot = tryShootRight

                if(bug.takes[firstGoodShoot.second * 10 + firstGoodShoot.first -10].state !=2 && bug.takes[firstGoodShoot.second * 10 + firstGoodShoot.first -10].state !=3 )
                    nextShoot = tryShootUp

                if(bug.takes[firstGoodShoot.second * 10 + firstGoodShoot.first +10].state !=2 && bug.takes[firstGoodShoot.second * 10 + firstGoodShoot.first +10].state !=3 )
                    nextShoot = tryShootDown

                //nextBotShoot(x,y,i,bug)
            }

            // смена хода
            GameOfflineBotFragment.botMiss = true
        }
    }


    fun lastGoodDirection(i: Int, bug: Bugs): String{
        var dir = ""
        try {
            if (bug.takes[i - 1].state == 3)
                dir = "RIGHT"

            if (bug.takes[i + 1].state == 3)
                dir = "LEFT"

            if (bug.takes[i - 10].state == 3)
                dir = "DOWN"

            if (bug.takes[i + 10].state == 3)
                dir = "UP"
            if (!firstBlood)
                dir = "FIRST_BLOOD"
        }
        catch (e: Exception){
            println("lastGoodDirectionException")
        }
        return dir
    }

    fun nextGoodDirection(i: Int, bug: Bugs): String{
        var dir = ""
        try {
            if (bug.takes[i + 1].state != 2 || bug.takes[i + 1].state != 3)
                dir = "RIGHT"

            if (bug.takes[i - 1].state != 2 || bug.takes[i - 1].state != 3 )
                dir = "LEFT"

            if (bug.takes[i + 10].state != 2 || bug.takes[i + 10].state !=3 )
                dir = "DOWN"

            if (bug.takes[i - 10].state != 2 || bug.takes[i - 10].state != 3)
                dir = "UP"

        }
        catch (e: Exception){
            println("lastGoodDirectionException")
        }
        return dir
    }

    fun nextBotShoot(x: Int, y: Int, i: Int, bug: Bugs){

        if (lastGoodDirection(i, bug) == "UP") {
            if (y - 1 > -1) {
                if (bug.takes[i - 10].state != 2) {
                    nextShoot = goOnShootUp
                }
                else {
                    nextShoot = tryShootDown
                }
            }
        }
        if (lastGoodDirection(i, bug) == "DOWN") {
            if (y + 1 < 10) {
                if (bug.takes[i + 10].state != 2) {
                    nextShoot = goOnShootDown
                }
                else {
                   nextShoot = tryShootUp
                }
            }
        }
        if (lastGoodDirection(i, bug) == "RIGHT") {
            if (x + 1 < 11) {
                if (bug.takes[i + 1].state != 2) {
                   nextShoot = goOnShootRight
                }
                if (bug.takes[i].state == 2){
                    nextShoot = tryShootLeft
                }
            }
        }
        if (lastGoodDirection(i, bug) == "LEFT") {
            if (x - 1 > -1) {
                if (bug.takes[i - 1].state != 2) {
                   nextShoot = goOnShootLeft
                }
                else {
                    nextShoot = tryShootRight
                }
            }
        }
        if (lastGoodDirection(i, bug) == "FIRST_BLOOD") {
                    nextMovesFirstBlood(bug)
            }
    }

    fun nextMovesFirstBlood(bug: Bugs) {
        //следующий в лево
        if ((firstGoodShoot.first - 1 > -1))
            if (bug.takes[firstGoodShoot.second * 10 + firstGoodShoot.first - 1].state != 2 && bug.takes[firstGoodShoot.second * 10 + firstGoodShoot.first - 1].state != 3)
                nextShoot = tryShootLeft

        //следующий вверх
        if (firstGoodShoot.second - 1 > -1)
            if (bug.takes[firstGoodShoot.second * 10 + firstGoodShoot.first - 10].state != 2 && bug.takes[firstGoodShoot.second * 10 + firstGoodShoot.first - 10].state != 3 )
                nextShoot = tryShootUp

        //следущий вниз
        if (firstGoodShoot.second + 1 < 9)
            if (bug.takes[firstGoodShoot.second * 10 + firstGoodShoot.first + 10].state != 2 && bug.takes[firstGoodShoot.second * 10 + firstGoodShoot.first + 10].state != 3)
                nextShoot = tryShootDown
        //следующий вправо
        if (firstGoodShoot.first + 1 < 9)
            if (bug.takes[firstGoodShoot.second * 10 + firstGoodShoot.first + 1].state != 2 && bug.takes[firstGoodShoot.second * 10 + firstGoodShoot.first + 1].state != 3)
                nextShoot = tryShootRight
    }



}
