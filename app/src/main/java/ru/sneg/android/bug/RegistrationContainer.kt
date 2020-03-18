package ru.sneg.android.bug

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import ru.sneg.android.bug.registration.MainActivity
import ru.sneg.android.bug.registration.SignInFragment
import ru.sneg.android.bug.registration.SignUpFragment

class RegistrationContainer : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_registration_conteiner)

        if (MainActivity().signUp == true) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.containerActivity, SignUpFragment())
                .commit()
        }

        if (MainActivity().signIn == true) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.containerActivity, SignInFragment())
                .commit()
        }
    }
}


