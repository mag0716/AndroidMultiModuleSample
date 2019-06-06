package com.github.mag0716.usercase

import com.github.mag0716.multimodulesample.datasource.IDataRepository
import timber.log.Timber
import timber.log.debug

internal class GetDataListUseCase(
    private val repository: IDataRepository,
    private val view: IDataListView
) : IGetDataListUseCase {
    override suspend fun execute() {
        Timber.debug { "GetDataListUseCase($this) : repository = $repository" }
        view.showLoading()

        view.showDataList(repository.loadDataList())

        view.showDataList(repository.refreshDataList())

        view.dismissLoading()
    }
}