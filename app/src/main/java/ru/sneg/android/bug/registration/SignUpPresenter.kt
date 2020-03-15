package ru.sneg.android.bug.registration

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.sneg.android.bug.repositories.UserRepository
@InjectViewState
class SignUpPresenter : MvpPresenter<ISignUpView>() {

    var userRepository : UserRepository = UserRepository()

    fun signUp (login: String, pass: String){
        // диалог прогрузки

        userRepository.signUp({
        viewState.showError("Ошибка ввода");
        },login, pass)


    }




}

