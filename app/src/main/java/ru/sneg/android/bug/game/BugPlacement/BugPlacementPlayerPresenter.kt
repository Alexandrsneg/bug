package ru.sneg.android.bug.game.bugPlacement

import android.os.Handler
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.sneg.android.bug.domain.repositories.GameRepository
import ru.sneg.android.bug.domain.repositories.UserRepository
import javax.inject.Inject

@InjectViewState
class BugPlacementPlayerPresenter : MvpPresenter<IBugPlaycementPlayerView> {

    private val gameRepository: GameRepository
    private val userRepository: UserRepository
   // private val handler: Handler()



    @Inject
    constructor(gameRepository: GameRepository, userRepository: UserRepository){
        this.gameRepository = gameRepository
        this.userRepository = userRepository
    }


    fun BugPlacementPlayer (str: String){

        gameRepository.BugPlacementPlayerShow()

    }

}