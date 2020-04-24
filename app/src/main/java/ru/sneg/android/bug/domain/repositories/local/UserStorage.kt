package ru.sneg.android.bug.domain.repositories.local

import io.realm.Realm
import ru.sneg.android.bug.domain.repositories.models.rest.Token
import ru.sneg.android.bug.domain.repositories.models.rest.User
import ru.sneg.android.bug.domain.repositories.models.realm.TokenRealm
import ru.sneg.android.bug.domain.repositories.models.realm.UserRealm
import ru.sneg.android.bug.domain.repositories.models.toBase
import ru.sneg.android.bug.domain.repositories.models.toRealm
import javax.inject.Inject

class UserStorage {

    private lateinit var score: List<User?>
    private var user: User? = null

    @Inject
    constructor()

    fun save(user: User) {
        this.user = user

        Realm.getDefaultInstance().use {
            it.executeTransaction { realm ->
                user.toRealm()?.let { realm.copyToRealmOrUpdate(it) }
            }
        }
    }

    fun save(token: Token) {
        user?.token = token

        Realm.getDefaultInstance().use {
            it.executeTransaction { realm ->
                it.where(UserRealm::class.java).findFirst()?.let {
                    it.token = token.toRealm()
                    realm.copyToRealmOrUpdate(it)
                }
            }
        }
    }

    fun dropCredentials() {
        user = null

        Realm.getDefaultInstance().use {
            it.executeTransaction { realm ->
                it.where(UserRealm::class.java).findAll().deleteAllFromRealm()
                it.where(TokenRealm::class.java).findAll().deleteAllFromRealm()
            }
        }
    }

    fun getUser(): User? {

        user?.let {
            return it
        }

        Realm.getDefaultInstance().use {
            return it.where(UserRealm::class.java).findFirst()?.toBase().apply { user = this }
        }
    }

    fun save(score: List<User?>) {
        this.score = score

        Realm.getDefaultInstance().use {
            it.executeTransaction { realm ->
                user.toRealm()?.let { realm.copyToRealmOrUpdate(it) }
            }
        }
    }
}
