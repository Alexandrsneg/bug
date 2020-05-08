package ru.sneg.android.bug.credentials.bugPlacement

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_bug_placement_player.*
import kotlinx.android.synthetic.main.fragment_bug_placement_player.bAcceptBug
import kotlinx.android.synthetic.main.fragment_bug_placement_player.bAutoSetUp
import kotlinx.android.synthetic.main.fragment_bug_placement_player.bCleanFields
import kotlinx.android.synthetic.main.fragment_bug_placement_player.tvCountBugFour
import kotlinx.android.synthetic.main.fragment_bug_placement_player.tvCountBugOne
import kotlinx.android.synthetic.main.fragment_bug_placement_player.tvCountBugThree
import kotlinx.android.synthetic.main.fragment_bug_placement_player.tvCountBugTwo
import kotlinx.android.synthetic.main.fragment_bug_placement_player_second.*
import ru.sneg.android.bug.R
import ru.sneg.android.bug.activities.GameModeActivity
import ru.sneg.android.bug.activities.routers.IBattleGroundsRouter
import ru.sneg.android.bug.base.ABaseFragment
import ru.sneg.android.bug.credentials.game.bugPlacement.BugPlacementPlayerFragment
import ru.sneg.android.bug.domain.di.components.DaggerAppComponent
import ru.sneg.android.bug.game.UI.PlayingFieldUI
import ru.sneg.android.bug.game.UI.TakeUI
import ru.sneg.android.bug.game.engine.GameState
import javax.inject.Inject
class BugPlacementPlayerSecondFragment : ABaseFragment(),
    IBugPlaycementPlayerSecondView {

    companion object {
        val listBugFour = mutableListOf<Int>()

        val listBugThreeFirst = mutableListOf<Int>()
        val listBugThreeSecond = mutableListOf<Int>()

        val listBugTwoFirst = mutableListOf<Int>()
        val listBugTwoSecond = mutableListOf<Int>()
        val listBugTwoThird = mutableListOf<Int>()

        val listBugOneFirst = mutableListOf<Int>()
        val listBugOneSecond = mutableListOf<Int>()
        val listBugOneThird = mutableListOf<Int>()
        val listBugOneFourth = mutableListOf<Int>()
    }

    @Inject //использование Даггером конструктора из презентера, подставление зависимости
    @InjectPresenter // аннотация Moxy управляет ж. циклом Presenter
    lateinit var presenter: BugPlacementPlayerSecondPresenter

    @ProvidePresenter // предоставление презентера для Moxy
    fun providePresenter() = presenter


    override fun inject() {
        DaggerAppComponent.create().inject(this)
    }

    override fun getViewId() = R.layout.fragment_bug_placement_player_second


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        PlayingFieldUI.fourPartBug = 1
        PlayingFieldUI.threePartBug = 2
        PlayingFieldUI.twoPartBug = 3
        PlayingFieldUI.onePartBug = 4

        PlayingFieldUI.bugsRemaining = 10

        PlayingFieldUI.chooseHorizontal = 0


        tvCountBugFourS.text = PlayingFieldUI.fourPartBug.toString()
        tvCountBugThreeS.text = PlayingFieldUI.threePartBug.toString()
        tvCountBugTwoS.text = PlayingFieldUI.twoPartBug.toString()
        tvCountBugOneS.text = PlayingFieldUI.onePartBug.toString()


        gameViewSecond.onSelectListener = {
            println(it)
            presenter.onCell(it)
        }

        //автоматическая расстановка жуков
        bAutoSetUp.setOnClickListener {
           // gameViewSecond.autoPlacing()
        }

        // смена фрагмента на фрагмент игры офлайн
        bForwardSecond.setOnClickListener {
            if(PlayingFieldUI.bugsRemaining == 0) {
                activity?.let {
                    if (it is IBattleGroundsRouter)
                        it.showBugVsBugGame()
                }
            }
            else toast(stringId = R.string.not_enougth_bugs_on_field)
        }

        // очистка игровогo поля, сброс всех счетчиков для работы логики расстановки жуков
        bCleanFields.setOnClickListener {
            clean()
        }

        bAcceptBug.setOnClickListener {
            var sum: Int = 0
            PlayingFieldUI.chooseHorizontal = 0

            for (i in 0..99) {
                if ( PlayingFieldUI.takesPlayerTwo[i].state == 1 ) {
                    sum += PlayingFieldUI.takesPlayerTwo[i].state
                }
            }

            if (PlayingFieldUI.bugsRemaining == 10 && sum > 4 ){
                toast(stringId = R.string.extra_bugs_on_field)
            }
            if (PlayingFieldUI.bugsRemaining == 10 && sum < 4 ){
                toast(stringId = R.string.not_enougth_bugs_on_field)
            }

            if (PlayingFieldUI.bugsRemaining == 10 && sum == 4) {
                surrounding(PlayingFieldUI.takesPlayerTwo)
                PlayingFieldUI.fourPartBug--
                tvCountBugFourS.text = PlayingFieldUI.fourPartBug.toString()
                PlayingFieldUI.bugsRemaining--
                return@setOnClickListener
            }

            if (PlayingFieldUI.bugsRemaining in 8..9 && sum == (4 + (9 - 3*PlayingFieldUI.threePartBug))) {
                surrounding(PlayingFieldUI.takesPlayerTwo)
                PlayingFieldUI.threePartBug--
                tvCountBugThreeS.text = PlayingFieldUI.threePartBug.toString()
                PlayingFieldUI.bugsRemaining--
                return@setOnClickListener
            }
            if (PlayingFieldUI.bugsRemaining in 8..9 && sum > (4 + (9 - 3*PlayingFieldUI.threePartBug))){
                toast(stringId = R.string.extra_bugs_on_field)
            }
            if (PlayingFieldUI.bugsRemaining in 8..9 && sum < (4 + (9 - 3*PlayingFieldUI.threePartBug))){
                toast(stringId = R.string.not_enougth_bugs_on_field)
            }

            if (PlayingFieldUI.bugsRemaining in 5..7 && sum == (10 + (8 - 2*PlayingFieldUI.twoPartBug))) {
                surrounding(PlayingFieldUI.takesPlayerTwo)
                PlayingFieldUI.twoPartBug--
                tvCountBugTwoS.text = PlayingFieldUI.twoPartBug.toString()
                PlayingFieldUI.bugsRemaining--
                return@setOnClickListener
            }
            if (PlayingFieldUI.bugsRemaining in 5..7 && sum > (10 + (8 - 2*PlayingFieldUI.twoPartBug))){
                toast(stringId = R.string.extra_bugs_on_field)
            }
            if (PlayingFieldUI.bugsRemaining in 5..7 && sum < (10 + (8 - 2*PlayingFieldUI.twoPartBug))){
                toast(stringId = R.string.not_enougth_bugs_on_field)
            }

            if (PlayingFieldUI.bugsRemaining in 1..4 && sum == (16 + (5 - PlayingFieldUI.onePartBug))) {
                surrounding(PlayingFieldUI.takesPlayerTwo)
                PlayingFieldUI.onePartBug--
                tvCountBugOneS.text = PlayingFieldUI.onePartBug.toString()
                PlayingFieldUI.bugsRemaining--
                return@setOnClickListener
            }
            if (PlayingFieldUI.bugsRemaining in 1..4 && sum > (16 + (5 - PlayingFieldUI.onePartBug))){
                toast(stringId = R.string.extra_bugs_on_field)
            }
            if (PlayingFieldUI.bugsRemaining in 1..4 && sum < (16 + (5 - PlayingFieldUI.onePartBug))){
                toast(stringId = R.string.not_enougth_bugs_on_field)
            }
        }
    }

    override fun onRender(state: GameState) {
        gameViewSecond.setGameStateSecond(state)
    }

    private fun clean() {
        PlayingFieldUI.fourPartBug = 1
        PlayingFieldUI.threePartBug = 2
        PlayingFieldUI.twoPartBug = 3
        PlayingFieldUI.onePartBug = 4

        PlayingFieldUI.bugsRemaining = 10

        tvCountBugFourS.text = PlayingFieldUI.fourPartBug.toString()
        tvCountBugThreeS.text = PlayingFieldUI.threePartBug.toString()
        tvCountBugTwoS.text = PlayingFieldUI.twoPartBug.toString()
        tvCountBugOneS.text = PlayingFieldUI.onePartBug.toString()

        PlayingFieldUI.chooseHorizontal = 0
        for (index in 0..99) {
            PlayingFieldUI.takesPlayerTwo[index].state = 0
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

        gameViewSecond.render()
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
        gameViewSecond.render()
    }
}






