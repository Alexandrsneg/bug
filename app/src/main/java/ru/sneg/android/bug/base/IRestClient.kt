package ru.sneg.android.bug.base

interface IRestClient {
    fun <S> createService(serviceClass: Class<S>): S

    fun cancelAllRequests()
}