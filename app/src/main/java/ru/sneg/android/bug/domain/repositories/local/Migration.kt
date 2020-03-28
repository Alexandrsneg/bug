package ru.sneg.android.bug.domain.repositories.local

import io.realm.DynamicRealm
import io.realm.RealmMigration

class Migration : RealmMigration {
    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {

        var version = oldVersion

    }

}