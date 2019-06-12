package com.github.mag0716.multimodulesample.datasource;

import com.github.mag0716.api.ApiService
import com.github.mag0716.api.response.DataResponse
import com.github.mag0716.api.response.DetailResponse
import com.github.mag0716.multimodulesample.datasource.converter.toDataList
import com.github.mag0716.multimodulesample.datasource.converter.toDetail
import com.github.mag0716.multimodulesample.datasource.model.Data
import com.github.mag0716.multimodulesample.datastore.model.Detail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import timber.log.debug
import kotlin.coroutines.coroutineContext

internal class DataRepository(val apiService: ApiService) : IDataRepository {

    // 変換したデータをキャッシュすべき？
    private var dataListCached: List<DataResponse>? = null
    private val detailMap = mutableMapOf<Int, DetailResponse>()

    override suspend fun refreshDataList(): List<Data> = withContext(Dispatchers.IO) {
        Timber.debug { "[$coroutineContext] refreshDataList() : apiService = $apiService" }
        dataListCached = apiService.data()
        checkNotNull(dataListCached).toDataList()
    }

    override suspend fun refreshDataDetail(id: Int): Detail = withContext(Dispatchers.IO) {
        Timber.debug { "[$coroutineContext] refreshDataDetail($id) : apiService = $apiService" }
        val detail = apiService.detail(id)
        detailMap[id] = detail
        checkNotNull(detailMap[id]).toDetail()
    }

    override suspend fun loadDataList(): List<Data> {
        Timber.debug { "[$coroutineContext] loadDataList()" }
        return dataListCached?.toDataList() ?: emptyList()
    }

    override suspend fun loadDataDetail(id: Int): Detail? {
        Timber.debug { "[$coroutineContext] loadDataDetail($id)" }
        return detailMap[id]?.toDetail()
    }
}
