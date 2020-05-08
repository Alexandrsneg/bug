package ru.sneg.android.bug.credentials.auth

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.fragment_sign_in.editText
import kotlinx.android.synthetic.main.fragment_sign_in.editText2
import ru.sneg.android.bug.R
import ru.sneg.android.bug.base.ABaseFragment
import ru.sneg.android.bug.activities.routers.ICredentialsRouter
import ru.sneg.android.bug.domain.di.components.DaggerAppComponent
import javax.inject.Inject


class SignInFragment : ABaseFragment(), ISignInView {
    @Inject //использование Даггером конструктора из презентера, подставление зависимости
    @InjectPresenter // аннотация Moxy управляет ж. циклом Presenter
    lateinit var presenter: SignInPresenter

    @ProvidePresenter // предоставление презентера для Moxy
    fun providePresenter() = presenter

    override fun inject() {
        DaggerAppComponent.create().inject(this)
    }
    /* override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }*/

    //вместо блока выше переопределяем метод Базового класса для фрагментов
    override fun getViewId() = R.layout.fragment_sign_in

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        bSigInBtn.setOnClickListener {

            val login = "${editText.text}"  //конструкия показывает, что переменная будет String
            val password = "${editText2.text}"

            if (login.isEmpty() || password.isEmpty()) {
                toast(R.string.error_login_pass_undefined)
                return@setOnClickListener
            }
                presenter.signIn(login, password)
            }

        /*
         * обработчик нажатия кнопк регистрации во ФРАГМЕНТЕ, реализует переход на др. ФРАГМЕНТ
        tvSignUpBtn.setOnClickListener {
            var fr = getFragmentManager()?.beginTransaction()
            fr?.replace(R.id.containerActivity,
                SignUpFragment()
            )
            fr?.addToBackStack(null) //добавляем транзацкию в стэк для возврата назад
            fr?.commit()
        }*/

        tvSignUpBtn.setOnClickListener {
            activity?.let {
                if (it is ICredentialsRouter)
                    it.showSignUp()
            }
        }
    }

        override fun lock() {
            visibility(flBtnContainer)
        }

        override fun unlock() {
            visibility(flBtnContainer, false)
        }

        override fun onSuccess() {
            toast("SUCCESS")

        }
}

