package ru.sneg.android.bug.credentials.bugPlacement

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.sneg.android.bug.domain.repositories.GameRepository
import ru.sneg.android.bug.domain.repositories.UserRepository
import ru.sneg.android.bug.game.UI.TakeUI
import ru.sneg.android.bug.game.engine.players.NetworkPlayer
import javax.inject.Inject

@InjectViewState
class BugPlacementPlayerSecondPresenter : MvpPresenter<IBugPlaycementPlayerSecondView> {

    private val gameRepository: GameRepository
    private val userRepository: UserRepository
    private var player: NetworkPlayer? = null
   // private val handler = Handler()



    @Inject
    constructor(gameRepository: GameRepository, userRepository: UserRepository){
        this.gameRepository = gameRepository
        this.userRepository = userRepository
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        fun BugPlacementPlayerSecond(str: String) {
            gameRepository.bugPlacementPlayerShow()
        }

        /*val tokenProvider: () -> String = {
            userRepository.getUser()?.token?.access
                ?: throw IllegalStateException("Undefined token")
        }

        val onErrorAuthListener: () -> String = {
            val token =
                userRepository.getUser()?.token ?: throw IllegalStateException("Token undefined")
            userRepository.refreshToken(token)?.access
                ?: throw IllegalStateException("Token undefined")
        }

        val renderCounter = AtomicInteger()  // к переменным можно обращаться только из одного потока, без паралельности, + отсутствует кэширование

        val renderListener: (GameState) -> Unit = {
            println(it)

            handler.post { viewState.onRender(it) }
            if (renderCounter.getAndIncrement() == 0)
                player?.ready()
        }

               player = NetworkPlayer(
           "212.75.210.227", 3456,
           tokenProvider, onErrorAuthListener, renderListener
        ).apply {
            start()
        }*/


    }

    fun onCell(take: TakeUI) {
        player?.cell(take.index)
    }
}


