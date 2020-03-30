package ru.sneg.android.bug

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.sneg.android.bug.credentials.CredentialsActivity


class MainActivity : AppCompatActivity() {

    companion object {

        fun show() {
            App.appContext.let {
                it.startActivity(Intent(it, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                })
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ru.sneg.android.bug.R.layout.activity_main)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN )
        if (savedInstanceState != null)
            return // Не будем пересоздавать фрагмент, пусть берется старый из стека

        bSignInBtn.setOnClickListener {
            CredentialsActivity.show()
        }
    }
}
