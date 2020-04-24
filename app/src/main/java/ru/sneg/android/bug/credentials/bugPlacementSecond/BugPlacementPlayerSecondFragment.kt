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
import ru.sneg.android.bug.base.ABaseFragment
import ru.sneg.android.bug.domain.di.components.DaggerAppComponent
import ru.sneg.android.bug.game.UI.PlayingFieldUI
import ru.sneg.android.bug.game.UI.TakeUI
import ru.sneg.android.bug.game.engine.GameState
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        PlayingFieldUI.fourPartBug = 1
        PlayingFieldUI.threePartBug = 2
        PlayingFieldUI.twoPartBug = 3
        PlayingFieldUI.onePartBug = 4

        PlayingFieldUI.bugsRemaining = 10

        PlayingFieldUI.chooseHorizontal = 0

       //gameViewSecond.render()

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

            gameViewSecond.autoPlacing()
        }

        // очистка игровога поля, сброс всех счетчиков для работы логики расстановки жуков
        bCleanFields.setOnClickListener {

           /* val animation = AnimationUtils.loadAnimation(context, R.anim.scale)
            bCleanFields.startAnimation(animation)*/

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
            gameViewSecond.render()
        }


        bAcceptBug.setOnClickListener {
            PlayingFieldUI.chooseHorizontal = 0

            var sum: Int = 0
            for (i in 0..99) {
                sum += PlayingFieldUI.takesPlayerTwo[i].state
            }

            if (PlayingFieldUI.bugsRemaining == 10 && sum > 4 ){
                toast(stringId = R.string.extra_bugs_on_field)
            }
            if (PlayingFieldUI.bugsRemaining == 10 && sum < 4 ){
                toast(stringId = R.string.not_enougth_bugs_on_field)
            }

            if (PlayingFieldUI.bugsRemaining == 10 && sum == 4) {
                PlayingFieldUI.fourPartBug--
                tvCountBugFourS.text = PlayingFieldUI.fourPartBug.toString()
                PlayingFieldUI.bugsRemaining--
                return@setOnClickListener
            }

            if (PlayingFieldUI.bugsRemaining in 8..9 && sum == (4 + (9 - 3*PlayingFieldUI.threePartBug))) {
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

    fun sumChek(){
    }
    override fun onRender(state: GameState) {
        gameViewSecond.setGameStateSecond(state)
    }
}






