package ru.sneg.android.bug

import android.app.Application
import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration

class App : Application() {

companion object {

lateinit var appContext: Context
}
    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext
    }

    private fun initRealm(){
        Realm.init()
        Realm.setDefaultConfiguration(RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
    }
}