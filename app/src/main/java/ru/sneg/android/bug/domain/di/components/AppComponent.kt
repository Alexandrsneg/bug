package ru.sneg.android.bug.domain.di.components

import dagger.Component
import ru.sneg.android.bug.credentials.game.bugPlacement.BugPlacementPlayerFragment
import ru.sneg.android.bug.credentials.auth.SignInFragment
import ru.sneg.android.bug.credentials.bugPlacement.BugPlacementPlayerSecondFragment
import ru.sneg.android.bug.credentials.createGame.CreateGameFragment
import ru.sneg.android.bug.credentials.game.gameOfflineBot.GameOfflineBotFragment
import ru.sneg.android.bug.credentials.game.gameOfflinePvp.GameOfflinePvpFragment
import ru.sneg.android.bug.credentials.gameModes.GameModeFragment
import ru.sneg.android.bug.credentials.loading.LoadingFragment
import ru.sneg.android.bug.credentials.profile.ProfileFragment
import ru.sneg.android.bug.credentials.registration.SignUpFragment
import ru.sneg.android.bug.domain.di.modules.NetModule
import ru.sneg.android.bug.credentials.score.ScoreFragment
import javax.inject.Singleton


@Singleton
@Component(modules = [  // механизм создающий дагер, является мостом между  фрагментом и презентором
    NetModule::class
])

interface AppComponent {

    fun inject(target: SignUpFragment)
    fun inject(target: SignInFragment)
    fun inject(target: LoadingFragment)
    fun inject(target: ProfileFragment)
    fun inject(target: GameModeFragment)
    fun inject(target: CreateGameFragment)
    fun inject(target: BugPlacementPlayerFragment)
    fun inject(target: ScoreFragment)
    fun inject(target: BugPlacementPlayerSecondFragment)
    fun inject(target: GameOfflinePvpFragment)
    fun inject(target: GameOfflineBotFragment)

}