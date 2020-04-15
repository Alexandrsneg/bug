package ru.sneg.android.bug.game.engine

import com.google.gson.GsonBuilder
import eac.network.Connection
import eac.network.PackageReceiver
import eac.network.PackageSender
import eac.network.Tcp
import java.util.concurrent.atomic.AtomicBoolean


class NetworkPlayer  (
    val address: String,
    val port: Int,
    val tokenProvider: () -> String,
    val onErrorAuthListener: () -> String,
    val renderListener: (GameState) -> Unit //

) : IPlayer {

    companion object {

        const val CMD_NEED_AUTHORIZATION = "AUTHORIZATION"
        const val CMD_RESULT_SUCCESS = "SUCCESS"
        const val CMD_DIVIDER = ":"
        const val CMD_SELECT_GAME = "GAME${CMD_DIVIDER}tic-tac-toe"
        const val CMD_RENDER = "RENDER$CMD_DIVIDER"

        const val CMD_READY = "READY"
        const val CMD_CELL = "CELL"// команда выбора
        const val CMD_EXIT = "EXIT"
    }

    private var connection: Connection? = null
    private var sender = PackageSender()
    private var receiver = PackageReceiver()

    private val isAuth = AtomicBoolean()
    private val gson = GsonBuilder().create()

    override fun start() {

        val connection = connection
        if (connection != null)
            return

        val tcp = Tcp(address, port)
            .setOnDisconnected<Tcp> { disconnect() }

        this.connection = tcp

        tcp.start()
        sender.register(tcp)
        receiver.register(tcp) { _, bytes -> onMessage(String(bytes)) }
    }

    override fun ready() {
        send(CMD_READY)
    }

    override fun cell(value: Int) {
        send(CMD_CELL + CMD_DIVIDER + "$value")
    }

    override fun exit() {
        send(CMD_EXIT) { disconnect() }
    }

    private fun onMessage(message: String) {
        println("onMessage: $message")

        if (!isAuth.get()) {
            onAuth(message)
            return
        }

        if (message.startsWith(CMD_RENDER)) { //если команда на чинается с CMD_RENDER,
            val json = message.substring(CMD_RENDER.length).trim()
            val state = gson.fromJson(json, GameState::class.java)
            renderListener(state)
            if (state.winner != null)
                exit()
            return
        }
    }

    private fun onAuth(message: String) {

        when (message) {
            CMD_NEED_AUTHORIZATION -> send(tokenProvider())
            CMD_RESULT_SUCCESS -> onSuccessAuth()
            else -> onErrorAuth() //взов функции создания нового токена
        }
    }

    private fun onSuccessAuth() {

        isAuth.set(true)  //успешно авторизовались
        send(CMD_SELECT_GAME) //отправка команды выбора игры
    }

    private fun onErrorAuth() {
        val token = onErrorAuthListener() //в случае ошибки высылается новый токен
        send(token)
    }

    private fun send(message: String, call: ((Boolean) -> Unit)? = null) {
        println("send: $message")
        sender.send(message, object : Connection.Call() {
            override fun onSuccess(data: Connection.Data) { call?.invoke(true) }
            override fun onError(data: Connection.Data) { call?.invoke(false) }
        })
    }

    private fun disconnect() { //проверяет connection

        connection?.let {            //если определено, то
            connection = null        //зануляем
            sender.unregister()
            receiver.unregister()
            it.shutdown()
        }
    }
}