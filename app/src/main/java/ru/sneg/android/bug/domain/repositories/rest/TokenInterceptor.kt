package ru.sneg.android.bug.domain.repositories.rest

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import ru.sneg.android.bug.activities.CredentialsActivity
import ru.sneg.android.bug.domain.repositories.UserRepository
import ru.sneg.android.bug.domain.repositories.models.rest.Token
import ru.sneg.android.bug.exceptions.AuthException
import java.net.HttpURLConnection
import java.util.concurrent.locks.ReentrantLock
import javax.inject.Inject


//Сущность подставляющая во все запросы Токены авторизации, обновляет токены
//взяли с рабочего проекта
class TokenInterceptor//репозиторий предоставляет данные текущео польователя
@Inject constructor(private val userRepository: UserRepository) : Interceptor {
    companion object {

        const val HEADER_AUTHORIZATION = "access_token"
    }


    private val lock = ReentrantLock()


    override fun intercept(inChain: Interceptor.Chain?): Response {

        val chain = inChain ?: throw IllegalArgumentException("Chain is NULL")

        var token = userRepository.getUser()?.token
        if (token == null) {
            CredentialsActivity.show()
            throw AuthException("Auth is NULL")
        }

        val original = chain.request()     //выполнение запроса
        val response = chain.proceed(addAuth(original, token))

        if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) { //если не авторизован (код 401)
            if (lock.tryLock()) { //блокировка потока
                try {

                    token = userRepository.refreshToken(token) //выполнение функции обновления

                } catch (e: Exception) {
                    e.printStackTrace()
                    CredentialsActivity.show()
                    throw AuthException("Fail refresh auth") //в случае ошибки выбрасывается "Fail refresh auth"
                } finally { //блок finally исполняется 100%, поэтому используется чтобы наверняка снять блокировку
                    lock.unlock()    //если успешно - блокировка потока снимается
                }

                return chain.proceed(addAuth(original, token)) // подставление в запрос новых авторизационных данных
            }

            else {
                lock.lock()
                lock.unlock()

                token = userRepository.getUser()?.token
                if (token == null) {
                    CredentialsActivity.show()
                    throw AuthException("Auth is NULL")
                }

                return chain.proceed(addAuth(original, token))
            }
        }

        return response
    }


    private fun addAuth(original: Request, token: Token): Request {

        val request = original.newBuilder()

            .removeHeader("Content-Type")
            .removeHeader("Accept")
            .removeHeader(HEADER_AUTHORIZATION)

            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .addHeader(HEADER_AUTHORIZATION, token.access)
            .build()

        Log.d("REST", "${request.headers()}")

        return request
    }

}