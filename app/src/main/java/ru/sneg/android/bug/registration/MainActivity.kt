package ru.sneg.android.bug.registration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Intent
import android.view.Window
import android.view.WindowManager
import ru.sneg.android.bug.R
import ru.sneg.android.bug.RegistrationContainer


class MainActivity : AppCompatActivity() {

      var signIn : Boolean = false;
      var signUp : Boolean = false;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN )
        setContentView(R.layout.activity_main)

    }

    fun signInBtn(view: View) {
        val intent = Intent(this@MainActivity, RegistrationContainer::class.java)
        startActivity(intent)

        signIn = true;

    }


    fun signUpBtn(view: View) {
        val intent = Intent(this@MainActivity, RegistrationContainer::class.java)
        startActivity(intent)

        signUp = true;
    }

}
