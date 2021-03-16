package com.example.android.politicalpreparedness.election.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.databinding.ItemElectionBinding
import com.example.android.politicalpreparedness.databinding.ItemHeaderBinding
import com.example.android.politicalpreparedness.network.models.Election

class ElectionListAdapter(
    private val headerTitle: String,
    private val onClickListener: (Election) -> Unit
) : ListAdapter<DataItem, RecyclerView.ViewHolder>(DiffCallback) {

    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1

    fun addHeaderAndSubmitList(list: List<Election>?) {
        val items = when (list) {
            null -> listOf(DataItem.Header)
            else -> listOf(DataItem.Header) + list.map { DataItem.ElectionItem(it) }
        }
        submitList(items)
    }


    companion object DiffCallback : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> ViewHolderElectionHeader.from(parent)
            TYPE_ITEM -> ViewHolderElectionBinding.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> TYPE_HEADER
            is DataItem.ElectionItem -> TYPE_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolderElectionHeader -> {
                holder.bind(headerTitle)
            }
            is ViewHolderElectionBinding -> {
                holder.bind(
                    (getItem(position) as DataItem.ElectionItem).election,
                    onClickListener
                )
            }
        }
    }

    class ViewHolderElectionBinding(private var binding: ItemElectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(election: Election, onClickListener: (Election) -> Unit) {
            binding.election = election
            binding.root.setOnClickListener { onClickListener(election) }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolderElectionBinding {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemElectionBinding.inflate(layoutInflater, parent, false)
                return ViewHolderElectionBinding(binding)
            }
        }
    }

    class ViewHolderElectionHeader(private var binding: ItemHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(title: String) {
            binding.title = title
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolderElectionHeader {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemHeaderBinding.inflate(layoutInflater, parent, false)
                return ViewHolderElectionHeader(binding)
            }
        }
    }
}

sealed class DataItem {
    data class ElectionItem(val election: Election) : DataItem() {
        override val id = election.id
    }

    object Header : DataItem() {
        override val id = Int.MIN_VALUE
    }

    abstract val id: Int
}