package ru.sneg.android.bug.game.UI

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
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
                state = 0//random.nextInt(3)

            })

        //  for (i in 11..20)
        //   takes[i-1].state=0
        //    setshipsfour(5)

    }

    fun setBugsFour(n: Int) {
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
            // for (i in 1..100)
            //     takes[i].state=1
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

}