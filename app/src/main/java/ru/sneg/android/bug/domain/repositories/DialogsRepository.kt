package ru.sneg.android.bug.domain.repositories

import ru.sneg.android.bug.domain.repositories.models.CellUserItem

import java.util.*
import javax.inject.Inject

class DialogsRepository  {


    @Inject
    constructor()

    fun loadDialogs(call: (List<CellUserItem>) -> Unit) {

        val result = mutableListOf<CellUserItem>()
        val random = Random(System.currentTimeMillis())
        val count = random.nextInt(100) + 10
        for (index in 0 until count)
            result.add(CellUserItem("$index"))

        call(result)
    }
}