package ru.sneg.android.bug.eac

import android.app.Service
import android.content.Intent
import android.os.IBinder
import ru.sneg.android.bug.eac.game.GameEngine

class GameService : Service() {

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    val gameEngine = GameEngine()
    val networkServer = NetworkServer(gameEngine) { player ->
        gameEngine.addPlayer(player)
    }

    override fun onCreate() {
        super.onCreate()
    }
}