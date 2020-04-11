package ru.sneg.android.bug.game.engine



class NetworkPlayer(
    val address: String,
    val port: Int,
    val tokenProvider: () -> String,
val renderListener: (GameState) -> Unit
)  {

    /*companion object{

        const val CMD_NEED_AUTHORIZATION = "AUTHORIZATION"
        const val CMD_RESULT_SUCCES  = "SUCCES"
        const val CMD_SELECT_GAME  = "GAME: tic-tac-toe"
        const val CMD_RENDER  = "RENDER"
    }


    private var connection: Connection? = null  //import eac.network.Connection
    private var sender = PackageSender()
    private var receiver = PackageReceiver()

    private val isAuth =


    override fun start() {
       vat tcp =  Tcp(address, port)
            .setOnConnected<Tcp>{

            }
            .setOnDisconnected<Tcp>{
        }
            .setOnRecieved<Tcp>{_, bytes ->

            }
            .aplly {
                connection = this
                start()
            }

        sender.register(tcp)
        reciever.register(tcp) { _, bytes ->
            onMessage(String(bytes))
        }
    }

    override fun ready() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    //tcp.start()

    private fun onMessage(message: String){
        if(!isAuth.get()){
            onAuth(message)
            return
        }

        if(message.startsWith(CMD_RENDER)){
val json = message.substring(CMD_RENDER.length).trim()   //trim() - удаляет пробельные символы с начала и конца
        }
    }
    private fun onAuth(message: String){
        when (message) {
            CMD_NEED_AUTHORIZATION -> sender.send(tokenProvider())
            CMD_RESULT_SUCCES -> onSuccessAuth()
            else -> onErrorAuth()
        }
    }

    private fun onSuccessAuth(){
        isAuth.set(true)

    }*/

}