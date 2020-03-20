package ru.sneg.android.bug

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import ru.sneg.android.bug.registration.MainActivity

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN )
        setContentView(R.layout.activity_profile)
    }

    //при нажатии кнопки Change profile переходим на main экран
    fun changeProfile(view: View) {
        val intent = Intent(this@ProfileActivity, MainActivity::class.java)
        startActivity(intent)
    }

    //при нажатии кнопки Game mode переходим на экран выбора режима игры
    fun gameModeBtn(view: View) {
        val intent = Intent(this@ProfileActivity, GameModeActivity::class.java)
        startActivity(intent)
    }
}
