package ru.sneg.android.bug.credentials.game.gameOfflineBot

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import kotlinx.android.synthetic.main.fragment_game_offline_bot.*
import kotlinx.android.synthetic.main.fragment_game_offline_bot.tv_player_login
import kotlinx.android.synthetic.main.fragment_game_offline_bot_result.*
import ru.sneg.android.bug.R
import ru.sneg.android.bug.activities.routers.IBattleGroundsGameRouter
import ru.sneg.android.bug.base.ABaseFragment
import ru.sneg.android.bug.domain.repositories.local.UserStorage

class ResultBotFragment : ABaseFragment() {

    companion object{
    var winnerIs = ""
}


    override fun getViewId(): Int =  R.layout.fragment_game_offline_bot_result

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_player_login.text = UserStorage().getUser()?.login ?:  "Unonimous bug"

        if (winnerIs == "Player"){
            visibility(wasted_player, false)
            visibility(wasted_bot, true)
        }
        else {
            visibility(wasted_player, true)
            visibility(wasted_bot, false)
        }
    }



    override fun inject() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
