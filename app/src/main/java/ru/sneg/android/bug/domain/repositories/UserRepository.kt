package ru.sneg.android.bug.domain.repositories

import android.os.SystemClock
import ru.sneg.android.bug.base.SubRX
import ru.sneg.android.bug.base.standardSubscribeIO
import ru.sneg.android.bug.domain.repositories.models.rest.Token
import ru.sneg.android.bug.domain.repositories.models.rest.User
import ru.sneg.android.bug.domain.repositories.local.UserStorage
import ru.sneg.android.bug.domain.repositories.rest.api.UserRestApi
import java.net.HttpURLConnection
import javax.inject.Inject


class UserRepository @Inject constructor(
    private val storage: UserStorage,
    private val rest: UserRestApi
) {

    fun registration(observer: SubRX<User>, login: String, pass: String) {

        rest.registration(login, pass)
            .doOnNext { storage.save(it) }
            .standardSubscribeIO(observer)
    }

    fun login(observer: SubRX<User>, login: String, pass: String) {

        rest.login(login, pass)
            .doOnNext { storage.save(it) }
            .standardSubscribeIO(observer)
    }

    fun getUser() = storage.getUser()

    fun refreshToken(token: Token, onRetry: (Int) -> Boolean = { it == HttpURLConnection.HTTP_UNAUTHORIZED }): Token? {

        val response = rest.refreshToken(token.refresh).execute()
        response.body()?.let {
            storage.save(it)
            return it
        }

        if (onRetry(response.code())) {
            SystemClock.sleep(500)
            return refreshToken(token)
        }

        return null
    }
//получение списка всех пользователей SWAGER
    fun users(observer: SubRX<List<User>>, token: Token?) {
        if (token != null) {
            rest.users(accessToken = token.access)
                .doOnNext { storage.save(it) }
                .standardSubscribeIO(observer)
        }
    }
}