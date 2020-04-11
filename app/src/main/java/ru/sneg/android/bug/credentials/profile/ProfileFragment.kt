package ru.sneg.android.bug.credentials.profile

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_profile.*
import ru.sneg.android.bug.R
import ru.sneg.android.bug.activities.CredentialsActivity
import ru.sneg.android.bug.activities.GameModeActivity
import ru.sneg.android.bug.activities.ScoreActivity
import ru.sneg.android.bug.activities.routers.ICredentialsRouter
import ru.sneg.android.bug.activities.routers.IGameModeRouter
import ru.sneg.android.bug.base.ABaseFragment
import ru.sneg.android.bug.domain.di.components.DaggerAppComponent
import ru.sneg.android.bug.domain.repositories.models.realm.UserRealm
import javax.inject.Inject

class ProfileFragment : ABaseFragment(), IProfileView {

    @Inject //использование Даггером конструктора из презентера, подставление зависимости
    @InjectPresenter // аннотация Moxy управляет ж. циклом Presenter
    lateinit var presenter: ProfilePresenter

    @ProvidePresenter // предоставление презентера для Moxy
    fun providePresenter() = presenter

    override fun inject() {
        DaggerAppComponent.create().inject(this)
    }

    override fun getViewId() = R.layout.fragment_profile


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

      // ЗДЕСЬ ДОЛЖНЫ ОТОБРАЖАТЬСЯ РЕЗУЛЬТАТЫ КОНКРЕТНОГО ПОЛЬЗОВАТЕЛЯ

        var name = UserRealm().login //берем имя-логин из реалм модели

        // пока абстрактные методы для полученния неободимх данных
        var score = presenter.showScore()
        var wins = presenter.showWins()
        var loses = presenter.showLoses()


        //отображение достижений игрока



        //при нажатии кнопки Change profile переходим
        bChangeProfile.setOnClickListener {
           CredentialsActivity.show()
        }

        //при нажатии кнопки Game mode переходим на экран выбора режима игры
        bGameMode.setOnClickListener {
            activity?.let {
                if (it is IGameModeRouter)
                    it.showGameModes()
            }
        }

        bScore.setOnClickListener {
            ScoreActivity.show()
        }
    }

    override fun showProfile() {

    }
}
