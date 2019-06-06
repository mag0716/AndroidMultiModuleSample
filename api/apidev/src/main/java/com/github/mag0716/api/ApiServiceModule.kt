package com.github.mag0716.api

import com.github.mag0716.api.response.DataResponse
import com.github.mag0716.api.response.DetailResponse
import kotlinx.coroutines.delay
import timber.log.Timber
import timber.log.debug

object ApiServiceModule {

    fun provide(): ApiService {
        return object : ApiService {
            override suspend fun data(): List<DataResponse> {
                Timber.debug { "data" }
                return createData()
            }

            override suspend fun detail(id: Int): DetailResponse {
                Timber.debug { "detail($id)" }
                return createDetail(id)
            }
        }
    }

    private suspend fun createData(): List<DataResponse> {
        delay(1000)
        return (0..10).map {
            DataResponse(it, "title$it")
        }.toList()
    }

    private suspend fun createDetail(id: Int): DetailResponse {
        delay(1000)
        return DetailResponse(
            id,
            "title$id",
            "description$id",
            createdTime = System.currentTimeMillis()
        )
    }

}