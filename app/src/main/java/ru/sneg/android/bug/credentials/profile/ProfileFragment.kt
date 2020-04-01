package ru.sneg.android.bug.credentials.profile

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_profile.*
import ru.sneg.android.bug.R
import ru.sneg.android.bug.base.ABaseFragment
import ru.sneg.android.bug.domain.di.components.DaggerAppComponent
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

     /*   val name  = "${tvName.text}"
        val score = "${tvScore.text}"
        val wins = "${tvWins.text}"
        val loses = "${tvLoses.text}"

        //отображение достижений игрока
        presenter.showAchieves(name, score, wins, loses)*/
/*
    //при нажатии кнопки Change profile переходим на main экран
    fun changeProfile(view: View) {
        val intent = Intent(this@ProfileFragment, MainActivity::class.java)
        startActivity(intent)
    }

    //при нажатии кнопки Game mode переходим на экран выбора режима игры
    fun gameModeBtn(view: View) {
        val intent = Intent(this@ProfileFragment, GameModeActivity::class.java)
        startActivity(intent)
    }*/
    }
}
