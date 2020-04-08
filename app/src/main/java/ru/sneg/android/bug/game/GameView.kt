package ru.sneg.android.bug.game

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import ru.sneg.android.bug.game.UI.PlayingFieldUI

class GameView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : SurfaceView(context, attrs, defStyleAttr), SurfaceHolder.Callback {

    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) { }
    override fun surfaceDestroyed(p0: SurfaceHolder?) { sfHolder = null }
    override fun surfaceCreated(p0: SurfaceHolder?) { sfHolder = p0 }

    private var sfHolder: SurfaceHolder? = null
        set(value) {
            field = value
            value?.addCallback(this)
        }

    private val playingField = PlayingFieldUI()

    init {

        sfHolder = holder
    }


    fun setBugs(){
        playingField.setBugsFour(5)
        //super.onAttachedToWindow()
        //GameView(gameView.context).onAttachedToWindow()
        //super.onAttachedToWindow()
        // render()
        post({ render() })

    }

    fun render() {

        val holder = sfHolder ?: return
        var canvas: Canvas? = null

        try {

            canvas = holder.lockCanvas()
            if (canvas != null)
                render(canvas)

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            canvas?.let { holder.unlockCanvasAndPost(it) }
        }
    }

    private fun render(canvas: Canvas) {
        playingField.width = width
        playingField.height = height
        playingField.render(canvas)
    }

}