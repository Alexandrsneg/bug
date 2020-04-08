package ru.sneg.android.bug.base

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class ABaseAdapter<D, VH: RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    var data = mutableListOf<D>()
    set(value) {
        field = value
        notifyDataSetChanged()  // уведомляет RecicleView что его датасет изменился
    }


    override fun getItemCount(): Int = data.size

    fun addBegin(item: D){
        data.add(0, item)
        notifyItemInserted(0)
    }

    fun addFinish (item: D){
        data.add(item)
        notifyItemInserted(data.size - 1)
    }
}