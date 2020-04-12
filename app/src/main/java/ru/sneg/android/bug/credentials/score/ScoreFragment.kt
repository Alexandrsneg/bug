package ru.sneg.android.bug.credentials.score

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import ru.sneg.android.bug.R
import ru.sneg.android.bug.base.ABaseAdapter
import ru.sneg.android.bug.base.ABaseListFragment
import ru.sneg.android.bug.domain.di.components.DaggerAppComponent
import ru.sneg.android.bug.domain.repositories.models.CellUserItem
import javax.inject.Inject

class ScoreFragment: ABaseListFragment<CellUserItem, RecyclerView.ViewHolder>(),
    IScoreView {


    class Adapter : ABaseAdapter<CellUserItem, RecyclerView.ViewHolder>(){

        companion object{
            const val TYPE_CELL_USER = 0
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view: View = CellUserView(parent.context)
            return object: RecyclerView.ViewHolder(view){ }
        }

        override fun getItemViewType(position: Int): Int {
            return TYPE_CELL_USER
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
           val view = holder.itemView
            if (view is ITypeView)
                view.bind(data[position])
        }
    }

    override fun getListId(): Int = R.id.rv_users_score
    override fun getViewId(): Int = R.layout.fragment_score

    @Inject
    @InjectPresenter
    lateinit var presenter: ScorePresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    private val adapter =
        Adapter()


    override fun provideAdapter() = adapter

    override fun inject() {
    DaggerAppComponent.create().inject(this)
    }

    override fun bindDialogs(dialogs: List<CellUserItem>) {
        adapter.data = dialogs.toMutableList()
    }
}