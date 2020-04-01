package ru.sneg.android.bug.credentials.profile

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.sneg.android.bug.MainActivity
import ru.sneg.android.bug.base.SubRX
import ru.sneg.android.bug.domain.repositories.UserRepository
import javax.inject.Inject

@InjectViewState
class ProfilePresenter : MvpPresenter<IProfileView> {

    @Inject
    lateinit var userRepository : UserRepository

    @Inject
    constructor()

    /*fun showAchieves(name: String, score: Int, wins: Int, loses: Int){
        viewState.lock()
        userRepository.achivies(SubRX { _, e ->
            viewState.unlock()

            if (e != null) {
                e.printStackTrace()
                viewState.onError(e.localizedMessage)
                return@SubRX
            }

            MainActivity.show()

        },name, score, wins, loses)

    }*/

}