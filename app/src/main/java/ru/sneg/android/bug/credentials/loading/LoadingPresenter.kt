package ru.sneg.android.bug.credentials.loading

import android.os.Handler
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.sneg.android.bug.MainActivity
import ru.sneg.android.bug.credentials.CredentialsActivity
import ru.sneg.android.bug.domain.repositories.UserRepository
import javax.inject.Inject

@InjectViewState
class LoadingPresenter : MvpPresenter<ILoadingView> {

    private val userRepository : UserRepository

    @Inject
    constructor(userRepository: UserRepository){
        this.userRepository = userRepository
    }

    override fun onFirstViewAttach() { //метод вызывается только один раз, кода она первый раз появляется и к ней приатачивается презентер
        super.onFirstViewAttach()

        loadStaticResources()
    }

    fun loadStaticResources() {
        Handler().postDelayed({ // Handler - работает с потоками, по дефолту приатачивается к главному потоку

            val user = userRepository.getUser()
            if (user != null){
                CredentialsActivity.show()
                return@postDelayed
            }

            viewState.showAuth()

        }, 2000)
    }
}