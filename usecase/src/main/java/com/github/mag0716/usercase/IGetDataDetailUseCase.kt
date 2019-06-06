package com.github.mag0716.usercase

import com.github.mag0716.multimodulesample.datastore.model.Detail

interface IGetDataDetailUseCase {
    suspend fun execute(id: Int)
}

interface IDataDetailView {
    fun showDataDetail(detail: Detail)

    fun showLoading()

    fun dismissLoading()
}