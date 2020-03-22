package ru.sneg.android.bug.base

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_registration_conteiner.view.*
import ru.sneg.android.bug.R

abstract class ABaseActivity: AppCompatActivity() {
    fun replace (fragment: Fragment, backStack: String? = null, tag: String? = null){
        supportFragmentManager.beginTransaction()
            .replace(R.id.containerActivity, fragment, tag).apply {
                backStack?.let {addToBackStack(it)}
            }
            .commit()
    }
}