package ru.sneg.android.bug.credentials.score

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.sneg.android.bug.domain.repositories.DialogsRepository
import javax.inject.Inject

@InjectViewState // аннотация для Moxy, что бы предоставить вьюстэйт
class DialogsPresenter : MvpPresenter<IDialogsView> {

    private val repository: DialogsRepository

    @Inject // аннотация для Дагер, класс можно использовать как зависимость
    constructor(repository: DialogsRepository){
        this.repository = repository
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        repository.loadDialogs {
            viewState.bindDialogs(it)
        }
    }
}