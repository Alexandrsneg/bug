package ru.sneg.android.bug.domain.repositories.local

import ru.sneg.android.bug.domain.di.models.User
import javax.inject.Inject

class UserStorage {

    var user: User? = null
        private set

    @Inject

    constructor()

    fun save(user: User){
    this.user = user
}
    fun dropCredentials() {
        user = null
    }
}