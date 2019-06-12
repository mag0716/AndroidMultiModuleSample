package com.github.mag0716.usercase

import com.github.mag0716.multimodulesample.datasource.IDataRepository

fun provideGetDataListUseCase(
    dataRepository: IDataRepository,
    view: IDataListView
): IGetDataListUseCase {
    return GetDataListUseCase(dataRepository, view)
}

fun provideGetDataDetailUseCase(dataRepository: IDataRepository, view: IDataDetailView): IGetDataDetailUseCase {
    return GetDataDetailUseCase(dataRepository, view)
}