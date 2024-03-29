package ru.akumakeito.githubusers.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.akumakeito.githubusers.databinding.LoadStateBinding

class PagingLoadStateAdapter(
) : LoadStateAdapter<PagingLoadStateAdapter.LoadStateViewHolder>() {
    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder =
        LoadStateViewHolder(
            LoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        )
    class LoadStateViewHolder(
        private val binding : LoadStateBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) {

            binding.apply {

                progress.isVisible = loadState is LoadState.Loading


            }

        }

    }
}