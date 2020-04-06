package ru.sneg.android.bug.credentials.createGame

import ru.sneg.android.bug.base.IBaseView

interface ICreateGameView: IBaseView {
    fun showError(text:String)
    fun showBattleGroundsActivity()
}