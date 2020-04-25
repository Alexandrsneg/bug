package ru.sneg.android.bug.credentials.game.gameOfflinePvp

import ru.sneg.android.bug.base.IBaseView
import ru.sneg.android.bug.game.engine.GameState

interface IGameOfflinePvpView: IBaseView {
    fun onRender(state: GameState)
}