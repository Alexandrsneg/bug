package ru.sneg.android.bug.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.sneg.android.bug.R
import ru.sneg.android.bug.domain.repositories.models.rest.GameResult

class PlayersAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mPlayersList:ArrayList<GameResult> = ArrayList()
    //отрисовка списка
    fun setupPlayers(gameResultList:ArrayList<GameResult>){
        mPlayersList.clear()
        mPlayersList.addAll(gameResultList)
        //говорим,что данные изменились, и их нужно отрисовать заного
        notifyDataSetChanged()
    }
    //создается класс View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PlayersViewHolder(itemView =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.cell_user,parent,false))

    }

    override fun getItemCount(): Int {
        return  mPlayersList.count()
    }
    //Заполняем созданный класс
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is PlayersViewHolder){
            holder.bind(gameResult = mPlayersList[position])
        }
    }
    //класс который отображает ячейку
    class PlayersViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private var mTxtLogin: TextView =itemView.findViewById(R.id.t_user_login)
        //отрисовка модели и тд
        fun bind(gameResult:GameResult){
            mTxtLogin.text=gameResult.login
        }
    }
}