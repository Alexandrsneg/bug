package ru.sneg.android.bug.eac.players

import ru.sneg.android.bug.eac.game.GameEngine

abstract class APlayer : IPlayer {

    protected var gameEngine: GameEngine? = null

    override fun setEngine(engine: GameEngine) {
        gameEngine = engine
    }

    abstract fun onTouch(xCell: Int, yCell: Int)
}