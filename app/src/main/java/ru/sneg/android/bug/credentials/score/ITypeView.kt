package ru.sneg.android.bug.credentials.score

import ru.sneg.android.bug.domain.repositories.models.CellUserItem


interface ITypeView {
    fun bind(data : CellUserItem)
}