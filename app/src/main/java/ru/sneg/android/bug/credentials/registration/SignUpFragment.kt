package ru.sneg.android.bug.credentials.registration

import android.content.Intent
import android.graphics.PorterDuff
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
import ru.sneg.android.bug.domain.di.components.DaggerAppComponent
import javax.inject.Inject

class SignUpFragment : ABaseFragment(), ISignUpView {
    @Inject //использование Даггером конструктора из презентера, подставление зависимости
    @InjectPresenter // аннотация Moxy управляет ж. циклом Presenter
    lateinit var presenter: SignUpPresenter

    @ProvidePresenter // предоставление презентера для Moxy
    fun providePresenter() = presenter

    override fun inject() {
        DaggerAppComponent.create().inject(this) // DaggerAppComponent генерируется автоматически
                                                        //при наличии дерева зависимостей и связуещего звена @Component IAppComponent
    }

    override fun getViewId() = R.layout.fragment_sign_up

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        button.setOnClickListener {

           // buttonEffect(button)

            // обработчик нажатия на кнопку зерег-ся
            val login = "${editText.text}"
            val pass = "${editText2.text}"
            val rPass = "${editText3.text}"

            if (login.isEmpty() || pass.isEmpty() || rPass.isEmpty()) {
                toast(stringId = R.string.error_login_pass_undefined)}
                 if (pass != rPass){
                    toast(stringId = R.string.error_password_undefined)
                return@setOnClickListener
            }

            presenter.signUp(login, pass)
        }

    }

}