package ru.sneg.android.bug.loading

import com.arellomobile.mvp.MvpPresenter
import javax.inject.Inject

class LoadingPresenter : MvpPresenter<ILoadingView> {

    @Inject
    constructor()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        loadStatic
    }
}