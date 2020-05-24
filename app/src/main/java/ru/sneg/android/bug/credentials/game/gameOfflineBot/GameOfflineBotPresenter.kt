package ru.sneg.android.bug.credentials.game.gameOfflineBot

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.sneg.android.bug.credentials.game.bugPlacement.BugPlacementPlayerFragment
import ru.sneg.android.bug.game.engine.players.NetworkPlayer
import javax.inject.Inject
import kotlin.random.Random

@InjectViewState
class GameOfflineBotPresenter: MvpPresenter<IGameOfflineBotView> {

    lateinit var bugPlacement: BugPlacementPlayerFragment


    private var player: NetworkPlayer? = null

    @Inject
    constructor(){
    }

    fun whoIsFirst(): Int {
        val random = Random(System.nanoTime())
        return random.nextInt(1,3)
    }

}