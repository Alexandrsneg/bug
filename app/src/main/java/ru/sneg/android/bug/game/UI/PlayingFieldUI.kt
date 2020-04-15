package ru.sneg.android.bug.game.UI

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import ru.sneg.android.bug.game.engine.GameState
import ru.sneg.android.bug.game.gameObjects.Const
import kotlin.random.Random

//отображение игрового поля
class PlayingFieldUI: IElementUI {

    private val takes = mutableListOf<TakeUI>() //список возможных выборов пользователя
    private val bgPaint = Paint().apply { color = Color.DKGRAY }

    var width: Int = 0
    var height: Int = 0



    init {
        //заполнение игрового поля пустыми клетками (STATE_UNDEFINED)
        for (index in 0..99)
            takes.add(TakeUI(index).apply {
                state = 0
            })
    }

    //обработчик нажатия на клетку поля
    fun onClickField(x: Float, y: Float){
        val x : Int = (x/(width/10)).toInt()
        val y : Int = (y/(height/10)).toInt()

        if (takes[y*10+x].state==1)  //bug_part
              takes[y*10+x].state=3   //explode
        else  takes[y*10+x].state=2 //miss
    }

    fun fourBugPlacing () {

        for (i in 1..1) {
            val random = 31 + Math.random() * 70
            //возможные вертикальные расстановки 4 палубника
            var i: Int = random.toInt()
            takes[i].state = 1
            takes[i - 10].state = 1
            takes[i - 20].state = 1
            takes[i - 30].state = 1
        }
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


    //отображение данных присланных с сервера
    fun setGameState(state: GameState) {

        val game = state.game.toTypedArray()
        for (i in 0 until 99)
            takes.get(i).state = when (game[i]) {
                Const.SELECT_TYPE_SHIP_PART-> TakeUI.STATE_BUG_PART
                Const.SELECT_TYPE_MISS -> TakeUI.STATE_MISS
                else -> TakeUI.STATE_UNDEFINED
            }

        if (state.winner != null)
            println("WIN!")
    }
}