package com.github.mag0716.multimodulesample.datasource;

import com.github.mag0716.api.ApiService
import com.github.mag0716.api.response.DataResponse
import com.github.mag0716.api.response.DetailResponse
import com.github.mag0716.multimodulesample.datasource.converter.toDataList
import com.github.mag0716.multimodulesample.datasource.converter.toDetail
import com.github.mag0716.multimodulesample.datasource.model.Data
import com.github.mag0716.multimodulesample.datastore.model.Detail
import timber.log.Timber
import timber.log.debug

internal class DataRepository(val apiService: ApiService) : IDataRepository {

    // TODO: キャッシュ機能
    // 変換したデータをキャッシュすべき？
    private var dataListCached: List<DataResponse>? = null
    private val detailMap = mutableMapOf<Int, DetailResponse>()

    // TODO:リフレッシュ機構

    override suspend fun fetchDataListOrCache(): List<Data> {
        Timber.debug { "fetchDataListOrCache($this) : apiService = $apiService" }
        if (dataListCached == null) {
            dataListCached = apiService.data()
        }
        return checkNotNull(dataListCached).toDataList()
    }

    override suspend fun fetchDataDetailOrCache(id: Int): Detail {
        Timber.debug { "fetchDataDetailOrCache($this) : apiService = $apiService" }
        if (detailMap.contains(id).not()) {
            val detail = apiService.detail(id)
            detailMap[id] = detail
        }
        return checkNotNull(detailMap[id]).toDetail()
    }
}
