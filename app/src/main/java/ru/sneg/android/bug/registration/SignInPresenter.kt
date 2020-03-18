package ru.sneg.android.bug.registration

import com.arellomobile.mvp.MvpPresenter
import ru.sneg.android.bug.repositories.UserRepository

class SignInPresenter : MvpPresenter<ISignInView>() {

    var userRepository : UserRepository = UserRepository()

    fun signIn (login: String, pass: String){
        // диалог прогрузки

        userRepository.signUp({

            viewState.showError("Ошибка ввода");

        },login, pass)

    }
}