package ru.sneg.android.bug.auth

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.sneg.android.bug.domain.repositories.UserRepository
@InjectViewState
class SignInPresenter : MvpPresenter<ISignInView>() {

    var userRepository : UserRepository = UserRepository()

    fun signIn (login: String, pass: String){
        // диалог прогрузки
        userRepository.signIn({
            // закрыть диалог прогрузки
            if(login.isEmpty())
                viewState.showError(message = "поле логин не заполнено")
            if (pass.isEmpty())
                viewState.showError(message = "Поле пароль незаполнено")

        },login, pass)

        fun validation(login:String , pass:String){


        }

    }
}