package ru.sneg.android.bug.credentials.registration

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.sneg.android.bug.MainActivity
import ru.sneg.android.bug.base.SubRX
import ru.sneg.android.bug.credentials.CredentialsActivity
import ru.sneg.android.bug.domain.repositories.UserRepository
import javax.inject.Inject


@InjectViewState // аннотация привязывает ViewState к Presenter, генерирует код ViewState

class SignUpPresenter : MvpPresenter<ISignUpView> {
    @Inject
    lateinit var userRepository : UserRepository /* lateinit - фича Котлина проставляется в зависимостях, обеспечивает NullSafe
                                                   "var будет определена, но чуть позже"*/


        @Inject      /*предоставление зависимостей (презентера во фрагмент) вызовом конструктора;
                     механизм создающий дагер делается на основе интерфейса AppComponent помечается аннотацией @component*/
        constructor()

    fun signUp(login:String,pass:String){
        viewState.lock()
        userRepository.login(SubRX { _, e ->
            viewState.unlock()

            if (e != null) {
                e.printStackTrace()
                viewState.onError(e.localizedMessage)
                return@SubRX
            }

            MainActivity.show()

        },login,pass)

    }




}

