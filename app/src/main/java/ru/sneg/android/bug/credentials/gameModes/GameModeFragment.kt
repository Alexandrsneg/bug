package ru.sneg.android.bug.credentials.gameModes

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_game_mode.*
import ru.sneg.android.bug.BugPlacementPlayerActivity
import ru.sneg.android.bug.R
import ru.sneg.android.bug.activities.GameModeActivity
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
            GameModeActivity.show()
        }
        //игра на одном устройстве
        bBugVsBug.setOnClickListener() {
            GameModeActivity.show()
        }
        //игра по сети
        bBugVsRandomBug.setOnClickListener() {
            GameModeActivity.show()
        }
    }

    override fun lock() {

    }

    override fun unlock() {

    }
}
