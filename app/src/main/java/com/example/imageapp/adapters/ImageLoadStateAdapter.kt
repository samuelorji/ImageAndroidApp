package com.example.imageapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.imageapp.databinding.ImageLoadStateFooterBinding

class ImageLoadStateAdapter(private val retry : () -> Unit) : LoadStateAdapter<ImageLoadStateAdapter.ImageLoadStateViewHolder> (){
    override fun onBindViewHolder(holder: ImageLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)


    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ImageLoadStateViewHolder {
        return ImageLoadStateViewHolder(
                ImageLoadStateFooterBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        )
    }

    inner class ImageLoadStateViewHolder(private val binding : ImageLoadStateFooterBinding) : RecyclerView.ViewHolder(binding.root){

        init {
            binding.loadRetry.setOnClickListener {
                retry.invoke()
            }
        }
        fun bind(loadState: LoadState){
            binding.apply {
                loadingProgressbar.isVisible = loadState is LoadState.Loading
                loadRetry.isVisible = loadState !is LoadState.Loading
                loadError.isVisible = loadState !is LoadState.Loading
            }
        }

    }
}