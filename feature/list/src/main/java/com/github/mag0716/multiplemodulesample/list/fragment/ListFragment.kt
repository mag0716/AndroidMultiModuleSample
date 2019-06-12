package com.github.mag0716.multiplemodulesample.list.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.github.mag0716.multimodulesample.datasource.model.Data
import com.github.mag0716.multiplemodulesample.App
import com.github.mag0716.multiplemodulesample.list.R
import com.github.mag0716.usercase.IDataListView
import com.github.mag0716.usercase.IGetDataListUseCase
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.view.*
import kotlinx.android.synthetic.main.item_list.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ListFragment : Fragment(), IDataListView, CoroutineScope by MainScope() {

    private lateinit var getDataListUseCase: IGetDataListUseCase

    private lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getDataListUseCase = (requireActivity().application as App).provideGetDataListUseCase(this)
        adapter = Adapter(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.list.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        fetchData()
    }

    override fun onStop() {
        super.onStop()
        cancel()
    }

    override fun showDataList(dataList: List<Data>) {
        adapter.addData(dataList)
    }

    override fun showLoading() {
        progress.visibility = View.VISIBLE
    }

    override fun dismissLoading() {
        progress.visibility = View.GONE
    }

    private fun fetchData() = launch {
        getDataListUseCase.execute()
    }

    private class Adapter(val context: Context) : RecyclerView.Adapter<ViewHolder>() {

        val dataList: MutableList<Data> = mutableListOf()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.item_list,
                    parent,
                    false
                )
            )
        }

        override fun getItemCount() = dataList.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemView.setOnClickListener {
                val action =
                    ListFragmentDirections.moveDetail(dataList[position].id)
                it.findNavController().navigate(action)
            }
            holder.titleText.text = dataList[position].title
        }

        fun addData(dataList: List<Data>) {
            this.dataList.addAll(dataList)
            notifyDataSetChanged()
        }
    }

    private class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleText = itemView.titleText
    }
}