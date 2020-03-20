package ru.sneg.android.bug.base

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.MvpAppCompatFragment

abstract class ABaseFragment : MvpAppCompatFragment() {

    init {
        inject()
    }
    abstract fun inject()

    abstract fun getViewId

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}