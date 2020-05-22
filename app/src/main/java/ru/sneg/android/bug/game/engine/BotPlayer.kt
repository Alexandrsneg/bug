package ru.sneg.android.bug.game.engine

import ru.sneg.android.bug.credentials.game.gameOfflineBot.GameOfflineBotFragment
import ru.sneg.android.bug.credentials.game.gameOfflineBot.GameOfflineBotFragment.Companion.gameWithBotIsOver
import ru.sneg.android.bug.credentials.game.gameOfflineBot.ResultBotFragment.Companion.winnerIs
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


    //рандомный выстрел после убийства жука или в первый ход
    fun botNewShot(): Pair<Float, Float> {
        val random = Random(System.nanoTime())
        var x = random.nextInt(0, 10).toFloat()
        var y = random.nextInt(0, 10).toFloat()

        return Pair(x, y)
    }

//******************************* логика ходов бота************************************
    fun onClickGameFieldByBot(x: Float, y: Float, bug: Bugs) {

        var x: Int = x.toInt()
        var y: Int = y.toInt()
        var i: Int = y * 10 + x

        //если клетка в которую ткнул бот уже сыграна, рандомно меняем клетку пока не попадем на свободную

        if ((bug.takes[i].state == 2 || bug.takes[i].state == 3))
            do {
                x = Random.nextInt(0,10)
                y = Random.nextInt(0,10)
                i = y * 10 + x
            } while ((bug.takes[i].state == 2 || bug.takes[i].state == 3))

        //**************если попал в жука********************
        if (bug.takes[i].state == 1) {   //bug_part
            bug.takes[i].state = 3      //explode

            //это последний удачный выстрел
            lastGoodShoot = Pair(x, y)

            //в первое попадание сохраняем значение первого удачного выстрела
            if (!firstBlood)
                firstGoodShoot = Pair(x, y)

            //создаем переменные следующего выстрела
            //используются для продолжения выстрелов после попаданий
            goOnShootLeft = Pair(lastGoodShoot.first - 1, lastGoodShoot.second)
            goOnShootRight = Pair(lastGoodShoot.first + 1, lastGoodShoot.second)
            goOnShootUp = Pair(lastGoodShoot.first, lastGoodShoot.second - 1)
            goOnShootDown = Pair(lastGoodShoot.first, lastGoodShoot.second + 1)

            //используются для пробных выстрелов после промаха пока жук не убит
            tryShootLeft = Pair(firstGoodShoot.first - 1, firstGoodShoot.second)
            tryShootRight = Pair(firstGoodShoot.first + 1, firstGoodShoot.second)
            tryShootUp = Pair(firstGoodShoot.first, firstGoodShoot.second - 1)
            tryShootDown = Pair(firstGoodShoot.first, firstGoodShoot.second + 1)

            //функия с логикой применения вышеописанных переменных обстрела
            nextBotComboShoot(x,y,i,bug)

            //обозначаем что был совершен первый удачный выстрел, который не будет перезаписан пока не умрет жук
            firstBlood = true
            //обозначаем что включен режим добивания
            botFindAndFinishingBug = true

            //если жук убит
            if (bug.killCheck(bug.identBug(i))) { // если все элементы жука подбиты
                bug.killedBugSurrounding() // обводка клеток вокруг всех убитых жуков
                firstBlood = false
                botFindAndFinishingBug = false

                //конец игры, все жуки игрока убиты!!!
                if(bug.checkSum(bug) == 20) {
                    gameWithBotIsOver = true
                    winnerIs = "Bot"
                }
            }
            GameOfflineBotFragment.botMiss = false
        }
        //********************если промахнулся**********************************************

        if (bug.takes[i].state == 0 || bug.takes[i].state == 4) { //undefined
            bug.takes[i].state = 2  //miss

            // если жук ещё не убит (режим добивания)
            if (botFindAndFinishingBug && !bug.killCheck(bug.identBug(firstGoodShoot.second * 10 + firstGoodShoot.first))) {
                //выстрелы вокруг первого удачного выстрела
                tryToFindNextBugPart(bug)
            }
            // смена хода
            GameOfflineBotFragment.botMiss = true
        }
    }


    //логика обстрела неизвестной клетки после промаха
    private fun tryToFindNextBugPart(bug: Bugs) {
        //следующий в лево
        if ((firstGoodShoot.first - 1 > -1))
            if (bug.takes[firstGoodShoot.second * 10 + firstGoodShoot.first - 1].state != 2 && bug.takes[firstGoodShoot.second * 10 + firstGoodShoot.first - 1].state != 3)
                nextShoot = tryShootLeft

        //следующий вверх
        if (firstGoodShoot.second - 1 > -1)
            if (bug.takes[firstGoodShoot.second * 10 + firstGoodShoot.first - 10].state != 2 && bug.takes[firstGoodShoot.second * 10 + firstGoodShoot.first - 10].state != 3 )
                nextShoot = tryShootUp

        //следущий вниз
        if (firstGoodShoot.second + 1 < 10)
            if (bug.takes[firstGoodShoot.second * 10 + firstGoodShoot.first + 10].state != 2 && bug.takes[firstGoodShoot.second * 10 + firstGoodShoot.first + 10].state != 3)
                nextShoot = tryShootDown
        //следующий вправо
        if (firstGoodShoot.first + 1 < 10)
            if (bug.takes[firstGoodShoot.second * 10 + firstGoodShoot.first + 1].state != 2 && bug.takes[firstGoodShoot.second * 10 + firstGoodShoot.first + 1].state != 3)
                nextShoot = tryShootRight
    }

    //логика продолжительного обстрела (если без промахов)
    private fun nextBotComboShoot(x: Int, y: Int, i: Int, bug: Bugs){

        if (lastGoodDirection(x,y,i, bug) == "UP")
            if (y - 1 > -1)
                if (bug.takes[i - 10].state != 2)
                    nextShoot = goOnShootUp

        if (lastGoodDirection(x,y,i, bug) == "DOWN")
            if (y + 1 < 10)
                if (bug.takes[i + 10].state != 2)
                    nextShoot = goOnShootDown


        if (lastGoodDirection(x,y,i, bug) == "RIGHT")
            if (x + 1 < 10)
                if (bug.takes[i + 1].state != 2)
                    nextShoot = goOnShootRight

        if (lastGoodDirection(x,y,i, bug) == "LEFT")
            if (x - 1 > -1)
                if (bug.takes[i - 1].state != 2)
                    nextShoot = goOnShootLeft

        if (lastGoodDirection(x,y,i, bug) == "FIRST_BLOOD")
            tryToFindNextBugPart(bug)
    }

    // определение удачного направления обстрела
    private fun lastGoodDirection(x: Int, y: Int, i: Int, bug: Bugs): String{
        var dir = ""

            if (x - 1 > -1)
                if (bug.takes[i - 1].state == 3)
                    dir = "RIGHT"

            if (x + 1 < 10)
                if (bug.takes[i + 1].state == 3)
                dir = "LEFT"

            if (y - 1 > -1)
                if (bug.takes[i - 10].state == 3)
                dir = "DOWN"

            if (y + 1 < 10)
                if (bug.takes[i + 10].state == 3)
                dir = "UP"

            if (!firstBlood)
                dir = "FIRST_BLOOD"

        return dir
    }
}
