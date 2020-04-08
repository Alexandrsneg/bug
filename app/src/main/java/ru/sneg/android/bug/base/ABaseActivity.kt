package ru.sneg.android.bug.base

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.sneg.android.bug.R
import ru.sneg.android.bug.credentials.loading.LoadingFragment

abstract class ABaseActivity: AppCompatActivity() {
/* определяем базовую функцию для для смены (.replace) фрагментов в containerActivity, чтобы не писать руками
это каждый раз для каждой активити
 */
    //open fun getConteiner(): Int = R.id.containerActivity

fun replace (fragment: Fragment, backStack: String? = null, tag: String? = null){

    supportFragmentManager.beginTransaction()
        .replace(R.id.containerActivity, fragment, tag).apply {
            backStack?.let {addToBackStack(it)} //добавление фрагмента в стек, для возможности возврата через back
        }
        .commit()
}


}