package ru.sneg.android.bug.credentials.bugPlacement

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_bug_placement_player.*
import kotlinx.android.synthetic.main.fragment_bug_placement_player.bAcceptBug
import kotlinx.android.synthetic.main.fragment_bug_placement_player.bAutoSetUp
import kotlinx.android.synthetic.main.fragment_bug_placement_player.bCleanFields
import kotlinx.android.synthetic.main.fragment_bug_placement_player_second.*
import ru.sneg.android.bug.R
import ru.sneg.android.bug.activities.routers.IBattleGroundsGameRouter
import ru.sneg.android.bug.base.ABaseFragment
import ru.sneg.android.bug.domain.di.components.DaggerAppComponent
import ru.sneg.android.bug.game.engine.GameState
import ru.sneg.android.bug.game.gameObjects.BugsPlacing
import ru.sneg.android.bug.game.gameViews.GameBugPlacementSecondPlayerView
import ru.sneg.android.bug.game.gameViews.GameBugPlacementView
import ru.sneg.android.bug.game.gameViews.GameOfflinePvpFirstPlayerView
import javax.inject.Inject
class BugPlacementPlayerSecondFragment : ABaseFragment(),
    IBugPlaycementPlayerSecondView {


    @Inject //использование Даггером конструктора из презентера, подставление зависимости
    @InjectPresenter // аннотация Moxy управляет ж. циклом Presenter
    lateinit var presenter: BugPlacementPlayerSecondPresenter

    @ProvidePresenter // предоставление презентера для Moxy
    fun providePresenter() = presenter


    override fun inject() {
        DaggerAppComponent.create().inject(this)
    }

    override fun getViewId() = R.layout.fragment_bug_placement_player_second

    var secondPlayerBugs = GameBugPlacementSecondPlayerView.secondPlayerBugs

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvCountBugFourS.text = secondPlayerBugs.fourPartBug.toString()
        tvCountBugThreeS.text = secondPlayerBugs.threePartBug.toString()
        tvCountBugTwoS.text = secondPlayerBugs.twoPartBug.toString()
        tvCountBugOneS.text = secondPlayerBugs.onePartBug.toString()

        gameViewSecond.onSelectListener = {
            println(it)
            presenter.onCell(it)
        }

        //автоматическая расстановка жуков
        bAutoSetUp.setOnClickListener {
            secondPlayerBugs.cleanField()
            BugsPlacing().eachBugAutoPlacing(GameBugPlacementSecondPlayerView.secondPlayerBugs)
            showTvCounts()
            gameViewSecond.render()
        }

        // смена фрагмента на фрагмент игры офлайн
        bForwardSecond.setOnClickListener {
            if(secondPlayerBugs.bugsRemaining == 0) {
                activity?.let {
                    if (it is IBattleGroundsGameRouter)
                        it.showBugVsBugGame()
                }
            }
            else toast(stringId = R.string.not_enougth_bugs_on_field)
        }

        // очистка игровогo поля, сброс всех счетчиков для работы логики расстановки жуков
        bCleanFields.setOnClickListener {
            secondPlayerBugs.cleanField()
            showTvCounts()
            gameViewSecond.render()
        }

        bAcceptBug.setOnClickListener {
            when (BugsPlacing().bugPlacingCheckOut(secondPlayerBugs)){
                "delete" -> toast(stringId = R.string.extra_bugs_on_field)
                "add" -> toast(stringId = R.string.not_enougth_bugs_on_field)
            }
            showTvCounts()
            gameViewSecond.render()
        }
    }

    override fun onRender(state: GameState) {
        gameViewSecond.setGameState(state)
    }

    private fun showTvCounts(){
        tvCountBugFourS.text = secondPlayerBugs.fourPartBug.toString()
        tvCountBugThreeS.text = secondPlayerBugs.threePartBug.toString()
        tvCountBugTwoS.text = secondPlayerBugs.twoPartBug.toString()
        tvCountBugOneS.text = secondPlayerBugs.onePartBug.toString()
    }
}






