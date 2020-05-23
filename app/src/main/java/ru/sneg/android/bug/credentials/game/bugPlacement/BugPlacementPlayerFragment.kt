package ru.sneg.android.bug.credentials.game.bugPlacement

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_bug_placement_player.*
import kotlinx.android.synthetic.main.fragment_bug_placement_player.bAcceptBug
import kotlinx.android.synthetic.main.fragment_bug_placement_player.bAutoSetUp
import kotlinx.android.synthetic.main.fragment_bug_placement_player.bCleanFields
import kotlinx.android.synthetic.main.fragment_bug_placement_player.bForward
import kotlinx.android.synthetic.main.fragment_bug_placement_player.tvCountBugFour
import kotlinx.android.synthetic.main.fragment_bug_placement_player.tvCountBugOne
import kotlinx.android.synthetic.main.fragment_bug_placement_player.tvCountBugThree
import kotlinx.android.synthetic.main.fragment_bug_placement_player.tvCountBugTwo
import kotlinx.android.synthetic.main.fragment_bug_placement_player_second.*
import ru.sneg.android.bug.R
import ru.sneg.android.bug.activities.GameModeActivity
import ru.sneg.android.bug.activities.routers.IBattleGroundsGameRouter
import ru.sneg.android.bug.base.ABaseFragment
import ru.sneg.android.bug.credentials.bugPlacement.BugPlacementPlayerSecondFragment
import ru.sneg.android.bug.domain.di.components.DaggerAppComponent
import ru.sneg.android.bug.game.UI.PlayingFieldUI
import ru.sneg.android.bug.game.engine.GameState
import ru.sneg.android.bug.game.gameObjects.BugsPlacing
import ru.sneg.android.bug.game.gameViews.GameBugPlacementSecondPlayerView.Companion.secondPlayerBugs

import ru.sneg.android.bug.game.gameViews.GameBugPlacementView
import javax.inject.Inject
class BugPlacementPlayerFragment : ABaseFragment(),
    IBugPlaycementPlayerView {

    companion object{
        var botGame = false
    }

    @Inject //использование Даггером конструктора из презентера, подставление зависимости
    @InjectPresenter // аннотация Moxy управляет ж. циклом Presenter
    lateinit var presenter: BugPlacementPlayerPresenter

    @ProvidePresenter // предоставление презентера для Moxy
    fun providePresenter() = presenter

    override fun inject() {
        DaggerAppComponent.create().inject(this)
    }

    override fun getViewId() = R.layout.fragment_bug_placement_player

    var firstPlayerBugs = GameBugPlacementView.firstPlayerBugs

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showTvCounts()

        gameView.onSelectListener = {
            println(it)
            presenter.onCell(it)
        }

// смена фрагмента на расстановку жуков для второго игрока
        bForward.setOnClickListener {
            if (firstPlayerBugs.bugsRemaining == 0) {
                activity?.let {
                    if (it is IBattleGroundsGameRouter)
                        it.showBugPlaycementSecond()
                }
            } else toast(stringId = R.string.not_enougth_bugs_on_field)
            // если была нажата кнопка игры против бота
            if (firstPlayerBugs.bugsRemaining == 0 && botGame) {
                //авторасстановка жуков для бота
               BugsPlacing().eachBugAutoPlacing(secondPlayerBugs)

                activity?.let {
                    if (it is IBattleGroundsGameRouter)
                        it.showBugVsCpuBugGame()
                }
            } else if(botGame) toast(stringId = R.string.not_enougth_bugs_on_field)
        }

        //при нажатии кнопки Change profile выводим фрагмент SignIn в CredentialsActivity
        bProfile.setOnClickListener {
            GameModeActivity.show()
        }
        //автоматическая расстановка жуков
        bAutoSetUp.setOnClickListener {
            firstPlayerBugs.cleanField()
            BugsPlacing().eachBugAutoPlacing(GameBugPlacementView.firstPlayerBugs)
            showTvCounts()
            gameView.render()
        }
        // очистка игровога поля, сброс всех счетчиков для работы логики расстановки жуков
        bCleanFields.setOnClickListener {
            firstPlayerBugs.cleanField()
            showTvCounts()
            gameView.render()
        }
        //кнопка подтверждения установки жука
        bAcceptBug.setOnClickListener {
            when (BugsPlacing().bugPlacingCheckOut(firstPlayerBugs)){
                "delete" -> toast(stringId = R.string.extra_bugs_on_field)
                "add" -> toast(stringId = R.string.not_enougth_bugs_on_field)
            }
            showTvCounts()
            gameView.render()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        botGame = false
    }

    override fun onRender(state: GameState) {
        gameView.setGameState(state)
    }

    private fun showTvCounts(){
        tvCountBugFour.text = firstPlayerBugs.fourPartBug.toString()
        tvCountBugThree.text = firstPlayerBugs.threePartBug.toString()
        tvCountBugTwo.text = firstPlayerBugs.twoPartBug.toString()
        tvCountBugOne.text = firstPlayerBugs.onePartBug.toString()
    }

}