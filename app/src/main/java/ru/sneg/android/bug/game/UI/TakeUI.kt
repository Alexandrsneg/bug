package ru.sneg.android.bug.game.UI

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import ru.sneg.android.bug.game.gameViews.GameBugPlacementView

//выбор игрока
open class TakeUI(
    val index: Int
) : IElementUI {

    companion object {
        const val STATE_UNDEFINED = 0
        const val STATE_BUG_PART = 1 // клетка жука
        const val STATE_MISS = 2 // промах (клетка пуста)
        const val STATE_EXPLODE = 3 // ранение
        const val STATE_NOTHING = 4


        //определение цветов для отрисовок
        val paintWhite = Paint().apply { color = Color.WHITE
            this.strokeWidth = 4f
        }
        val paintGreen = Paint().apply { color = Color.GREEN }
        val paintRed = Paint().apply { color = Color.RED}
        val paintBlack = Paint().apply { color = Color.BLACK}
        val paintYellow = Paint().apply { color = Color.YELLOW}

    }

    var x: Int = 0
    var y: Int = 0

    var width: Int = 0
    var height: Int = 0

    var state: Int = STATE_UNDEFINED


    override fun render(canvas: Canvas) {

        renderField(canvas)

        when (state) {
            //STATE_UNDEFINED -> renderField(canvas)
            STATE_BUG_PART  -> renderBugPart(canvas)
            STATE_NOTHING  -> renderNothing(canvas)
            STATE_EXPLODE -> renderExplode(canvas)
        }
    }
    override fun renderSecond(canvas: Canvas) {
        renderField(canvas)
        when (state) {
           //STATE_UNDEFINED -> renderField(canvas)
            STATE_BUG_PART -> renderBugPart(canvas)
            STATE_NOTHING  -> renderMiss(canvas)
            STATE_EXPLODE -> renderExplode(canvas)
        }
    }

    override fun renderWithoutBugsParts(canvas: Canvas) {
        renderField(canvas)

        when (state) {
            STATE_MISS  -> renderMiss(canvas)
            STATE_EXPLODE -> renderExplode(canvas)
        }
    }

    override fun renderWithoutBugsPartsSecond(canvas: Canvas) {
        renderField(canvas)
        when (state) {
            STATE_MISS  -> renderMiss(canvas)
            STATE_EXPLODE -> renderExplode(canvas)
        }
    }

    //функция отрисовки игрового поля
    private fun renderField(canvas: Canvas) {
        val x = x.toFloat()
        val y = y.toFloat()
        val w = width.toFloat()
        val h = height.toFloat()


        canvas.drawLine(x, y, x, y + w, paintWhite)
        canvas.drawLine(x, y, x + h, y, paintWhite)
        canvas.drawLine(x + h, y, x + h, y + w, paintWhite)
        canvas.drawLine(x + h, y + w, x, y + w, paintWhite)

    }

   //отрисовка промаха
    private fun renderMiss(canvas: Canvas) {

        val x = x.toFloat()
        val y = y.toFloat()
        val w = width.toFloat()
        val h = height.toFloat()

       canvas.drawLine(x, y, x, y + w, paintWhite)
       canvas.drawLine(x, y, x + h, y, paintWhite)
       canvas.drawLine(x + h, y, x + h, y + w, paintWhite)
       canvas.drawLine(x + h, y + w, x, y + w, paintWhite)

        canvas.drawLine(x, y, x + w, y + h, paintWhite)
        canvas.drawLine(x + w, y, x, y + h, paintWhite)
    }

    //отрисовка ранения
    private fun renderExplode(canvas: Canvas) {

        val x = x.toFloat()
        val y = y.toFloat()
        val w = width.toFloat()
        val h = height.toFloat()
        val hw = w * 0.5f
        val cx = x + hw
        val cy = y + h * 0.5f

        canvas.drawLine(x, y, x, y + w, paintWhite)
        canvas.drawLine(x, y, x + h, y, paintWhite)
        canvas.drawLine(x + h, y, x + h, y + w, paintWhite)
        canvas.drawLine(x + h, y + w, x, y + w, paintWhite)

        canvas.drawCircle(cx, cy, hw, paintBlack)
        canvas.drawCircle(cx, cy, hw * 0.9f, paintRed)
    }
    //отрисовка клетки с жуком
    private fun renderBugPart(canvas: Canvas) {
        val x = x.toFloat()
        val y = y.toFloat()
        val w = width.toFloat()
        val h = height.toFloat()
        val hw = w * 0.5f
        val cx = x + hw
        val cy = y + h * 0.5f

        canvas.drawLine(x, y, x, y + w, paintWhite)
        canvas.drawLine(x, y, x + h, y, paintWhite)
        canvas.drawLine(x + h, y, x + h, y + w, paintWhite)
        canvas.drawLine(x + h, y + w, x, y + w, paintWhite)

        canvas.drawCircle(cx, cy, hw, paintBlack)
        canvas.drawCircle(cx, cy, hw * 0.9f, paintGreen)
    }

    private fun renderNothing(canvas: Canvas) {

        val x = x.toFloat()
        val y = y.toFloat()
        val w = width.toFloat()
        val h = height.toFloat()

        canvas.drawLine(x, y, x, y + w, paintYellow)
        canvas.drawLine(x, y, x + h, y, paintYellow)
        canvas.drawLine(x + h, y, x + h, y + w, paintYellow)
        canvas.drawLine(x + h, y + w, x, y + w, paintYellow)

        canvas.drawLine(x, y, x + w, y + h, paintYellow)
        canvas.drawLine(x + w, y, x, y + h, paintYellow)
    }

}


