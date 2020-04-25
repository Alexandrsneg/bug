package ru.sneg.android.bug.credentials.game.bugPlacement

import ru.sneg.android.bug.base.IBaseView
import ru.sneg.android.bug.game.engine.GameState

interface IBugPlaycementPlayerView : IBaseView {

    fun onRender(state: GameState)
}