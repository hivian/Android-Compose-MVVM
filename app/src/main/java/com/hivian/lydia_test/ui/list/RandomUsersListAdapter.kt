package com.hivian.lydia_test.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hivian.lydia_test.business.model.domain.RandomUserDomain
import com.hivian.lydia_test.databinding.RandomUserItemBinding
import com.hivian.lydia_test.presentation.home.HomeViewModel

class RandomUsersListAdapter(val viewModel: HomeViewModel) : ListAdapter<RandomUserDomain, RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<RandomUserDomain>() {
    override fun areItemsTheSame(old: RandomUserDomain, new: RandomUserDomain): Boolean = old.id == new.id
    override fun areContentsTheSame(old: RandomUserDomain, new: RandomUserDomain): Boolean = old == new
}) {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        RandomUserViewHolder(RandomUserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RandomUserViewHolder).bind(viewModel, getItem(position))
    }

    override fun getItemId(position: Int): Long = getItem(position).id.toLong()

}
