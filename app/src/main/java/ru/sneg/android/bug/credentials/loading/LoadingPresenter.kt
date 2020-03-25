package ru.sneg.android.bug.credentials.loading

import android.os.Handler
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import javax.inject.Inject

@InjectViewState
class LoadingPresenter : MvpPresenter<ILoadingView> {

    @Inject
    constructor()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        loadStaticResources()
    }

    fun loadStaticResources() {
        Handler().postDelayed({

            viewState.onLoadingComplete()

        }, 2000)
    }
}