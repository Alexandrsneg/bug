package ru.sneg.android.bug.credentials.game.gameOfflinePvp

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.sneg.android.bug.credentials.game.bugPlacement.BugPlacementPlayerFragment
import ru.sneg.android.bug.domain.repositories.GameRepository
import ru.sneg.android.bug.domain.repositories.UserRepository
import ru.sneg.android.bug.game.UI.TakeUI
import ru.sneg.android.bug.game.engine.NetworkPlayer
import javax.inject.Inject

@InjectViewState
class GameOfflinePvpPresenter: MvpPresenter<IGameOfflinePvpView> {

    lateinit var bugPlacement: BugPlacementPlayerFragment

    private val gameRepository: GameRepository
    private val userRepository: UserRepository
    private var player: NetworkPlayer? = null

    @Inject
    constructor(gameRepository: GameRepository, userRepository: UserRepository){
        this.gameRepository = gameRepository
        this.userRepository = userRepository
    }

    //метод проверки убит ли жук
 fun killCheck(listBug : MutableList<Int>, tk: MutableList<TakeUI>): Boolean{
     var killed : Boolean = false
     for (i in listBug){
         killed = tk[i].state == 3
     }
     return killed
 }

    //метод смена хода
    fun onChangeTurn(){

    }



}