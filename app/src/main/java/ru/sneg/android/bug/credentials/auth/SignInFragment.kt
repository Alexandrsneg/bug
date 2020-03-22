package ru.sneg.android.bug.credentials.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.fragment_sign_in.editText
import kotlinx.android.synthetic.main.fragment_sign_in.editText2
import kotlinx.android.synthetic.main.fragment_sign_up.*
import ru.sneg.android.bug.ProfileActivity
import ru.sneg.android.bug.R
import ru.sneg.android.bug.base.ABaseFragment
import ru.sneg.android.bug.base.IBaseView
import ru.sneg.android.bug.credentials.ICredentialsRouter
import ru.sneg.android.bug.credentials.registration.SignUpFragment
import javax.inject.Inject


class SignInFragment : ABaseFragment(), ISignInView {
    @Inject
    @InjectPresenter
    lateinit var presenter: SignInPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun inject() {
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

          /* обработчик нажатия на кнопку вход, переход с ФРАГМЕНТА на АКТИВИТИ
        bSigInBtn.setOnClickListener {

            activity?.let{
                val intent = Intent (it, ProfileActivity::class.java)
                it.startActivity(intent)
            } */

        /* нажимая на кнопку вход - проверяем на заполненность логин и пароль и отправляем в
        презентер на проверку*/
        bSigInBtn.setOnClickListener {

            val login = "${editText.text}"
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
    //отправить на гл. форму

    override fun onError(message: String) {
        toast("SOMETHING WRONG")
    }
}