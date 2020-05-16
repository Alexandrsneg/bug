package ru.sneg.android.bug.credentials.game.bugPlacement

import android.os.Bundle
import android.view.View
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
               PlayingFieldUI().autoPlacing(secondPlayerBugs)

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
            cleanField()
            autoPlacing()
            showTvCounts()
        }
        // очистка игровога поля, сброс всех счетчиков для работы логики расстановки жуков
        bCleanFields.setOnClickListener {
            cleanField()
            showTvCounts()
        }

        bAcceptBug.setOnClickListener {
            var sum = 0
            BugsPlacing.orientationAndRemoving = 0

            for (i in 0..99) {
                if (firstPlayerBugs.takes[i].state == 1)  sum += firstPlayerBugs.takes[i].state
            }

            if (firstPlayerBugs.bugsRemaining == 10 && sum > 4) {
                toast(stringId = R.string.extra_bugs_on_field)
            }
            if (firstPlayerBugs.bugsRemaining == 10 && sum < 4) {
                toast(stringId = R.string.not_enougth_bugs_on_field)
            }

            if (firstPlayerBugs.bugsRemaining == 10 && sum == 4) {
                surrounding()
                firstPlayerBugs.fourPartBug--
                tvCountBugFour.text = firstPlayerBugs.fourPartBug.toString()
                firstPlayerBugs.bugsRemaining--
                return@setOnClickListener
            }

            if (firstPlayerBugs.bugsRemaining in 8..9 && sum == (4 + (9 - 3 * firstPlayerBugs.threePartBug))) {
                surrounding()
                firstPlayerBugs.threePartBug--
                tvCountBugThree.text = firstPlayerBugs.threePartBug.toString()
                firstPlayerBugs.bugsRemaining--
                return@setOnClickListener
            }
            if (firstPlayerBugs.bugsRemaining in 8..9 && sum > (4 + (9 - 3 * firstPlayerBugs.threePartBug))) {
                toast(stringId = R.string.extra_bugs_on_field)
            }
            if (firstPlayerBugs.bugsRemaining in 8..9 && sum < (4 + (9 - 3 * firstPlayerBugs.threePartBug))) {
                toast(stringId = R.string.not_enougth_bugs_on_field)
            }

            if (firstPlayerBugs.bugsRemaining in 5..7 && sum == (10 + (8 - 2 * firstPlayerBugs.twoPartBug))) {
                surrounding()
                firstPlayerBugs.twoPartBug--
                tvCountBugTwo.text = firstPlayerBugs.twoPartBug.toString()
                firstPlayerBugs.bugsRemaining--
                return@setOnClickListener
            }
            if (firstPlayerBugs.bugsRemaining in 5..7 && sum > (10 + (8 - 2 * firstPlayerBugs.twoPartBug))) {
                toast(stringId = R.string.extra_bugs_on_field)
            }
            if (firstPlayerBugs.bugsRemaining in 5..7 && sum < (10 + (8 - 2 * firstPlayerBugs.twoPartBug))) {
                toast(stringId = R.string.not_enougth_bugs_on_field)
            }

            if (firstPlayerBugs.bugsRemaining in 1..4 && sum == (16 + (5 - firstPlayerBugs.onePartBug))) {
                surrounding()
                firstPlayerBugs.onePartBug--
                tvCountBugOne.text = firstPlayerBugs.onePartBug.toString()
                firstPlayerBugs.bugsRemaining--
                return@setOnClickListener
            }
            if (firstPlayerBugs.bugsRemaining in 1..4 && sum > (16 + (5 - firstPlayerBugs.onePartBug))) {
                toast(stringId = R.string.extra_bugs_on_field)
            }
            if (firstPlayerBugs.bugsRemaining in 1..4 && sum < (16 + (5 - firstPlayerBugs.onePartBug))) {
                toast(stringId = R.string.not_enougth_bugs_on_field)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        botGame = false
    }

    override fun onRender(state: GameState) {
        gameView.setGameState(state)
    }
    private fun cleanField() {
        firstPlayerBugs.cleanField()
        gameView.render()
    }
    private fun surrounding() {
        firstPlayerBugs.acceptBugSurrounding()
        gameView.render()
    }

    private fun autoPlacing() {
        gameView.autoPlacing()
        firstPlayerBugs.fourPartBug = 0
        firstPlayerBugs.threePartBug = 0
        firstPlayerBugs.twoPartBug = 0
        firstPlayerBugs.onePartBug = 0
    }

    private fun showTvCounts(){
        tvCountBugFour.text = firstPlayerBugs.fourPartBug.toString()
        tvCountBugThree.text = firstPlayerBugs.threePartBug.toString()
        tvCountBugTwo.text = firstPlayerBugs.twoPartBug.toString()
        tvCountBugOne.text = firstPlayerBugs.onePartBug.toString()
    }

}