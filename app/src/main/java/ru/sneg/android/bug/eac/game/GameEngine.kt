package ru.sneg.android.bug.eac.game

import ru.sneg.android.bug.eac.players.APlayer
import ru.sneg.android.bug.eac.players.IPlayer

class GameEngine {

    private var player1: IPlayer? = null
    private var player2: IPlayer? = null

    var onStateChanged: ((GameEngine) -> Unit) = {}

    // Прописать состояние игры

    private var isFirstPlayerCell = true

    fun addPlayer(player: IPlayer) {

        if (player1 == null) {
            player1 = player
            player.setEngine(this)
            onStateChanged(this)
            return
        }

        if (player2 == null) {
            player2 = player
            player.setEngine(this)
            onStateChanged(this)
            return
        }

        throw IllegalStateException("Больше нельзя устанавливать игроков")
    }

    fun onCell(player: APlayer, xCell: Int, yCell: Int) {

        if (player1 == player) {
            if (!isFirstPlayerCell)
                return
            // processing
            onStateChanged(this)
        }

        if (player2 == player) {
            if (isFirstPlayerCell)
                return
            // processing
            onStateChanged(this)
        }

        if (player1?.isDead() || player2?.isDead())
            // Алгоритм завершения
    }

    fun findPlayerByTag(tag: String): IPlayer? {

        player1?.let {
            if (it.tag == tag)
                return it
        }

        player2?.let {
            if (it.tag == tag)
                return it
        }

        return null
    }
}