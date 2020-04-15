package ru.sneg.android.bug.credentials.bugPlacement

import ru.sneg.android.bug.base.IBaseView
import ru.sneg.android.bug.game.engine.GameState

interface IBugPlaycementPlayerView : IBaseView {

    fun onRender(state: GameState)
}