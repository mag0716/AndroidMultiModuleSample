package com.github.mag0716.usercase

import com.github.mag0716.multimodulesample.datasource.model.Data

interface IGetDataListUseCase {
    suspend fun execute()
}

interface IDataListView {
    fun showDataList(dataList: List<Data>)

    fun showLoading()

    fun dismissLoading()
}