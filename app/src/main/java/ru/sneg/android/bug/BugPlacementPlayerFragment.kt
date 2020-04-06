package ru.sneg.android.bug

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_bug_placement_player.*
import ru.sneg.android.bug.activities.routers.ICredentialsRouter
import ru.sneg.android.bug.base.ABaseFragment
import ru.sneg.android.bug.credentials.profile.ProfilePresenter
import ru.sneg.android.bug.domain.di.components.DaggerAppComponent
import javax.inject.Inject

class BugPlacementPlayerFragment : ABaseFragment() {

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


        //при нажатии кнопки Change profile выводим фрагмент SignIn в CredentialsActivity
        bProfile.setOnClickListener {
            activity?.let {
                if (it is ICredentialsRouter)
                    it.showSignIn()
            }
        }
    }
}




