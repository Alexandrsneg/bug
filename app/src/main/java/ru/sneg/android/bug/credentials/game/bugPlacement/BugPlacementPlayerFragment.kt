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
import ru.sneg.android.bug.game.UI.PlayingFieldUI
import ru.sneg.android.bug.game.UI.PlayingFieldUI.Companion.takes
import ru.sneg.android.bug.game.UI.TakeUI
import ru.sneg.android.bug.game.engine.GameState
import java.lang.IndexOutOfBoundsException
import javax.inject.Inject
class BugPlacementPlayerFragment : ABaseFragment(),
    IBugPlaycementPlayerView {

companion object {
    var listBugFour = mutableListOf<Int>()

    var listBugThreeFirst = mutableListOf<Int>()
    var listBugThreeSecond = mutableListOf<Int>()

    var listBugTwoFirst = mutableListOf<Int>()
    var listBugTwoSecond = mutableListOf<Int>()
    var listBugTwoThird = mutableListOf<Int>()

    var listBugOneFirst = mutableListOf<Int>()
    var listBugOneSecond = mutableListOf<Int>()
    var listBugOneThird = mutableListOf<Int>()
    var listBugOneFourth = mutableListOf<Int>()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvCountBugFour.text = PlayingFieldUI.fourPartBug.toString()
        tvCountBugThree.text = PlayingFieldUI.threePartBug.toString()
        tvCountBugTwo.text = PlayingFieldUI.twoPartBug.toString()
        tvCountBugOne.text = PlayingFieldUI.onePartBug.toString()

        gameView.onSelectListener = {
            println(it)
            presenter.onCell(it)
        }

// смена фрагмента на расстановку жуков для второго игрока
        bForward.setOnClickListener {
            if (PlayingFieldUI.bugsRemaining == 0) {
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
            gameView.autoPlacing()
        }

        // очистка игровога поля, сброс всех счетчиков для работы логики расстановки жуков
        bCleanFields.setOnClickListener {
            clean()
        }

        bAcceptBug.setOnClickListener {
            var sum: Int = 0
            PlayingFieldUI.chooseHorizontal = 0
            surrounding(PlayingFieldUI.takes)

            for (i in 0..99) {
                if (takes[i].state == 1) {
                    sum += takes[i].state
                }
            }

            if (PlayingFieldUI.bugsRemaining == 10 && sum > 4) {
                toast(stringId = R.string.extra_bugs_on_field)
            }
            if (PlayingFieldUI.bugsRemaining == 10 && sum < 4) {
                toast(stringId = R.string.not_enougth_bugs_on_field)
            }

            if (PlayingFieldUI.bugsRemaining == 10 && sum == 4) {
                PlayingFieldUI.fourPartBug--
                tvCountBugFour.text = PlayingFieldUI.fourPartBug.toString()
                PlayingFieldUI.bugsRemaining--
                return@setOnClickListener
            }

            if (PlayingFieldUI.bugsRemaining in 8..9 && sum == (4 + (9 - 3 * PlayingFieldUI.threePartBug))) {
                PlayingFieldUI.threePartBug--
                tvCountBugThree.text = PlayingFieldUI.threePartBug.toString()
                PlayingFieldUI.bugsRemaining--
                return@setOnClickListener
            }
            if (PlayingFieldUI.bugsRemaining in 8..9 && sum > (4 + (9 - 3 * PlayingFieldUI.threePartBug))) {
                toast(stringId = R.string.extra_bugs_on_field)
            }
            if (PlayingFieldUI.bugsRemaining in 8..9 && sum < (4 + (9 - 3 * PlayingFieldUI.threePartBug))) {
                toast(stringId = R.string.not_enougth_bugs_on_field)
            }

            if (PlayingFieldUI.bugsRemaining in 5..7 && sum == (10 + (8 - 2 * PlayingFieldUI.twoPartBug))) {
                PlayingFieldUI.twoPartBug--
                tvCountBugTwo.text = PlayingFieldUI.twoPartBug.toString()
                PlayingFieldUI.bugsRemaining--
                return@setOnClickListener
            }
            if (PlayingFieldUI.bugsRemaining in 5..7 && sum > (10 + (8 - 2 * PlayingFieldUI.twoPartBug))) {
                toast(stringId = R.string.extra_bugs_on_field)
            }
            if (PlayingFieldUI.bugsRemaining in 5..7 && sum < (10 + (8 - 2 * PlayingFieldUI.twoPartBug))) {
                toast(stringId = R.string.not_enougth_bugs_on_field)
            }

            if (PlayingFieldUI.bugsRemaining in 1..4 && sum == (16 + (5 - PlayingFieldUI.onePartBug))) {
                PlayingFieldUI.onePartBug--
                tvCountBugOne.text = PlayingFieldUI.onePartBug.toString()
                PlayingFieldUI.bugsRemaining--
                return@setOnClickListener
            }
            if (PlayingFieldUI.bugsRemaining in 1..4 && sum > (16 + (5 - PlayingFieldUI.onePartBug))) {
                toast(stringId = R.string.extra_bugs_on_field)
            }
            if (PlayingFieldUI.bugsRemaining in 1..4 && sum < (16 + (5 - PlayingFieldUI.onePartBug))) {
                toast(stringId = R.string.not_enougth_bugs_on_field)
            }
        }
    }

    override fun onRender(state: GameState) {
        gameView.setGameState(state)
    }

    private fun clean() {
        PlayingFieldUI.fourPartBug = 1
        PlayingFieldUI.threePartBug = 2
        PlayingFieldUI.twoPartBug = 3
        PlayingFieldUI.onePartBug = 4

        PlayingFieldUI.bugsRemaining = 10

        tvCountBugFour.text = PlayingFieldUI.fourPartBug.toString()
        tvCountBugThree.text = PlayingFieldUI.threePartBug.toString()
        tvCountBugTwo.text = PlayingFieldUI.twoPartBug.toString()
        tvCountBugOne.text = PlayingFieldUI.onePartBug.toString()

        PlayingFieldUI.chooseHorizontal = 0
        for (index in 0..99) {
            takes[index].state = 0
        }

        listBugFour.clear()

        listBugThreeFirst.clear()
        listBugThreeSecond.clear()

        listBugTwoFirst.clear()
        listBugTwoSecond.clear()
        listBugTwoThird.clear()

        listBugOneFirst.clear()
        listBugOneSecond.clear()
        listBugOneThird.clear()
        listBugOneFourth.clear()

        gameView.render()
    }

    private fun surrounding(tk: MutableList<TakeUI>) {

        for (i: Int in 0..99) {
            if (tk[i].state == 1) {

                // обводка клетки сверху
                if (i !in 0..9) {
                    if (tk[i - 10].state == 0){
                        try {
                           // tk[i - 11].state = 4;
                            tk[i - 10].state = 4;
                           // tk[i - 9].state = 4
                        } catch (e: ArrayIndexOutOfBoundsException) { println ("Exception") }
                    }
                }
                // обводка клетки снизу
                if(i !in 90..99) {
                    if (tk[i + 10].state == 0) {
                        try {
                            tk[i + 10].state = 4;
                        } catch (e: Exception) { println ("Exception") }
                    }
                }
                // обводка клетки слева
                val left = listOf<Int>(0,10,20,30,40,50,60,70,80,90)
                if (i !in left) {
                    if (tk[i - 1].state == 0 || tk[i - 1].state == 4) {
                        tk[i - 1].state = 4;
                        try {
                            tk[i + 9].state = 4
                        } catch (e: Exception) { println("Exception") }
                        try {
                            tk[i - 11].state = 4;
                        } catch (e: Exception) { println("Exception") }
                    }
                }
                // обводка клетки справа
                val right = listOf<Int>(9,19,29,39,49,59,69,79,89,99)
                if (i !in right) {
                    if (tk[i + 1].state == 0 || tk[i + 1].state == 4) {
                        tk[i + 1].state = 4;
                        try {
                            tk[i + 11].state = 4;
                        } catch (e: Exception) { println("Exception") }
                        try {
                            tk[i - 9].state = 4
                        } catch (e: Exception) { println("Exception") }
                    }
                }

            }
        }
        gameView.render()
    }
  //удаление значений в списках жуков при очищении поля
}








