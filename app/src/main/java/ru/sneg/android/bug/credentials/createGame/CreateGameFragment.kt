package ru.sneg.android.bug.credentials.createGame

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_game_create.*
import ru.sneg.android.bug.R
import ru.sneg.android.bug.activities.BattleGroundsActivity
import ru.sneg.android.bug.base.ABaseFragment
import ru.sneg.android.bug.domain.di.components.DaggerAppComponent
import javax.inject.Inject

class CreateGameFragment : ABaseFragment(),ICreateGameView {


    @Inject
    @InjectPresenter
    lateinit var presenterCreateGame: CreateGamePresenter

    @ProvidePresenter
    fun providePresenter() = presenterCreateGame

    override fun inject() {
        DaggerAppComponent.create().inject(this)
    }

    override fun getViewId() = R.layout.fragment_game_create

    //------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null)
            return // Не будем пересоздавать фрагмент, пусть берется старый из стека

        bCreateGame.setOnClickListener {
            val gameName = "${gameName.text}"

            if (gameName.isEmpty()) {
                toast(stringId = R.string.game_name_error)
                return@setOnClickListener
            }
            presenterCreateGame.creategame(gameName)
        }
    }

    override fun showError(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }

    override fun showBattleGroundsActivity() {
        BattleGroundsActivity.show()
    }
}