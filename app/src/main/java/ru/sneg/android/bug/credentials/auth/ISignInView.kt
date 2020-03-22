package ru.sneg.android.bug.credentials.auth

import com.arellomobile.mvp.MvpView
import ru.sneg.android.bug.base.IBaseView

interface ISignInView : IBaseView {
 fun onSuccess()
}