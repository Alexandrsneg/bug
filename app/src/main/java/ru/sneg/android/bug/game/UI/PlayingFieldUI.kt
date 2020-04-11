package ru.sneg.android.bug.game.UI

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import ru.sneg.android.bug.game.engine.GameState
import kotlin.random.Random

//отображение игрового поля
class PlayingFieldUI: IElementUI {

    private val takes = mutableListOf<TakeUI>()
    private val bgPaint = Paint().apply { color = Color.DKGRAY }

    var width: Int = 0
    var height: Int = 0

    var k: Int = 0

    init {

        val random = Random(System.currentTimeMillis())
        for (i in 1..100)
            takes.add(TakeUI().apply {
                state = random.nextInt(10)

            })


    }

   /* fun setBugsFour(n: Int) {
        var kol:Int


        for (kol in 1..10) {

            val randome = Random(System.currentTimeMillis())
            if (randome.nextInt(2) == 0)
            {//вертикальный

                var ran: Int = randome.nextInt(69)
                takes[ran].state = 2
                takes[ran + 10].state = 2
                takes[ran + 20].state = 2
                takes[ran + 30].state = 2
            } else {//горизонтальный

                var ranx: Int = randome.nextInt(7)
                var rany: Int = randome.nextInt(10)
                takes[ranx + 10 * rany].state = 2
                takes[ranx + 10 * rany + 1].state = 2
                takes[ranx + 10 * rany + 2].state = 2
                takes[ranx + 10 * rany + 3].state = 2
            }

        }
    }*/
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

     fun onClick(x: Float, y: Float): TakeUI? {
        return takes.firstOrNull{it.x < x && it.x + it.width >= x && it.y < y && it.y + it.width >= y}
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