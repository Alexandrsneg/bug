package ru.sneg.android.bug.credentials.registration

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.sneg.android.bug.domain.repositories.UserRepository
import javax.inject.Inject


@InjectViewState // аннотация привязывает ViewState к Presenter, генерирует код ViewState

class SignUpPresenter : MvpPresenter<ISignUpView>() {
    @Inject
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

