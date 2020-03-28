package ru.sneg.android.bug.domain.repositories.models.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class UserRealm : RealmObject()  {

    @PrimaryKey
    var id: Int = 0
    var login: String? = null
    var avatarUrl: String = null

}