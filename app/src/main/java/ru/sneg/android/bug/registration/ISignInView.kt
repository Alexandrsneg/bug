package ru.sneg.android.bug.registration

import com.arellomobile.mvp.MvpView

interface ISignInView : MvpView {

        fun showError(message: String)
}