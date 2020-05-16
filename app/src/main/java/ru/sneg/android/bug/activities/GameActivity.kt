package ru.sneg.android.bug.activities

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import ru.sneg.android.bug.App
import ru.sneg.android.bug.credentials.game.bugPlacement.BugPlacementPlayerFragment
import ru.sneg.android.bug.R
import ru.sneg.android.bug.activities.routers.IBattleGroundsGameRouter
import ru.sneg.android.bug.base.ABaseActivity
import ru.sneg.android.bug.credentials.bugPlacement.BugPlacementPlayerSecondFragment
import ru.sneg.android.bug.credentials.game.gameOfflineBot.GameOfflineBotFragment
import ru.sneg.android.bug.credentials.game.gameOfflinePvp.GameOfflinePvpFragment

class GameActivity : ABaseActivity(), IBattleGroundsGameRouter {
    companion object {

        private const val ARG_DROP_CREDENTIALS = "ARG_DROP_CREDENTIALS"

        fun show() {
            App.appContext.let {
                it.startActivity(Intent(it, GameActivity::class.java).apply {
                    //flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    putExtra(ARG_DROP_CREDENTIALS, true)
                })
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN )
        setContentView(R.layout.activity_game_mode_conteiner)


        if (intent.getBooleanExtra(ARG_DROP_CREDENTIALS, false)) {
           // UserStorage().dropCredentials()
            showBugPlaycement()
            return
        }
    }

    // переопределенные ниже функции взяты из интерфейса-маршрутизатора ICredentialsRouter

    override fun showBugPlaycement() {
        replace(BugPlacementPlayerFragment())
    }


    override fun showBugPlaycementSecond() {
        replace(BugPlacementPlayerSecondFragment())
    }

    override fun showBugVsBugGame() {
        replace(GameOfflinePvpFragment())
    }

    override fun showBugVsCpuBugGame() {
        replace(GameOfflineBotFragment())
    }
}
