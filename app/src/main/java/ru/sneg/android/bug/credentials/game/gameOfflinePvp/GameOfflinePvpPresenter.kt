package ru.sneg.android.bug.credentials.game.gameOfflinePvp

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.sneg.android.bug.domain.repositories.GameRepository
import ru.sneg.android.bug.domain.repositories.UserRepository
import ru.sneg.android.bug.game.engine.NetworkPlayer
import javax.inject.Inject

@InjectViewState
class GameOfflinePvpPresenter: MvpPresenter<IGameOfflinePvpView> {

    private val gameRepository: GameRepository
    private val userRepository: UserRepository
    private var player: NetworkPlayer? = null

    @Inject
    constructor(gameRepository: GameRepository, userRepository: UserRepository){
        this.gameRepository = gameRepository
        this.userRepository = userRepository
    }
}