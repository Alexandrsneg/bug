package ru.sneg.android.bug.credentials.registration

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.sneg.android.bug.activities.GameModeActivity
import ru.sneg.android.bug.base.SubRX
import ru.sneg.android.bug.domain.repositories.UserRepository
import ru.sneg.android.bug.game.engine.GameState
import javax.inject.Inject


@InjectViewState // аннотация привязывает ViewState к Presenter, генерирует код ViewState

class SignUpPresenter : MvpPresenter<ISignUpView> {

    private val userRepository : UserRepository


        @Inject      /*предоставление зависимостей (презентера во фрагмент) вызовом конструктора;
                     механизм создающий дагер делается на основе интерфейса AppComponent помечается аннотацией @component*/
        constructor(userRepository : UserRepository){
            this.userRepository = userRepository
        }

    fun signUp(login:String,pass:String){
        viewState.lock()
        userRepository.registration(SubRX { _, e ->
            viewState.unlock()

            if (e != null) {
                e.printStackTrace()
                viewState.onError(e.localizedMessage)
                return@SubRX
            }

            GameModeActivity.show()

        },login,pass)

    }




}

