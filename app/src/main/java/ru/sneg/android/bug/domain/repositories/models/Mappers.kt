package ru.sneg.android.bug.domain.repositories.models

import ru.sneg.android.bug.domain.repositories.models.realm.TokenRealm

fun Token?.toRealm(): TokenRealm? {
this ?: return null

    return TokenRealm().let {
        it.access = access
        it.refresh = refresh
        true
    }
}


