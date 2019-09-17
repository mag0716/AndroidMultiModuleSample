package com.github.mag0716.multiplemodulesample.list.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.github.mag0716.multimodulesample.datasource.model.Data
import com.github.mag0716.multiplemodulesample.list.R
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.view.*
import kotlinx.android.synthetic.main.item_list.view.*

class ListFragment : Fragment() {

    private val viewModel: ListViewModel by viewModels()
    private lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = Adapter(requireContext())
        viewModel.getDataList().observe(this, Observer<List<Data>> {
            adapter.addData(it)
        })
        viewModel.getProgress().observe(this, Observer<Boolean> {
            progress.visibility = if (it) View.VISIBLE else View.GONE
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.list.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchData()
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
        val titleText: TextView = itemView.titleText
    }
}