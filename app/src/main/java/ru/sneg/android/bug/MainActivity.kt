package ru.sneg.android.bug

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Intent
import android.graphics.PorterDuff
import android.view.MotionEvent
import android.view.Window
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.sneg.android.bug.credentials.CredentialsActivity


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN )
        setContentView(R.layout.activity_main)

    }
   /* open fun buttonEffect(button: View) { //програмное создание эффекта нажатия на кнопку (смена цвета)
        button.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    v.background.setColorFilter(-0x1f0b8adf, PorterDuff.Mode.SRC_ATOP)
                    v.invalidate()
                }
                MotionEvent.ACTION_UP -> {
                    v.background.clearColorFilter()
                    v.invalidate()
                }
            }
            false
        }
    }*/


    fun signInBtn(view: View) {
        println("debug")

        val intent = Intent(this@MainActivity, CredentialsActivity::class.java)
        startActivity(intent)
    }


}
