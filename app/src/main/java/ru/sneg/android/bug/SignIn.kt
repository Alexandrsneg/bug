package ru.sneg.android.bug

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager

class SignIn : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN )
        setContentView(R.layout.activity_sign_in)
    }

    //при нажатии кнопки Sign In переходим на экран выбора режима игры
    fun signInBtn(view: View) {
        val intent = Intent(this@SignIn, GameMode::class.java)
        startActivity(intent)
    }
}
