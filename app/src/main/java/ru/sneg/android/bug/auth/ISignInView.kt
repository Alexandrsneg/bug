package ru.sneg.android.bug.auth

import com.arellomobile.mvp.MvpView

interface ISignInView : MvpView {

        fun showError(message: String)
}