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
import ru.sneg.android.bug.game.engine.GameState
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
                if(PlayingFieldUI.bugsRemaining == 0) {
                    activity?.let {
                        if (it is IBattleGroundsRouter)
                            it.showBugPlaycementSecond()
                    }
                }
                else toast(stringId = R.string.not_enougth_bugs_on_field)
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

           /* val animation = AnimationUtils.loadAnimation(context, R.anim.scale)
            bCleanFields.startAnimation(animation)*/
            clean()
        }


        bAcceptBug.setOnClickListener {
            var sum: Int = 0
            PlayingFieldUI.chooseHorizontal = 0

            for (i in 0..99) {
                if ( PlayingFieldUI.takes[i].state == 1 ) {
                    sum += PlayingFieldUI.takes[i].state
                }
            }

            if (PlayingFieldUI.bugsRemaining == 10 && sum > 4 ){
                toast(stringId = R.string.extra_bugs_on_field)
            }
            if (PlayingFieldUI.bugsRemaining == 10 && sum < 4 ){
                toast(stringId = R.string.not_enougth_bugs_on_field)
            }

            if (PlayingFieldUI.bugsRemaining == 10 && sum == 4) {
                PlayingFieldUI.fourPartBug--
                tvCountBugFour.text = PlayingFieldUI.fourPartBug.toString()
                PlayingFieldUI.bugsRemaining--
                return@setOnClickListener
            }

            if (PlayingFieldUI.bugsRemaining in 8..9 && sum == (4 + (9 - 3*PlayingFieldUI.threePartBug))) {
                PlayingFieldUI.threePartBug--
                tvCountBugThree.text = PlayingFieldUI.threePartBug.toString()
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
                tvCountBugTwo.text = PlayingFieldUI.twoPartBug.toString()
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
                tvCountBugOne.text = PlayingFieldUI.onePartBug.toString()
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
        gameView.setGameState(state)
    }

    fun clean(){
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
            PlayingFieldUI.takes[index].state = 0
        }
        gameView.render()
    }
}






