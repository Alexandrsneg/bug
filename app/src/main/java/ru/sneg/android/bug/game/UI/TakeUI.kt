package ru.sneg.android.bug.game.UI

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

//выбор игрока
class TakeUI : IElementUI {

    companion object {
        const val STATE_UNDEFINED = 0
        const val STATE_CROSS = 1
        const val STATE_ZERO = 2

        val paintRed = Paint().apply {
            color = Color.RED
            this.strokeWidth = 2f
        }
        val paintBlue = Paint().apply { color = Color.BLUE }
        val paintYellow = Paint().apply { color = Color.YELLOW }
    }

    var x: Int = 0
    var y: Int = 0

    var width: Int = 0
    var height: Int = 0

    var state: Int = STATE_UNDEFINED


    override fun render(canvas: Canvas) {

        when (state) {
            STATE_CROSS -> renderCross(canvas)
            STATE_ZERO -> renderZero(canvas)
            STATE_UNDEFINED -> renderUnder(canvas)
        }
    }


    private fun renderUnder(canvas: Canvas) {
        val x = x.toFloat()
        val y = y.toFloat()
        val w = width.toFloat()
        val h = height.toFloat()


        canvas.drawLine(x, y, x, y + w, paintRed)
        canvas.drawLine(x, y, x + h, y, paintRed)
        canvas.drawLine(x + h, y, x + h, y + w, paintRed)
        canvas.drawLine(x + h, y + w, x, y + w, paintRed)
        //canvas.drawText("x",x,y, paintRed)

    }


    private fun renderCross(canvas: Canvas) {

        val x = x.toFloat()
        val y = y.toFloat()
        val w = width.toFloat()
        val h = height.toFloat()

        canvas.drawLine(x, y, x + w, y + h, paintRed)
        canvas.drawLine(x + w, y, x, y + h, paintRed)
    }

    private fun renderZero(canvas: Canvas) {

        val x = x.toFloat()
        val y = y.toFloat()
        val w = width.toFloat()
        val h = height.toFloat()
        val hw = w * 0.5f
        val cx = x + hw
        val cy = y + h * 0.5f

        canvas.drawCircle(cx, cy, hw, paintBlue)
        canvas.drawCircle(cx, cy, hw * 0.9f, paintYellow)
    }
}


