package ru.sneg.android.bug.game.gameViews

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import ru.sneg.android.bug.credentials.game.gameOfflineBot.GameOfflineBotFragment
import ru.sneg.android.bug.game.UI.PlayingFieldUI
import ru.sneg.android.bug.game.UI.TakeUI
import ru.sneg.android.bug.game.engine.players.BotPlayer
import ru.sneg.android.bug.game.engine.GameState
import ru.sneg.android.bug.game.gameViews.GameBugPlacementView.Companion.firstPlayerBugs

class GamePlayFieldFirstPlayerView @JvmOverloads constructor(
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

    private val playingField = PlayingFieldUI()
    private val botPlayer = BotPlayer(GameOfflineBotFragment())

    var onSelectListener: ((TakeUI) -> Unit)? = null

    init {
        holder.addCallback(this)
        //обработчик нажатия
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


    fun setGameStateFirstPlayer(state: GameState) {
        playingField.setGameState(state, firstPlayerBugs)
        render()
    }

    private fun render(canvas: Canvas) {
        playingField.width = width
        playingField.height = height
        playingField.renderWithoutBugsParts(canvas, firstPlayerBugs )
    }

     fun onClick(x: Float, y: Float) : Boolean{

            playingField.onClickGameField(x, y, firstPlayerBugs)
            render()

        val listener = onSelectListener ?: return false

       playingField.onClick(x,y, firstPlayerBugs)?.let{  //если значения onClick не null -> срабатывет .let
           if(it.state == TakeUI.STATE_UNDEFINED)
           listener(it)
       }
        return true
    }
    fun onClickByBot(x: Float, y: Float) : Boolean{

        botPlayer.onClickGameFieldByBot(x, y, firstPlayerBugs)
        render()

        return true
    }
}