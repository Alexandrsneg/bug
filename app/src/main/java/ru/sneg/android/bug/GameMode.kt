package ru.sneg.android.bug

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import ru.sneg.android.bug.registration.MainActivity

class GameMode : AppCompatActivity() {

    // публичные переменные для определения какой тип игры выбран и логики расстановки кораблей;
    public var cpu : Boolean = false;
    public var pVp : Boolean = false;
    public var server : Boolean = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN )
        setContentView(R.layout.activity_game_mode)
    }

    //при нажатии кнопки Profile переходим на экран профиля
    fun profileBtn(view: View) {
        val intent = Intent(this@GameMode, Profile::class.java)
        startActivity(intent)
    }
    //при нажатии кнопки Score переходим на экран рекордов
    fun scoreBtn(view: View) {
        val intent = Intent(this@GameMode, Score::class.java)
        startActivity(intent)
    }
    //при нажатии кнопки Bug vs random Bug переходим на экран настройки сетевй игры
    fun serverBtn(view: View) {
        val intent = Intent(this@GameMode, ServerSettings::class.java)
        startActivity(intent)
        server  = true
    }
    //при нажатии кнопки Bug vs CPU Bug переходим на экран расстановки кораблей (только своих?)
    fun cpuBtn(view: View) {
        val intent = Intent(this@GameMode, BugPlacementPlayer::class.java)
        startActivity(intent)
        cpu  = true
    }
    //при нажатии кнопки Bug vs Bug переходим на экран расстановки кораблей (только своих?)
    fun pVpBtn(view: View) {
        val intent = Intent(this@GameMode, BugPlacementPlayer::class.java)
        startActivity(intent)
        server = true
    }
}
