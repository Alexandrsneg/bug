package ru.sneg.android.bug.eac

import eac.network.Connection
import eac.network.PackageReceiver
import eac.network.Server
import ru.sneg.android.bug.eac.game.GameEngine
import ru.sneg.android.bug.eac.players.NetworkPlayer

class NetworkServer (
    val gameEngine: GameEngine,
    val onConnected: (NetworkPlayer) -> Unit
) {

    companion object {
        const val DEFAULT_PORT = 9999
    }

    private val server: Server? = null

    fun start(port: Int) {

        val server = Server(port)
            .setOnConnected {
                PackageReceiver().register(it) { connect, bytes ->
                    val playerTag = String(bytes)

                    gameEngine.findPlayerByTag(playerTag)?.let { player ->
                        if (player is NetworkPlayer)
                            player.setConnection(connect)
                        return@register
                    }

                    gameEngine.addPlayer(NetworkPlayer(connect))

                }
                // Валидации подключения
                onConnected(NetworkPlayer(it))
            }
    }
}