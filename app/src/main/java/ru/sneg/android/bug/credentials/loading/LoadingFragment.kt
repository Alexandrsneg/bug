package ru.sneg.android.bug.credentials.loading

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import ru.sneg.android.bug.R
import ru.sneg.android.bug.base.ABaseFragment
import ru.sneg.android.bug.credentials.ICredentialsRouter
import javax.inject.Inject


class LoadingFragment : ABaseFragment(), ILoadingView {

    @Inject
    @InjectPresenter
    lateinit var presenter: LoadingPresenter

        @ProvidePresenter
    fun providePresenter() =presenter

    override fun inject(){
        DaggerAppComponent.create().inject(this)
    }
    override fun getViewId() = R.layout.fragment_loading

    override fun onLoadingComplete() {
        activity?.let {
            if (it is ICredentialsRouter)
                it.showAuth()
        }
    }

}
