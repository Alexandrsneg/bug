package ru.sneg.android.bug.credentials.game.gameOfflineBot

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_game_offline_bot.*
import ru.sneg.android.bug.R
import ru.sneg.android.bug.base.ABaseFragment
import ru.sneg.android.bug.domain.di.components.DaggerAppComponent
import ru.sneg.android.bug.domain.repositories.local.UserStorage
import ru.sneg.android.bug.game.engine.BotPlayer
import ru.sneg.android.bug.game.engine.GameState
import ru.sneg.android.bug.game.gameViews.GameBugPlacementSecondPlayerView.Companion.secondPlayerBugs
import ru.sneg.android.bug.game.gameViews.GameBugPlacementView.Companion.firstPlayerBugs
import javax.inject.Inject


class GameOfflineBotFragment: ABaseFragment(), IGameOfflineBotView {

    companion object{
        var playerMiss = false
        var botMiss = false

        var different = true
    }
    val botPlayer = BotPlayer()

    var firstBotShot = true


    @Inject //использование Даггером конструктора из презентера, подставление зависимости
    @InjectPresenter // аннотация Moxy управляет ж. циклом Presenter
    lateinit var presenter: GameOfflineBotPresenter

    @ProvidePresenter // предоставление презентера для Moxy
    fun providePresenter() = presenter

    override fun inject() {
        DaggerAppComponent.create().inject(this)
    }

    override fun getViewId(): Int = R.layout.fragment_game_offline_bot


            override fun onRender(state: GameState) {
        gameOfflineBotFirstPlayerView.setGameStateFirstPlayer(state)

        gameOfflineBotSecondPlayerView.setGameStateSecondPlayer(state)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        visibility(iv_anim_arrow_bot_right, false)
        visibility(iv_anim_arrow_bot_left, false)

        tv_player_login.text = UserStorage().getUser()?.login ?:  "Unonimous bug"

        //поле игрока всегда заблокированно для касаний


        //лпределяем чей ход будет первым, если нужно поворачиваем стрелку
        when (presenter.whoIsFirst()) {
            1 -> { // первый ходит бот
                //arrowToLeft()
                botMove()
            }
        }

        gameOfflineBotSecondPlayerView.setOnTouchListener {_, event ->
            when (event.action){
                MotionEvent.ACTION_DOWN -> true // Иначе не сработает ACTION_UP
                MotionEvent.ACTION_UP -> {
                    gameOfflineBotSecondPlayerView.onClick(event.x, event.y)

                    //если клетка ещё не сыграна, переход хода  *******
                    if (different) {
                        changeMove()
                    }
                    //если клетка ещё не сыграна, переход хода  *******
                    true
                }
                else -> false
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        firstPlayerBugs.cleanField()
        secondPlayerBugs.cleanField()
    }

    private fun changeMove(): Boolean{
            if(playerMiss) {
                //отображение стрелки в случае промаха на левое поле
               //arrowToLeft()
                //время на "подумать" для бота
                Thread.sleep(1000)
                //обнуление смены хода для игрока
                !playerMiss
                //ход бота
                botMove()
            }
            return true
    }

    private fun botMove(){
        //если это первый выстрел бота
        if (firstBotShot){
            gameOfflineBotFirstPlayerView.onClickByBot(botPlayer.botNewShot().first, botPlayer.botNewShot().second)
        }
            //если бот помахивается - следующий выстрел случайный
         if (!firstBotShot && botMiss) {
            gameOfflineBotFirstPlayerView.onClickByBot(botPlayer.botNewShot().first, botPlayer.botNewShot().second)
        }
            //если бот попал - обстрел ближайших полей
        while (!botMiss) {
            //время на "подумать" для бота
            Thread.sleep(1000)
            gameOfflineBotFirstPlayerView.onClickByBot(botPlayer.nextShootX.toFloat(), botPlayer.nextShootY.toFloat())
        }
        firstBotShot = false
    }

    private fun arrowToRight(){
        visibility(iv_anim_arrow_bot_right, true)
        visibility(iv_anim_arrow_bot_left, false)
    }
    private fun arrowToLeft(){
        visibility(iv_anim_arrow_bot_right, false)
        visibility(iv_anim_arrow_bot_left, true)
    }


    override fun lock() {
    }

    override fun unlock() {
    }

}