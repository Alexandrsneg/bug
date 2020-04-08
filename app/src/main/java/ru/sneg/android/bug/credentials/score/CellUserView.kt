package ru.sneg.android.bug.credentials.score

import android.content.Context
import android.util.AttributeSet
import kotlinx.android.synthetic.main.cell_user.view.*
import ru.sneg.android.bug.R
import ru.sneg.android.bug.base.ABaseView
import ru.sneg.android.bug.domain.repositories.models.CellUserItem

class CellUserView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ABaseView(context, attrs, defStyleAttr),
    ITypeView {

    override fun getViewId(): Int = R.layout.cell_user

    override fun bind(data: CellUserItem){
        t_user_login.text = data.title
    }

}