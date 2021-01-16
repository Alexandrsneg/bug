package ru.sneg.android.bug.base

import java.lang.reflect.ParameterizedType

// Базовый класс RestApi который позволит не писать явный код на создание сервиса выполнения запросов

abstract class ABaseRestApi<S>(private val client: IRestClient) : IRestApi {
    val service: S


    init {
        val type = javaClass.genericSuperclass as ParameterizedType
        val clazz = type.actualTypeArguments[0] as Class<S>
        service = client.createService(clazz)
    }


    override fun cancelAllRequests() {
        client.cancelAllRequests()
    }
}
