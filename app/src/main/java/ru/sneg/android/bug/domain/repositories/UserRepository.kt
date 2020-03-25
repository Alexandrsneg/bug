package ru.sneg.android.bug.domain.repositories

import ru.sneg.android.bug.base.SubRX
import ru.sneg.android.bug.base.standardSubscribeIO
import ru.sneg.android.bug.domain.di.models.User
import ru.sneg.android.bug.domain.repositories.local.UserStorage
import ru.sneg.android.bug.domain.repositories.rest.api.UserRestApi
import javax.inject.Inject


class UserRepository {

    private val storage: UserStorage
    private val rest: UserRestApi

    @Inject
    constructor(storage: UserStorage, rest: UserRestApi) {
        this.storage = storage
        this.rest = rest
    }

    fun signUp(
        observer: SubRX<User>,
        login: String,
        pass: String
    ) {

        rest.signUp(login, pass)
            .doOnNext { storage.save(it) }
            .standardSubscribeIO(observer)
    }

    fun login(observer: SubRX<User>, login: String, pass: String) {

        rest.login(login, pass)
            .doOnNext { storage.save(it) }
            .standardSubscribeIO(observer)
    }

    fun getUser() = storage.user

    fun refreshToken(token: Token): Token {
        TODO("Not yet implemented")
    }




}
