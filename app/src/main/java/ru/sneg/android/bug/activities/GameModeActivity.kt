package ru.sneg.android.bug.activities

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import ru.sneg.android.bug.App
import ru.sneg.android.bug.credentials.gameModes.GameModeFragment
import ru.sneg.android.bug.R
import ru.sneg.android.bug.activities.routers.IGameModeRouter
import ru.sneg.android.bug.base.ABaseActivity
import ru.sneg.android.bug.credentials.profile.ProfileFragment
import ru.sneg.android.bug.domain.repositories.local.UserStorage

class GameModeActivity : ABaseActivity(),
    IGameModeRouter {

        companion object {

            private const val ARG_DROP_GAME_MODE_ACTIVITY = "ARG_DROP_GAME_MODE_ACTIVITY"

            fun show() {
                App.appContext.let {
                    it.startActivity(Intent(it, GameModeActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        putExtra(ARG_DROP_GAME_MODE_ACTIVITY, true)
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


            if (intent.getBooleanExtra(ARG_DROP_GAME_MODE_ACTIVITY, false)) {
               // UserStorage().dropCredentials()
                showProfile()
                return
            }
        }

    override fun showProfile() {
        replace(ProfileFragment())
    }

    override fun showGameModes() {
        replace(GameModeFragment(),"back")
    }

    // переопределенные ниже функции взяты из интерфейса-маршрутизатора ICredentialsRouter


}