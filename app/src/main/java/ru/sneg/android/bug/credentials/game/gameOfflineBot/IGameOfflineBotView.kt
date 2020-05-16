package ru.sneg.android.bug.credentials.game.gameOfflineBot

import ru.sneg.android.bug.base.IBaseView
import ru.sneg.android.bug.game.engine.GameState

interface IGameOfflineBotView: IBaseView {
    fun onRender(state: GameState)
}