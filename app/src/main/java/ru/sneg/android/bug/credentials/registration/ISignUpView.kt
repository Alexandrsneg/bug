package ru.sneg.android.bug.credentials.registration

import com.arellomobile.mvp.MvpView

interface ISignUpView : MvpView {
    fun showError(message: String)

    fun validation(login:String , pass:String)
}