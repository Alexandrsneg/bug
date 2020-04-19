package ru.sneg.android.bug.credentials.bugPlacement

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_bug_placement_player.*
import ru.sneg.android.bug.R
import ru.sneg.android.bug.activities.GameModeActivity
import ru.sneg.android.bug.activities.routers.ICredentialsRouter
import ru.sneg.android.bug.base.ABaseFragment
import ru.sneg.android.bug.domain.di.components.DaggerAppComponent
import ru.sneg.android.bug.game.GameView
import ru.sneg.android.bug.game.UI.PlayingFieldUI
import ru.sneg.android.bug.game.engine.GameState
import ru.sneg.android.bug.game.engine.NetworkPlayer
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


        //при нажатии кнопки Change profile выводим фрагмент SignIn в CredentialsActivity
        bProfile.setOnClickListener {
            GameModeActivity.show()
            }
        //автоматическая расстановка жуков
        bAutoSetUp.setOnClickListener {
            gameView.autoPlacing()
        }

        bCountBugFour.setOnClickListener {
            PlayingFieldUI.chooseHorizontal = 0

            if (PlayingFieldUI.fourPartBug > 0) {
                PlayingFieldUI.fourPartBug--
            }
            tvCountBugFour.text = PlayingFieldUI.fourPartBug.toString()
        }
        bCountBugsThree.setOnClickListener {
            PlayingFieldUI.chooseHorizontal = 0

            if (PlayingFieldUI.threePartBug > 0) {
                PlayingFieldUI.threePartBug--
            }
            tvCountBugThree.text = PlayingFieldUI.threePartBug.toString()
        }
        bCountBugsTwo.setOnClickListener {
            PlayingFieldUI.chooseHorizontal = 0

            if (PlayingFieldUI.twoPartBug > 0) {
                PlayingFieldUI.twoPartBug--
            }
            tvCountBugTwo.text = PlayingFieldUI.twoPartBug.toString()
        }
        bCountBugsOne.setOnClickListener {
            PlayingFieldUI.chooseHorizontal = 0

            if (PlayingFieldUI.onePartBug > 0) {
                PlayingFieldUI.onePartBug--
            }
            tvCountBugOne.text = PlayingFieldUI.onePartBug.toString()
        }
    }

    override fun onRender(state: GameState) {
        gameView.setGameState(state)
    }
}






