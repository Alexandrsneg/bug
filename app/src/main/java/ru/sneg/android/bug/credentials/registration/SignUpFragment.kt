package ru.sneg.android.bug.credentials.registration

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_sign_up.*
import ru.sneg.android.bug.R
import ru.sneg.android.bug.base.ABaseFragment
import javax.inject.Inject

class SignUpFragment : ABaseFragment,
    ISignUpView {
    @Inject
    @InjectPresenter // аннотация управляет ж. циклом Presenter
    lateinit var presenter: SignUpPresenter

    @ProvidePresenter // Реализация для Dagger
    fun providePresenter() =
        SignUpPresenter()

    constructor(){
       // DaggerAppComponent.create().inject(this)

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        button.setOnClickListener { // обработчик нажатия на кнопку зерег-ся
            val login = "${editText.text}"
            val password = "${editText2.text}"
            val Rpassword = "${editText3.text}"


        }

    }

    override fun validation(login: String, pass: String) {

    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun inject() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getViewId() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}