package com.github.mag0716.multiplemodulesample.list.fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.mag0716.multimodulesample.datasource.model.Data
import com.github.mag0716.multiplemodulesample.App
import com.github.mag0716.usercase.IDataListView
import com.github.mag0716.usercase.IGetDataListUseCase
import kotlinx.coroutines.launch
import timber.log.Timber
import timber.log.debug

class ListViewModel(application: Application) : AndroidViewModel(application), IDataListView {

    private val getDataListUseCase: IGetDataListUseCase =
        (application as App).provideGetDataListUseCase(this@ListViewModel)
    private val progress = MutableLiveData<Boolean>()
    private val dataList = MutableLiveData<List<Data>>()

    fun getProgress(): LiveData<Boolean> {
        return progress
    }

    fun getDataList(): LiveData<List<Data>> {
        return dataList
    }

    override fun showDataList(dataList: List<Data>) {
        this.dataList.value = dataList
    }

    override fun showLoading() {
        progress.value = true
    }

    override fun dismissLoading() {
        progress.value = false
    }

    fun fetchData() {
        val progressing = progress.value ?: false
        Timber.debug { "[$this] fetchData() : $progressing" }
        if (progressing.not()) {
            viewModelScope.launch {
                getDataListUseCase.execute()
            }
        }
    }
}