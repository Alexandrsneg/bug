package ru.sneg.android.bug.credentials.auth

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.sneg.android.bug.activities.GameModeActivity
import ru.sneg.android.bug.base.SubRX
import ru.sneg.android.bug.domain.repositories.UserRepository
import javax.inject.Inject

@InjectViewState
class SignInPresenter @Inject constructor(private val userRepository: UserRepository) :
    MvpPresenter<ISignInView>() {


    fun signIn (login: String, password: String){
        viewState.lock()
        userRepository.login(SubRX { _, e ->
            viewState.unlock()
            if (e != null) {
                e.printStackTrace()
                viewState.onError(e.localizedMessage)
                return@SubRX
            }

            GameModeActivity.show()
        }, login, password)
    }
}