package ru.sneg.android.bug.credentials.createGame

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.sneg.android.bug.domain.repositories.GameRepository
import javax.inject.Inject

@InjectViewState
class CreateGamePresenter: MvpPresenter<ICreateGameView> {
    @Inject
    lateinit var gameRepository: GameRepository

    @Inject
    constructor()
    fun creategame(nameGame: String) {
        gameRepository.createGame({
            viewState.showBattleGroundsActivity()
        }, nameGame)
    }
}