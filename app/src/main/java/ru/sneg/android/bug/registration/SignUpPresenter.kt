package ru.sneg.android.bug.registration

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.sneg.android.bug.repositories.UserRepository
@InjectViewState
class SignUpPresenter : MvpPresenter<ISignUpView>() {

    var userRepository : UserRepository = UserRepository()

    fun signUp (login: String, pass: String, rPass: String){
        // диалог прогрузки

        userRepository.signUp({

            if(login.isEmpty())
                viewState.showError(message = "поле логин не заполнено")
            if (pass != rPass)
                viewState.showError(message = "пароли не совпадают")

        },login, pass)


    }




}

