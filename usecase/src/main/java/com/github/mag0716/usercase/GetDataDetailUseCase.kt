package com.github.mag0716.usercase

import com.github.mag0716.multimodulesample.datasource.IDataRepository
import timber.log.Timber
import timber.log.debug

internal class GetDataDetailUseCase(
    private val repository: IDataRepository,
    private val view: IDataDetailView
) : IGetDataDetailUseCase {
    override suspend fun execute(id: Int) {
        Timber.debug { "GetDataDetailUseCase($this) : repository = $repository" }
        view.showLoading()

        val cache = repository.loadDataDetail(id)
        if (cache != null) {
            view.showDataDetail(cache)
        }
        val detail = repository.refreshDataDetail(id)
        view.showDataDetail(detail)

        view.dismissLoading()
    }
}