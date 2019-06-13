package com.github.mag0716.multiplemodulesample.detail.fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.mag0716.multimodulesample.datastore.model.Detail
import com.github.mag0716.multiplemodulesample.App
import com.github.mag0716.usercase.IDataDetailView
import com.github.mag0716.usercase.IGetDataDetailUseCase
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : AndroidViewModel(application), IDataDetailView {

    private val getDetailUseCase: IGetDataDetailUseCase =
        (application as App).provideGetDataDetailUseCase(this)
    private val progress = MutableLiveData<Boolean>()
    private val detail = MutableLiveData<Detail>()

    fun getProgress(): LiveData<Boolean> {
        return progress
    }

    fun getDetail(): LiveData<Detail> {
        return detail
    }

    override fun showDataDetail(detail: Detail) {
        this.detail.value = detail
    }

    override fun showLoading() {
        progress.value = true
    }

    override fun dismissLoading() {
        progress.value = false
    }

    fun fetchDetail(id: Int) {
        viewModelScope.launch {
            getDetailUseCase.execute(id)
        }
    }
}