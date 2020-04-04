package ru.sneg.android.bug.credentials.gameModes


import com.arellomobile.mvp.MvpPresenter

import ru.sneg.android.bug.domain.repositories.UserRepository
import javax.inject.Inject

class GameModePresenter : MvpPresenter<IGameModeView> {
    @Inject
    lateinit var userRepository : UserRepository

    @Inject
    constructor()


}