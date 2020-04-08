package ru.sneg.android.bug.base

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class ABaseListFragment<D, VH : RecyclerView.ViewHolder> : ABaseFragment() {
    // Класс с <дженерикиками> работает с двумя пока неизвестными/абстрактными типами Data и ViewHandler - наследник RecyclerView.ViewHolder

    abstract fun getListId(): Int
    abstract fun provideAdapter(): ABaseAdapter<D, VH>

     open fun provideLayoutManager(): RecyclerView.LayoutManager = LinearLayoutManager(context)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list = view.findViewById<RecyclerView>(getListId())

        list.layoutManager = provideLayoutManager()
            list.adapter = provideAdapter()


    }
}