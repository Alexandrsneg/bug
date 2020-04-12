package ru.sneg.android.bug.game.UI

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import ru.sneg.android.bug.game.engine.GameState
import kotlin.random.Random

//отображение игрового поля
class PlayingFieldUI: IElementUI {

    private val takes = mutableListOf<TakeUI>() //список возможных выборов пользователя
    private val bgPaint = Paint().apply { color = Color.DKGRAY }

    var width: Int = 0
    var height: Int = 0



    init {
        //заполнение игрового поля пустыми клетками (STATE_UNDEFINED)
        for (i in 1..100)
            takes.add(TakeUI().apply {
                state = 0
            })
    }

    override fun render(canvas: Canvas) {

        canvas.drawRect(Rect(0, 0, width, height), bgPaint)

        var row = 0
        var col = 0
        val itemWidth = width / 10
        val itemHeight = height / 10

        for (take in takes) {

            take.x = col * itemWidth
            take.y = row * itemHeight

            take.width = itemWidth
            take.height = itemHeight

            take.render(canvas)

            if (++col == 10) {
                col = 0
                if (++row == 10)
                    return
            }
        }
    }

    //определяет по тычку на какую клетку попали
     fun onClick(x: Float, y: Float): TakeUI? {
        //лямбда проверяющая входит ли нажатая область в опрееленные в Тэйках клетки
        return takes.firstOrNull{it.x < x && it.x + it.width >= x && it.y < y && it.y + it.height >= y}
    }

   /* fun setGameState(state: GameState){
        val game = state.game.toTypedArray()
        for(i in 0 until 9){
            takes.get(i).state = when (game[i]){
                0 -> 1

            }
        }
    }*/
}