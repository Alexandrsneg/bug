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
import ru.sneg.android.bug.game.engine.BotPlayer.Companion.botFindAndFinishingBug
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

        tv_player_login.text = UserStorage().getUser()?.login ?:  "Unonimous bug"

        //лпределяем чей ход будет первым
        when (presenter.whoIsFirst()) {
            1 -> { // первый ходит бот
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

        //если бот промахнуля в проессе добивания жука - ход по алгоритму поиска
        if (!firstBotShot && botMiss && botFindAndFinishingBug) {
            gameOfflineBotFirstPlayerView.onClickByBot(BotPlayer.nextShoot.first.toFloat(), BotPlayer.nextShoot.second.toFloat())
        }

        //если бот помахивается и он не добивает жука - следующий выстрел случайный
        if (!firstBotShot && botMiss && !botFindAndFinishingBug) {
            gameOfflineBotFirstPlayerView.onClickByBot(botPlayer.botNewShot().first, botPlayer.botNewShot().second)
        }

        //если бот попал - случайный обстрел ближайшего поля
        while (!botMiss) {
            //время на "подумать" для бота
            Thread.sleep(1000)
            gameOfflineBotFirstPlayerView.onClickByBot(BotPlayer.nextShoot.first.toFloat(), BotPlayer.nextShoot.second.toFloat())
        }
        firstBotShot = false
    }

    override fun lock() {
    }

    override fun unlock() {
    }

}