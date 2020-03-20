package ru.sneg.android.bug.registration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import ru.sneg.android.bug.R
import ru.sneg.android.bug.auth.SignInFragment

class RegistrationActivity : AppCompatActivity(), IRegistrationRouter {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_registration_conteiner)

            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.containerActivity,
                    SignInFragment()
                )
                .commit()
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}


