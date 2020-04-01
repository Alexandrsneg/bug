package ru.sneg.android.bug

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import ru.sneg.android.bug.credentials.profile.ProfileFragment

class BugPlacementPlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN )
        setContentView(R.layout.activity_bug_placement_player)
    }

    //при нажатии кнопки Profile переходим на экран профиля
    fun profileBtn(view: View) {
        val intent = Intent(this@BugPlacementPlayerActivity, ProfileFragment::class.java)
        startActivity(intent)
    }
}
