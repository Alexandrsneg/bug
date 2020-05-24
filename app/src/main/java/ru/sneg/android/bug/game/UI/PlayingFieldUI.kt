package ru.sneg.android.bug.game.UI

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import ru.sneg.android.bug.game.engine.GameState
import ru.sneg.android.bug.game.gameObjects.Bugs
import ru.sneg.android.bug.game.engine.BugsPlacingEngine
import ru.sneg.android.bug.game.engine.GameEngine
import ru.sneg.android.bug.game.gameObjects.Const
import ru.sneg.android.bug.game.gameViews.GameBugPlacementSecondPlayerView
import ru.sneg.android.bug.game.gameViews.GameBugPlacementView

//отображение игрового поля
class PlayingFieldUI: IElementUI {

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

//*****************ручная расстановка жуков****************************************************
    //обработчик нажатия на клетку поля
    //логика возможности раастановки жуков по полю, установка в зависимости от расположения
    fun onClickFieldBugPlacing(x: Float, y: Float, bug: Bugs) {

    val x: Int = (x / (width / 10)).toInt()
    val y: Int = (y / (height / 10)).toInt()

    BugsPlacingEngine().onClickFieldBugPlacing(x, y, bug)
}

//**************************выстрелы по полям***********************************************
    //отрисовки нажатий на игоровое поле
    fun onClickGameField(x: Float, y: Float, bug: Bugs) {

    val x: Int = (x / (width / 10)).toInt()
    val y: Int = (y / (height / 10)).toInt()

    GameEngine().onClickGameField(x, y, bug)
}


    //отрисовка поля расстановки жуков
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
    //отрисовка поля боя
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

