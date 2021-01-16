package ru.sneg.android.bug.credentials.loading

import android.os.Handler
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.sneg.android.bug.activities.GameModeActivity
import ru.sneg.android.bug.domain.repositories.UserRepository
import javax.inject.Inject

@InjectViewState
class LoadingPresenter @Inject constructor(private val userRepository: UserRepository) :
    MvpPresenter<ILoadingView>() {

    override fun onFirstViewAttach() { //метод вызывается только один раз, кода она первый раз появляется и к ней приатачивается презентер
        super.onFirstViewAttach()
       loadStaticResources()
    }

    private fun loadStaticResources() {
        Handler().postDelayed({ // Handler - работает с потоками, по дефолту приатачивается к главному потоку
            val user = userRepository.getUser()
            user?.let {
                GameModeActivity.show()
                return@postDelayed
            }

            viewState.showAuth()
        }, 2000)
    }
}