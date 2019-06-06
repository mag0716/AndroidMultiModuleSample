package com.github.mag0716.multiplemodulesample

import android.app.Application
import com.github.mag0716.api.ApiService
import com.github.mag0716.api.ApiServiceModule
import com.github.mag0716.multimodulesample.datasource.DataSourceModule
import com.github.mag0716.multimodulesample.datasource.IDataRepository
import com.github.mag0716.usercase.*
import timber.log.LogcatTree
import timber.log.Timber

class App : Application() {

    val apiService: ApiService by lazy {
        ApiServiceModule.provide()
    }

    val dataSource: IDataRepository by lazy {
        DataSourceModule.provide(apiService)
    }

    override fun onCreate() {
        super.onCreate()

        Timber.plant(LogcatTree("MultipleModuleSample"))
    }

    fun provideGetDataListUseCase(view: IDataListView): IGetDataListUseCase {
        return provideGetDataListUseCase(dataSource, view)
    }

    fun provideGetDataDetailUseCase(view: IDataDetailView): IGetDataDetailUseCase {
        return provideGetDataDetailUseCase(dataSource, view)
    }
}