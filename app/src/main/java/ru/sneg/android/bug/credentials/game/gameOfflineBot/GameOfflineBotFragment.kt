package ru.sneg.android.bug.credentials.game.gameOfflineBot

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_game_offline_bot.*
import ru.sneg.android.bug.R
import ru.sneg.android.bug.activities.routers.IBattleGroundsGameRouter
import ru.sneg.android.bug.base.ABaseFragment
import ru.sneg.android.bug.domain.di.components.DaggerAppComponent
import ru.sneg.android.bug.domain.repositories.local.UserStorage
import ru.sneg.android.bug.game.engine.players.BotPlayer
import ru.sneg.android.bug.game.engine.players.BotPlayer.Companion.botFindAndFinishingBug
import ru.sneg.android.bug.game.engine.players.BotPlayer.Companion.firstBlood
import ru.sneg.android.bug.game.engine.players.BotPlayer.Companion.firstGoodShoot
import ru.sneg.android.bug.game.engine.players.BotPlayer.Companion.lastGoodShoot
import ru.sneg.android.bug.game.engine.players.BotPlayer.Companion.nextShoot
import ru.sneg.android.bug.game.engine.GameState
import ru.sneg.android.bug.game.engine.players.BotPlayer.Companion.playerMiss
import ru.sneg.android.bug.game.gameViews.GameBugPlacementSecondPlayerView.Companion.secondPlayerBugs
import ru.sneg.android.bug.game.gameViews.GameBugPlacementView.Companion.firstPlayerBugs
import javax.inject.Inject


class GameOfflineBotFragment: ABaseFragment(), IGameOfflineBotView {

    companion object{
        var gameWithBotIsOver = false
    }

    private val botPlayer = BotPlayer(this)


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


        gameOfflineBotSecondPlayerView.onSelectListener = {
            if (gameWithBotIsOver){
                if (it is IBattleGroundsGameRouter)
                    it.showBugPlaycementSecond()
            }
        }

        gameOfflineBotSecondPlayerView.setOnTouchListener {_, event ->
            when (event.action){
                MotionEvent.ACTION_DOWN -> true // Иначе не сработает ACTION_UP
                MotionEvent.ACTION_UP -> {
                    gameOfflineBotSecondPlayerView.onClick(event.x, event.y)

                    //если клетка ещё не сыграна, переход хода  *******
                    if (BotPlayer.differentCell) {
                        changeMove()
                    }
                    true
                }
                else -> false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //очистка поля
        firstPlayerBugs.cleanField()
        secondPlayerBugs.cleanField()

        //обнуление значений для нормально работы бота в новой игре
        firstGoodShoot = Pair(0, 0)
        lastGoodShoot = Pair(0, 0)
        nextShoot = Pair(0, 0)

        firstBlood = false
        botFindAndFinishingBug = false
        gameWithBotIsOver = false
    }

    private fun changeMove(): Boolean{
            if(playerMiss) {
                //время на "подумать" для бота
                Thread.sleep(1000)
                //обнуление смены хода для игрока
                !playerMiss
                //ход бота
                botPlayer.botShootingTactics()
            }
            return true
    }


    override fun lock() {
    }

    override fun unlock() {
    }

}