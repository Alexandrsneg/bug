package ru.sneg.android.bug.credentials.score

import com.arellomobile.mvp.MvpView
import ru.sneg.android.bug.domain.repositories.models.CellUserItem


interface IScoreView : MvpView {
    fun bindDialogs(dialogs: List<CellUserItem>)
}