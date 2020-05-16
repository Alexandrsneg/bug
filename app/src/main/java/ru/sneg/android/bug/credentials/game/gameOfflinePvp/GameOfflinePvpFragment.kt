package ru.sneg.android.bug.credentials.game.gameOfflinePvp

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_bug_placement_player.*
import kotlinx.android.synthetic.main.fragment_game_offline_pvp.*
import ru.sneg.android.bug.R
import ru.sneg.android.bug.activities.GameActivity
import ru.sneg.android.bug.base.ABaseFragment
import ru.sneg.android.bug.domain.di.components.DaggerAppComponent
import ru.sneg.android.bug.game.UI.PlayingFieldUI
import ru.sneg.android.bug.game.UI.TakeUI
import ru.sneg.android.bug.game.engine.GameState
import ru.sneg.android.bug.game.gameObjects.Bugs
import ru.sneg.android.bug.game.gameViews.GameBugPlacementSecondPlayerView.Companion.secondPlayerBugs
import ru.sneg.android.bug.game.gameViews.GameBugPlacementView
import ru.sneg.android.bug.game.gameViews.GameBugPlacementView.Companion.firstPlayerBugs
import javax.inject.Inject


class GameOfflinePvpFragment: ABaseFragment(), IGameOfflinePvpView {

    companion object{
        var changeMove = false
    }


    @Inject //использование Даггером конструктора из презентера, подставление зависимости
    @InjectPresenter // аннотация Moxy управляет ж. циклом Presenter
    lateinit var presenter: GameOfflinePvpPresenter

    @ProvidePresenter // предоставление презентера для Moxy
    fun providePresenter() = presenter

    override fun inject() {
        DaggerAppComponent.create().inject(this)
    }

    override fun getViewId(): Int = R.layout.fragment_game_offline_pvp


            override fun onRender(state: GameState) {
        gameOfflineFirstPlayerView.setGameStateFirstPlayer(state)

        gameOfflineSecondPlayerView.setGameStateSecondPlayer(state)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //лпределяем чей ход будет первым, если нужно поворачиваем стрелку
        when (presenter.whoIsFirst()) {
            1 -> {
                iv_anim_arrow.animate().rotation(180F)
                gameOfflineSecondPlayerView.isEnabled = false
                gameOfflineFirstPlayerView.isEnabled = true
            }
            2 -> {
                // блокировка левого поля
                gameOfflineSecondPlayerView.isEnabled = true
                gameOfflineFirstPlayerView.isEnabled = false

                changeMove = false
            }
        }

        gameOfflineFirstPlayerView.setOnTouchListener {_, event ->
            when (event.action){
                MotionEvent.ACTION_DOWN -> true // Иначе не сработает ACTION_UP
                MotionEvent.ACTION_UP -> {
                    gameOfflineFirstPlayerView.onClick(event.x, event.y)
                    //поворот стрелки в случае промаха на правое полеБ блокировка левого
                    rotate360()
                }
                else -> false
            }
        }


        gameOfflineSecondPlayerView.setOnTouchListener {_, event ->

            when (event.action){
                MotionEvent.ACTION_DOWN -> true // Иначе не сработает ACTION_UP
                MotionEvent.ACTION_UP -> {
                    gameOfflineSecondPlayerView.onClick(event.x, event.y)
                    //поворот стрелки в случае промаха на левое поле блокировка правого
                    rotate180()
                }
                else -> false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        firstPlayerBugs.cleanField()
        secondPlayerBugs.cleanField()
    }

    fun rotate360(): Boolean{

        if (changeMove) {
            // блокировка левого поля
            gameOfflineSecondPlayerView.isEnabled = true
            gameOfflineFirstPlayerView.isEnabled = false

            //поворот стрелки в случае промаха на правое поле
            iv_anim_arrow.animate().rotation(360F)
            changeMove = false
        }
            return true
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

    fun rotate180(): Boolean{

        if(changeMove) {
            iv_anim_arrow.animate().rotation(180F)
            gameOfflineSecondPlayerView.isEnabled = false
            gameOfflineFirstPlayerView.isEnabled = true
            changeMove = false
        }

        return true
    }

    override fun lock() {

    }

    override fun unlock() {
        visibility(gameOfflineFirstPlayerView, false)
    }

}