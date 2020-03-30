package ru.sneg.android.bug.base

import android.graphics.PorterDuff
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.sneg.android.bug.R

abstract class ABaseActivity: AppCompatActivity() {
/* определяем базовую функцию для для смены (.replace) фрагментов в containerActivity, чтобы не писать руками
это каждый раз для каждой активити
 */
fun replace (fragment: Fragment, backStack: String? = null, tag: String? = null){

    supportFragmentManager.beginTransaction()
        .replace(R.id.containerActivity, fragment, tag).apply {
            backStack?.let {addToBackStack(it)} //добавление фрагмента в стек, для возможности возврата через back
        }
        .commit()
}


}