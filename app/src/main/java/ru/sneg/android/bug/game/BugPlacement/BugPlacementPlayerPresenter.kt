package ru.sneg.android.bug.game.BugPlacement

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.sneg.android.bug.domain.repositories.GameRepository
import javax.inject.Inject

@InjectViewState
class BugPlacementPlayerPresenter : MvpPresenter<IBugPlaycementPlayerView> {

    @Inject
    lateinit var gameRepository: GameRepository

    @Inject
    constructor()

    fun BugPlacementPlayer (str: String){

        gameRepository.BugPlacementPlayerShow()

    }

}