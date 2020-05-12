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
import ru.sneg.android.bug.R
import ru.sneg.android.bug.activities.GameModeActivity
import ru.sneg.android.bug.activities.routers.IBattleGroundsRouter
import ru.sneg.android.bug.base.ABaseFragment
import ru.sneg.android.bug.domain.di.components.DaggerAppComponent
import ru.sneg.android.bug.game.engine.GameState
import ru.sneg.android.bug.game.gameObjects.Bugs

import ru.sneg.android.bug.game.gameViews.GameBugPlacementView
import javax.inject.Inject
class BugPlacementPlayerFragment : ABaseFragment(),
    IBugPlaycementPlayerView {

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

        tvCountBugFour.text = firstPlayerBugs.fourPartBug.toString()
        tvCountBugThree.text = firstPlayerBugs.threePartBug.toString()
        tvCountBugTwo.text = firstPlayerBugs.twoPartBug.toString()
        tvCountBugOne.text = firstPlayerBugs.onePartBug.toString()

        gameView.onSelectListener = {
            println(it)
            presenter.onCell(it)
        }

// смена фрагмента на расстановку жуков для второго игрока
        bForward.setOnClickListener {
            if (firstPlayerBugs.bugsRemaining == 0) {
                activity?.let {
                    if (it is IBattleGroundsRouter)
                        it.showBugPlaycementSecond()
                }
            } else toast(stringId = R.string.not_enougth_bugs_on_field)
        }

        //при нажатии кнопки Change profile выводим фрагмент SignIn в CredentialsActivity
        bProfile.setOnClickListener {
            GameModeActivity.show()
        }
        //автоматическая расстановка жуков
        bAutoSetUp.setOnClickListener {
            //gameView.autoPlacing()
        }
        // очистка игровога поля, сброс всех счетчиков для работы логики расстановки жуков
        bCleanFields.setOnClickListener {
            cleanField()
        }

        bAcceptBug.setOnClickListener {
            var sum = 0
            Bugs.chooseHorizontal = 0

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

    override fun onRender(state: GameState) {
        gameView.setGameState(state)
    }
    private fun cleanField() {

        firstPlayerBugs.cleanField()

        tvCountBugFour.text = firstPlayerBugs.fourPartBug.toString()
        tvCountBugThree.text = firstPlayerBugs.threePartBug.toString()
        tvCountBugTwo.text = firstPlayerBugs.twoPartBug.toString()
        tvCountBugOne.text = firstPlayerBugs.onePartBug.toString()

        gameView.render()
    }
    private fun surrounding() {
        firstPlayerBugs.acceptBugSurrounding()
        gameView.render()
    }
}