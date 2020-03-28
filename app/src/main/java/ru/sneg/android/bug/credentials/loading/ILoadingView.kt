package ru.sneg.android.bug.credentials.loading

import com.arellomobile.mvp.MvpView

interface ILoadingView : MvpView {
    fun showAuth()
}