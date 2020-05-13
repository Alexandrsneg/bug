package ru.sneg.android.bug.credentials.gameModes

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_game_mode.*
import kotlinx.android.synthetic.main.fragment_game_mode.bScore
import kotlinx.android.synthetic.main.fragment_profile.*
import ru.sneg.android.bug.R
import ru.sneg.android.bug.activities.GameActivity
import ru.sneg.android.bug.activities.CreateGameActivity
import ru.sneg.android.bug.activities.GameModeActivity
import ru.sneg.android.bug.activities.ScoreActivity
import ru.sneg.android.bug.base.ABaseFragment
import ru.sneg.android.bug.domain.di.components.DaggerAppComponent
import javax.inject.Inject

class GameModeFragment : ABaseFragment(), IGameModeView {

    @Inject
    @InjectPresenter
    lateinit var presenter: GameModePresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun inject() {
        DaggerAppComponent.create().inject(this)
    }

    override fun getViewId() = R.layout.fragment_game_mode

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null)
            return

        //игра против бота
        bBugVsCpuBug.setOnClickListener() {
            GameActivity.show()
        }
        //игра на одном устройстве
        bBugVsBug.setOnClickListener() {
            GameActivity.show()
        }
        //игра по сети
        bBugVsRandomBug.setOnClickListener() {
            CreateGameActivity.show()
        }
        //кнопка перехода на таблицу рекордов
        bScore.setOnClickListener {
            ScoreActivity.show()
        }
        //кнопка перехода на профиль
        bProfile.setOnClickListener {
            GameModeActivity.show()
        }
    }

    override fun lock() {

    }

    override fun unlock() {

    }
}
