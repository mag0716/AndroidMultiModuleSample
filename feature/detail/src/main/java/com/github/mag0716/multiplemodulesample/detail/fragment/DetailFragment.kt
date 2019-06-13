package com.github.mag0716.multiplemodulesample.detail.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.github.mag0716.multimodulesample.datastore.model.Detail
import com.github.mag0716.multiplemodulesample.detail.R
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private val args by navArgs<DetailFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(DetailViewModel::class.java)
        } ?: throw IllegalStateException("invalid Activity")
        viewModel.getDetail().observe(this, Observer<Detail> {
            titleText.text = it.title
        })
        viewModel.getProgress().observe(this, Observer<Boolean> {
            progress.visibility = if (it) View.VISIBLE else View.GONE
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchDetail(args.id)
    }
}