package ru.sneg.android.bug.game.UI

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import ru.sneg.android.bug.credentials.game.gameOfflineBot.GameOfflineBotFragment
import ru.sneg.android.bug.credentials.game.gameOfflineBot.GameOfflineBotFragment.Companion.different
import ru.sneg.android.bug.credentials.game.gameOfflinePvp.GameOfflinePvpFragment
import ru.sneg.android.bug.game.engine.GameState
import ru.sneg.android.bug.game.gameObjects.Bugs
import ru.sneg.android.bug.game.gameObjects.BugsPlacing
import ru.sneg.android.bug.game.gameObjects.Const
import ru.sneg.android.bug.game.gameViews.GameBugPlacementSecondPlayerView
import ru.sneg.android.bug.game.gameViews.GameBugPlacementView

//отображение игрового поля
class PlayingFieldUI: IElementUI {

    companion object{
        var bot = false
    }

    private val bgPaint = Paint().apply { color = Color.DKGRAY }

    var width: Int = 0
    var height: Int = 0

    init {
        //заполнение игрового поля пустыми клетками (STATE_UNDEFINED)
        for (index in 0..99)
            GameBugPlacementView.firstPlayerBugs.takes.add(TakeUI(index).apply {
                state = 0
            })
        for (index in 0..99)
            GameBugPlacementSecondPlayerView.secondPlayerBugs.takes.add(TakeUI(index).apply {
                state = 0
            })
    }

    var bugsPlacing = BugsPlacing()

//*****************автоматическая расстановка жуков****************************************************
fun autoPlacing(bug: Bugs){

    do{bugsPlacing.autoPlacing(4, bug, bug.listBugFour)}
        while (bug.checkSum(bug) < 4)

    do{bugsPlacing.autoPlacing(3, bug, bug.listBugThreeFirst)}
        while (bug.checkSum(bug) < 7)

    do{bugsPlacing.autoPlacing(3,bug,bug.listBugThreeSecond)}
        while (bug.checkSum(bug) < 10)

    do{bugsPlacing.autoPlacing(2,bug,bug.listBugTwoFirst)}
        while (bug.checkSum(bug) < 12)

    do{bugsPlacing.autoPlacing(2,bug,bug.listBugTwoSecond)}
        while (bug.checkSum(bug) < 14)

    do{bugsPlacing.autoPlacing(2,bug,bug.listBugTwoThird)}
        while (bug.checkSum(bug) < 16)

    do{bugsPlacing.autoPlacing(1,bug,bug.listBugOneFirst)}
        while (bug.checkSum(bug) < 17)

    do{bugsPlacing.autoPlacing(1,bug,bug.listBugOneSecond)}
        while (bug.checkSum(bug) < 18)

    do{bugsPlacing.autoPlacing(1,bug,bug.listBugOneThird)}
        while (bug.checkSum(bug) < 19)

    do{bugsPlacing.autoPlacing(1,bug,bug.listBugOneFourth)}
        while (bug.checkSum(bug) < 20)

    bug.bugsRemaining = 0
}

//*****************ручная расстановка жуков****************************************************
    //обработчик нажатия на клетку поля
    //логика возможности раастановки жуков по полю, установка в зависимости от расположения(офлайн)
    fun onClickFieldBugPlacing(x: Float, y: Float, bug: Bugs) {

    val x: Int = (x / (width / 10)).toInt()
    val y: Int = (y / (height / 10)).toInt()

    val i: Int = y * 10 + x

    if (bug.fourPartBug > 0) {
        if (i in 0..99) {
            bugsPlacing.placingEngine(4, i, bug, bug.listBugFour)
        }
    } else if (bug.fourPartBug == 0 && bug.threePartBug > 0) {
        if (i in 0..99) {
            when (bug.threePartBug) {
                2 -> { bugsPlacing.placingEngine(3, i,  bug, bug.listBugThreeFirst) }
                1 -> { bugsPlacing.placingEngine(3, i,  bug, bug.listBugThreeSecond) }
            }
        }
    } else if (bug.threePartBug == 0 && bug.twoPartBug > 0) {
        if (i in 0..99) {
            when (bug.twoPartBug) {
                3 -> { bugsPlacing.placingEngine(2, i,  bug, bug.listBugTwoFirst) }
                2 -> { bugsPlacing.placingEngine(2, i,  bug, bug.listBugTwoSecond) }
                1 -> { bugsPlacing.placingEngine(2, i,  bug, bug.listBugTwoThird) }
            }
        }
    } else if (bug.twoPartBug == 0 && bug.onePartBug > 0) {
        when (bug.onePartBug) {
            4 -> { bugsPlacing.placingEngine(1, i, bug, bug.listBugOneFirst) }
            3 -> { bugsPlacing.placingEngine(1, i, bug, bug.listBugOneSecond) }
            2 -> { bugsPlacing.placingEngine(1, i, bug, bug.listBugOneThird) }
            1 -> { bugsPlacing.placingEngine(1, i, bug, bug.listBugOneFourth) }
        }
    }
}

//**************************конец расстановки жуков*****************************************

//**************************выстрелы по полям***********************************************
    //отрисовки нажатий на игоровое
    fun onClickGameField(x: Float, y: Float, bug: Bugs) {


        val x: Int = (x / (width / 10)).toInt()
        val y: Int = (y / (height / 10)).toInt()

        val i: Int = y * 10 + x

        //если нажать на сыгранную клетку в игре с ботом, переход хода не произойдет *******
        if ((bug.takes[i].state == 2 || bug.takes[i].state == 3)) {
            different = false
            return
        }
        else different = true
        //если нажать на сыгранную клетку в игре с ботом, переход хода не произойдет *******

        if (bug.takes[i].state == 1){   //bug_part
            bug.takes[i].state = 3      //explode

            if (bug.killCheck(bug.identBug(i))){ // если все элементы жука подбиты
            bug.killedBugSurrounding() // обводка клеток вокруг всех убитых жуков
            }
            GameOfflineBotFragment.playerMiss = false
        }

    if (bug.takes[i].state == 0 || bug.takes[i].state == 4){ //undefined
        bug.takes[i].state = 2  //miss
        // смена хода, блокировка первого поля, разблокировка второго поля
        GameOfflinePvpFragment.changeMove = true
        GameOfflineBotFragment.playerMiss = true

        }
    }

//**************************выстрелы по полям***********************************************

