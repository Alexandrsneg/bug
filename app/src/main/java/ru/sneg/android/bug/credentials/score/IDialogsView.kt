package ru.sneg.android.bug.credentials.score

import com.arellomobile.mvp.MvpView
import ru.sneg.android.bug.domain.repositories.models.CellUserItem


interface IDialogsView : MvpView {
    fun bindDialogs(dialogs: List<CellUserItem>)
}