package ru.sneg.android.bug.credentials.score

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_game_mode.*
import kotlinx.android.synthetic.main.fragment_score.*

import ru.sneg.android.bug.R
import ru.sneg.android.bug.activities.CredentialsActivity
import ru.sneg.android.bug.activities.GameModeActivity
import ru.sneg.android.bug.activities.ScoreActivity
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
            if (view is ITypeView){
                view.bind(data[position])
            }
            if (position %2 == 0){
                Log.d("POS", position.toString())
                view.setBackgroundColor(0xff00ff00.toInt())
            }
            else view.setBackgroundColor(0xff444444.toInt())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            //кнопка перехода на профиль
                bProfile1.setOnClickListener {
             GameModeActivity.show()
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