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
import ru.sneg.android.bug.activities.routers.IBattleGroundsRouter
import ru.sneg.android.bug.base.ABaseFragment
import ru.sneg.android.bug.domain.di.components.DaggerAppComponent
import ru.sneg.android.bug.game.engine.GameState
import ru.sneg.android.bug.game.gameObjects.BugsPlacing
import ru.sneg.android.bug.game.gameViews.GameBugPlacementSecondPlayerView
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
            autoPlacing()
        }

        // смена фрагмента на фрагмент игры офлайн
        bForwardSecond.setOnClickListener {
            if(secondPlayerBugs.bugsRemaining == 0) {
                activity?.let {
                    if (it is IBattleGroundsRouter)
                        it.showBugVsBugGame()
                }
            }
            else toast(stringId = R.string.not_enougth_bugs_on_field)
        }

        // очистка игровогo поля, сброс всех счетчиков для работы логики расстановки жуков
        bCleanFields.setOnClickListener {
            cleanField()
            showTvCounts()
        }

        bAcceptBug.setOnClickListener {
            var sum = 0
            BugsPlacing.orientationAndRemoving = 0

            for (i in 0..99) {
                if (secondPlayerBugs.takes[i].state == 1)  sum += secondPlayerBugs.takes[i].state
            }

            if (secondPlayerBugs.bugsRemaining == 10 && sum > 4) {
                toast(stringId = R.string.extra_bugs_on_field)
            }
            if (secondPlayerBugs.bugsRemaining == 10 && sum < 4) {
                toast(stringId = R.string.not_enougth_bugs_on_field)
            }

            if (secondPlayerBugs.bugsRemaining == 10 && sum == 4) {
                surrounding()
                secondPlayerBugs.fourPartBug--
                tvCountBugFourS.text = secondPlayerBugs.fourPartBug.toString()
                secondPlayerBugs.bugsRemaining--
                return@setOnClickListener
            }

            if (secondPlayerBugs.bugsRemaining in 8..9 && sum == (4 + (9 - 3 * secondPlayerBugs.threePartBug))) {
                surrounding()
                secondPlayerBugs.threePartBug--
                tvCountBugThreeS.text = secondPlayerBugs.threePartBug.toString()
                secondPlayerBugs.bugsRemaining--
                return@setOnClickListener
            }
            if (secondPlayerBugs.bugsRemaining in 8..9 && sum > (4 + (9 - 3 * secondPlayerBugs.threePartBug))) {
                toast(stringId = R.string.extra_bugs_on_field)
            }
            if (secondPlayerBugs.bugsRemaining in 8..9 && sum < (4 + (9 - 3 * secondPlayerBugs.threePartBug))) {
                toast(stringId = R.string.not_enougth_bugs_on_field)
            }

            if (secondPlayerBugs.bugsRemaining in 5..7 && sum == (10 + (8 - 2 * secondPlayerBugs.twoPartBug))) {
                surrounding()
                secondPlayerBugs.twoPartBug--
                tvCountBugTwoS.text = secondPlayerBugs.twoPartBug.toString()
                secondPlayerBugs.bugsRemaining--
                return@setOnClickListener
            }
            if (secondPlayerBugs.bugsRemaining in 5..7 && sum > (10 + (8 - 2 * secondPlayerBugs.twoPartBug))) {
                toast(stringId = R.string.extra_bugs_on_field)
            }
            if (secondPlayerBugs.bugsRemaining in 5..7 && sum < (10 + (8 - 2 * secondPlayerBugs.twoPartBug))) {
                toast(stringId = R.string.not_enougth_bugs_on_field)
            }

            if (secondPlayerBugs.bugsRemaining in 1..4 && sum == (16 + (5 - secondPlayerBugs.onePartBug))) {
                surrounding()
                secondPlayerBugs.onePartBug--
                tvCountBugOneS.text = secondPlayerBugs.onePartBug.toString()
                secondPlayerBugs.bugsRemaining--
                return@setOnClickListener
            }
            if (secondPlayerBugs.bugsRemaining in 1..4 && sum > (16 + (5 - secondPlayerBugs.onePartBug))) {
                toast(stringId = R.string.extra_bugs_on_field)
            }
            if (secondPlayerBugs.bugsRemaining in 1..4 && sum < (16 + (5 - secondPlayerBugs.onePartBug))) {
                toast(stringId = R.string.not_enougth_bugs_on_field)
            }
        }
    }

    override fun onRender(state: GameState) {
        gameViewSecond.setGameState(state)
    }
    //очищает поле от всех жуков и обводок
    private fun cleanField() {
        secondPlayerBugs.cleanField()
        gameViewSecond.render()
    }
    //обводка после нажатия кнопик "установить"
    private fun surrounding() {
        secondPlayerBugs.acceptBugSurrounding()
        gameViewSecond.render()
    }
    private fun autoPlacing() {
        gameViewSecond.autoPlacing()
        secondPlayerBugs.fourPartBug = 0
        secondPlayerBugs.threePartBug = 0
        secondPlayerBugs.twoPartBug = 0
        secondPlayerBugs.onePartBug = 0
    }

    private fun showTvCounts(){
        tvCountBugFourS.text = secondPlayerBugs.fourPartBug.toString()
        tvCountBugThreeS.text = secondPlayerBugs.threePartBug.toString()
        tvCountBugTwoS.text = secondPlayerBugs.twoPartBug.toString()
        tvCountBugOneS.text = secondPlayerBugs.onePartBug.toString()
    }
}






