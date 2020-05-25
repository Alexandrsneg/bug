package ru.sneg.android.bug.eac.players

import eac.network.Connection
import eac.network.PackageReceiver
import eac.network.PackageSender
import eac.network.Tcp
import java.util.*

class NetworkPlayer : APlayer {

    private var host = ""
    private var port = 0
    private val playerTag = UUID.randomUUID().toString()

    private var connection: Connection? = null
    private var sender = PackageSender()
    private var receiver = PackageReceiver()

    private var isStarted = false

    constructor(host: String, port: Int) : super() {
        this.host = host
        this.port = port
    }

    constructor(connection: Connection) : super() {
        setConnection(connection)
    }

    override fun onTouch(xCell: Int, yCell: Int) {
        gameEngine?.onCell(this, xCell, yCell)
    }

    fun start() {

        if (isStarted)
            return

        isStarted = true
        connect()
    }


    private fun connect() {

        val connection = Tcp(host, port)
            .setOnConnected<Tcp> {
                sender.send(playerTag)
            }
            .setOnError<Tcp> { _, throwable ->
                false
            }

//        connection.start()

        setConnection(connection)
    }

    fun setConnection(connection: Connection) {
        this.connection = connection

        sender.register(connection)
        receiver.register(connection) { _ , data ->

        }
    }
}