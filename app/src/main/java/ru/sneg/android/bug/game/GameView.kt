package ru.sneg.android.bug.game

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import ru.sneg.android.bug.game.UI.PlayingFieldUI
import ru.sneg.android.bug.game.UI.TakeUI

class GameView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : SurfaceView(context, attrs, defStyleAttr), SurfaceHolder.Callback {

    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) { }
    override fun surfaceDestroyed(p0: SurfaceHolder?) { sfHolder = null }
    override fun surfaceCreated(p0: SurfaceHolder?) { sfHolder = p0 }

    private var sfHolder: SurfaceHolder? = null
        set(value) {
            field?.removeCallback(this)
            field = value
            value?.addCallback(this)
            render()
        }

    private val playingField = PlayingFieldUI()

    var onSelectListener: ((TakeUI) -> Unit)? = null

    init {
        sfHolder = holder

        //обработчик нажатия
        setOnTouchListener {_, event ->

            when (event.action){
                MotionEvent.ACTION_UP -> onClick(event.x, event.y)
                else -> false
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        postDelayed({ render() }, 2000)
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

    private fun onClick(x: Float, y: Float) : Boolean{
        val listener = onSelectListener ?: return false

       playingField.onClick(x,y)?.let{  //если значения onClick не null -> срабатывет .let
           if(it.state == TakeUI.STATE_UNDEFINED)
           listener(it)
       }
        return true
    }
}