package ru.sneg.android.bug.eac

import androidx.fragment.app.Fragment
import ru.sneg.android.bug.eac.players.NetworkPlayer

// Поле, где отображается игра
class FieldFragment : Fragment() {

    fun getPlayerNameArg() = arguments?.getString("")

    fun onCreate() {

        val playerName = getPlayerNameArg()

        GameService.connect { service ->

            val engine = service.gameEngine
            val player = engine.getPlayerByName(playerName)

            if (player == null) {
                player = NetworkPlayer("localhost", NetworkServer.DEFAULT_PORT)
                engine.addPlayer(player)
            }

            fieldView.player = player


            service.networkServer.start()
        }
    }
}