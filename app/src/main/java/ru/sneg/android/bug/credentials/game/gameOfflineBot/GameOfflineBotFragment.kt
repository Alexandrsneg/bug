package ru.sneg.android.bug.credentials.game.gameOfflineBot

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_game_offline_bot.*
import kotlinx.android.synthetic.main.fragment_game_offline_pvp.gameOfflineFirstPlayerView
import kotlinx.android.synthetic.main.fragment_game_offline_pvp.gameOfflineSecondPlayerView
import kotlinx.android.synthetic.main.fragment_game_offline_pvp.iv_anim_arrow
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
    }
    val botPlayer = BotPlayer()


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

        tv_player_login.text = UserStorage().getUser()?.login ?:  "Unonimous bug"

        //поле игрока всегда заблокированно для касаний


        //лпределяем чей ход будет первым, если нужно поворачиваем стрелку
        when (presenter.whoIsFirst()) {
            1 -> { // первый ходит бот
                rotate180()
                //блокировка поля бота
                gameOfflineBotSecondPlayerView.isEnabled = false
                botMove()
            }
            2 -> { // первый ходит игрок
                // разблокировка поля бота
                gameOfflineBotSecondPlayerView.isEnabled = true

            }
        }

        gameOfflineBotSecondPlayerView.setOnTouchListener {_, event ->
            when (event.action){
                MotionEvent.ACTION_DOWN -> true // Иначе не сработает ACTION_UP
                MotionEvent.ACTION_UP -> {
                    gameOfflineBotSecondPlayerView.onClick(event.x, event.y)
                    //поворот стрелки в случае промаха на правое поле, блокировка левого
                    changeMove()
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
                // блокировка поля андроида
                gameOfflineBotSecondPlayerView.isEnabled = false

                //поворот стрелки в случае промаха на левое поле
                rotate180()
                Thread.sleep(1000)

                //обнуление смены хода для игрока
                !playerMiss
                //ход бота
                botMove()
            }


            return true
    }


    private fun botMove(){
        var x = botPlayer.botMove().first
        var y = botPlayer.botMove().second
        gameOfflineBotFirstPlayerView.onClickByBot(x,y)
        if (!botMiss) {
            Thread.sleep(1000)
            botMove()
        }
        else {
            botMiss
            rotate360()
            gameOfflineBotSecondPlayerView.isEnabled = true
        }

    }

    private fun rotate180(){
        iv_anim_arrow_bot.animate().rotation(180F)
    }
    private fun rotate360(){
        iv_anim_arrow_bot.animate().rotation(360F)
    }


    override fun lock() {

    }

    override fun unlock() {
        visibility(gameOfflineFirstPlayerView, false)
    }

}