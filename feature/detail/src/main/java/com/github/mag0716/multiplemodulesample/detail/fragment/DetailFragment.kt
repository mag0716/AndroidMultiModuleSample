package com.github.mag0716.multiplemodulesample.detail.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.github.mag0716.multimodulesample.datastore.model.Detail
import com.github.mag0716.multiplemodulesample.App
import com.github.mag0716.multiplemodulesample.detail.R
import com.github.mag0716.usercase.IDataDetailView
import com.github.mag0716.usercase.IGetDataDetailUseCase
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailFragment : Fragment(), CoroutineScope, IDataDetailView {

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var getDataDetailUseCase: IGetDataDetailUseCase

    private val args by navArgs<DetailFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()

        getDataDetailUseCase = (requireActivity().application as App).provideGetDataDetailUseCase(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchDataDetail(args.id)
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun showDataDetail(detail: Detail) {
        titleText.text = detail.title
    }

    override fun showLoading() {
        // TODO: ProgressBar, エラー処理
    }

    override fun dismissLoading() {
        // TODO: ProgressBar, エラー処理
    }

    private fun fetchDataDetail(id: Int) = launch {
        getDataDetailUseCase.execute(id)
    }
}