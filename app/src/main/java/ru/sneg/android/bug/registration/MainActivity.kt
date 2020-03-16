package ru.sneg.android.bug.registration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Intent
import android.view.Window
import android.view.WindowManager
import ru.sneg.android.bug.SignIn
import ru.sneg.android.bug.R


class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN )
        setContentView(R.layout.activity_main)

    }

    fun signInBtn(view: View) {
        val intent = Intent(this@MainActivity, SignIn::class.java)
        startActivity(intent)
    }

    fun signUpBtn(view: View) {
        val intent = Intent(this@MainActivity, SignUp::class.java)
        startActivity(intent)
    }


}
