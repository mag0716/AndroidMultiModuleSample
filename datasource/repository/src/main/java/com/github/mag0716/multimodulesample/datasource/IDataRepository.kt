package com.github.mag0716.multimodulesample.datasource

import com.github.mag0716.multimodulesample.datasource.model.Data
import com.github.mag0716.multimodulesample.datastore.model.Detail

interface IDataRepository {

    suspend fun refreshDataList(): List<Data>

    suspend fun refreshDataDetail(id: Int): Detail

    suspend fun loadDataList(): List<Data>

    suspend fun loadDataDetail(id: Int): Detail?
}