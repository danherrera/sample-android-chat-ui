package io.agileninja.androidchatui.ui

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.databinding.library.baseAdapters.BR
import kotlin.properties.Delegates

class LayoutViewModelAdapter : RecyclerView.Adapter<LayoutViewModelAdapter.LayoutViewHolder>() {

    var viewModels: List<LayoutViewModel> by Delegates.observable(emptyList()) { _, oldList, newList ->
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] == newList[newItemPosition]
            }

            override fun getOldListSize(): Int = oldList.size

            override fun getNewListSize(): Int = newList.size

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition].layout == newList[newItemPosition].layout
            }
        }).dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): LayoutViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(viewType, parent, false)
        return LayoutViewHolder(DataBindingUtil.bind(view)!!)
    }

    override fun getItemCount(): Int = viewModels.size

    override fun getItemViewType(position: Int): Int = viewModels[position].layout

    override fun onBindViewHolder(holder: LayoutViewHolder, position: Int) {
        holder.binding.setVariable(BR.viewModel, viewModels[position])
    }

    class LayoutViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)
}
