package ru.sneg.android.bug

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import ru.sneg.android.bug.credentials.profile.ProfileFragment

class ServerSettingsFragment : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN )
        setContentView(R.layout.fragment_server_settings)
    }

    fun profileBtn(view: View) {
        val intent = Intent(this@ServerSettingsFragment, ProfileFragment::class.java)
        startActivity(intent)
    }
}
