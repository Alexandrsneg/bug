package ru.sneg.android.bug.game.gameViews

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import ru.sneg.android.bug.game.UI.PlayingFieldUI
import ru.sneg.android.bug.game.UI.TakeUI
import ru.sneg.android.bug.game.engine.GameState
import ru.sneg.android.bug.game.gameObjects.Bugs

class GameBugPlacementView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : SurfaceView(context, attrs, defStyleAttr), SurfaceHolder.Callback {

    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) { }
    override fun surfaceDestroyed(p0: SurfaceHolder?) { }
    override fun surfaceCreated(p0: SurfaceHolder?) { render()}

    private var sfHolder: SurfaceHolder? = null
        set(value) {
            field?.removeCallback(this)
            field = value
            value?.addCallback(this)
            render()
        }
companion object {
    var firstPlayerBugs = Bugs()
}

    private val playingField = PlayingFieldUI()

    var onSelectListener: ((TakeUI) -> Unit)? = null

    init {
        holder.addCallback(this)

        //обработчик нажатия
        setOnTouchListener {_, event ->

            when (event.action){
                MotionEvent.ACTION_DOWN -> true // Иначе не сработает ACTION_UP
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

    fun setGameState(state: GameState) {

        playingField.setGameState(state, firstPlayerBugs)
        render()
    }

    private fun render(canvas: Canvas) {
        playingField.width = width
        playingField.height = height
        playingField.renderGameField(canvas, firstPlayerBugs)
    }


    private fun onClick(x: Float, y: Float) : Boolean{

            playingField.onClickFieldBugPlacing(x, y,firstPlayerBugs)
            render()

        val listener = onSelectListener ?: return false

       playingField.onClick(x,y, firstPlayerBugs)?.let{  //если значения onClick не null -> срабатывет .let
           if(it.state == TakeUI.STATE_UNDEFINED)
           listener(it)
       }
        return true
    }
}
