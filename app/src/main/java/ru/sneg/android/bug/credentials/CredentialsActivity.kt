package ru.sneg.android.bug.credentials

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import ru.sneg.android.bug.App
import ru.sneg.android.bug.R
import ru.sneg.android.bug.base.ABaseActivity
import ru.sneg.android.bug.credentials.ICredentialsRouter
import ru.sneg.android.bug.credentials.auth.SignInFragment
import ru.sneg.android.bug.credentials.loading.LoadingFragment
import ru.sneg.android.bug.credentials.registration.SignUpFragment
import ru.sneg.android.bug.domain.repositories.local.UserStorage

class CredentialsActivity : ABaseActivity(), ICredentialsRouter {

    companion object {

        private const val ARG_DROP_CREDENTIALS = "ARG_DROP_CREDENTIALS"

        fun show() {
            App.appContext.let {
                it.startActivity(Intent(it, CredentialsActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
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
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_registration_conteiner)
        if (savedInstanceState != null)
            return

        if (intent.getBooleanExtra(ARG_DROP_CREDENTIALS, false)) {
            UserStorage().dropCredentials()
            showAuth()
            return
        }
        showLoading()
    }

   // переопределенные ниже функции взяты из интерфейса-маршрутизатора ICredentialsRouter

    override fun showLoading() {
        replace(LoadingFragment()) //функция replace определена в классе ABaseActivity
    }
    /*переопределяет имплементированный метод ICredentialsRouter и вызываясь в SignInFragment
    в tvSignUpBtn.setOnClickListener(е) переходит на SignUp фрагмент*/
    override fun showSignUp() {
        replace(SignUpFragment(), "Registration")
    }

    override fun showAuth() {
        replace(SignInFragment())
    }

}


