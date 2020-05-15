package ru.sneg.android.bug.game.engine

import com.google.gson.JsonObject

/*class ClashOfBugsPlayer  {
    val SELECT_TYPE_UNDEFINED = -1
    private val SELECT_TYPE_ZERO = 0
    private val SELECT_TYPE_CROSS = 1

    private val CMD_DIVIDER = ":"
    private val CMD_READY = "READY"
    private val CMD_CELL = "CELL"
    private val CMD_EXIT = "EXIT"
    private val CMD_STATE = "STATE"

    private var player: NetworkPlayer? = null
    private var engine: GameEngine? = null

    private var winCounter = 0
    private var actionType = SELECT_TYPE_UNDEFINED

    @Volatile
    var isReady = false
    @Volatile
    var action = false


    fun ClashOfBugsEngine(player: NetworkPlayer?, engine: GameEngine?) {
        this.player = player
        this.engine = engine
    }

    fun getNetworkPlayer(): NetworkPlayer? {
        return player
    }

    // Обработчик команд от удаленных игроков
// Сюда приходят команды по сети через протокол TCP/IP
    fun onCommand(bytes: ByteArray?) {
        val message = String(bytes!!).trim { it <= ' ' }
        val position = message.indexOf(CMD_DIVIDER)
        val cmd: String
        cmd = if (position < 0) message else message.substring(0, position)
        when (cmd) {
            CMD_READY -> onReady()
            CMD_CELL -> onCell(message, position + 1)
            CMD_EXIT -> engine.onExit(this)
            CMD_STATE -> engine.onState(this)
            else -> player!!.send(prepareError("Wrong command"))
        }
    }

    fun getActionType(): Int {
        return actionType
    }

    fun initAsFirst() {
        action = true
        actionType = SELECT_TYPE_CROSS
    }

    fun initAsSecond() {
        action = false
        actionType = SELECT_TYPE_ZERO
    }

    fun toJson(): JsonObject? {
        return  // Отдает Json-модель описания состояние игрока
    }

    private fun onReady() {
        isReady = true
        engine.onReady()
    }

    // Обработка хода игрока
    private fun onCell(message: String, position: Int) {
        try {
            if (!action) throw Exception("Now is not your turn")
            val cellNumber = message.substring(position).toInt()
            engine.onCell(player.getGamePlayer(), cellNumber)
        } catch (e: Exception) {
            e.printStackTrace()
            player!!.send(prepareError(e.localizedMessage))
        }
    }

    // Событие о том, что игрок победил
    fun onWin() {
        winCounter++
    }

}*/