        //отрисовка поля расстановки первого игрока (офлайн)
        override fun renderGameField(canvas: Canvas, bug: Bugs) {

            canvas.drawRect(Rect(0, 0, width, height), bgPaint)

            var row = 0
            var col = 0
            val itemWidth = width / 10
            val itemHeight = height / 10

            for (take in bug.takes) {

                take.x = col * itemWidth
                take.y = row * itemHeight

                take.width = itemWidth
                take.height = itemHeight

                take.renderGameField(canvas, bug)

                if (++col == 10) {
                    col = 0
                    if (++row == 10)
                        return
                }
            }
        }

    override fun renderWithoutBugsParts(canvas: Canvas, bug: Bugs) {

        canvas.drawRect(Rect(0, 0, width, height), bgPaint)

        var row = 0
        var col = 0
        val itemWidth = width / 10
        val itemHeight = height / 10

        for (take in bug.takes) {

            take.x = col * itemWidth
            take.y = row * itemHeight

            take.width = itemWidth
            take.height = itemHeight

            take.renderWithoutBugsParts(canvas, bug)

            if (++col == 10) {
                col = 0
                if (++row == 10)
                    return
            }
        }
    }

    //определяет по тычку на какую клетку попали
        fun onClick(x: Float, y: Float, bug: Bugs): TakeUI? {
            //лямбда проверяющая входит ли нажатая область в опрееленные в Тэйках клетки
            return bug.takes.firstOrNull { it.x < x && it.x + it.width >= x && it.y < y && it.y + it.height >= y }
        }

        //отображение данных присланных с сервера
        fun setGameState(state: GameState, bug: Bugs) {

            val game = state.game.toTypedArray()
            for (i in 0 until 99)
                bug.takes[i].state = when (game[i]) {
                    Const.SELECT_TYPE_SHIP_PART -> TakeUI.STATE_BUG_PART
                    Const.SELECT_TYPE_MISS -> TakeUI.STATE_MISS
                    Const.SELECT_TYPE_EXPLODE -> TakeUI.STATE_EXPLODE
                    else -> TakeUI.STATE_UNDEFINED
                }

            if (state.winner != null)
                println("WIN!")
        }

}

