package com.example.imageapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.imageapp.R
import androidx.recyclerview.widget.DiffUtil
import com.example.imageapp.databinding.PictureLayoutBinding
import com.example.imageapp.models.Image

class UnsplashImageAdapter(private val imageClickedListener : OnimageClickedListener) : PagingDataAdapter<Image, UnsplashImageAdapter.UnsplashImageViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnsplashImageViewHolder {

        val view = PictureLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UnsplashImageViewHolder(view)
    }


    override fun onBindViewHolder(holder: UnsplashImageViewHolder, position: Int) {
        val image = getItem(position)
        if (image != null) {
            holder.bind(image)
        }
    }

    interface OnimageClickedListener{
        fun onImageClicked(image : Image, position : Int) : Unit
    }
    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Image>(){
            override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {

                return  oldItem.id.contentEquals(newItem.id)
            }

            override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
                return oldItem == newItem
            }
        }
    }
    inner class UnsplashImageViewHolder(private val binding: PictureLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        val imageView = binding.itemMovieImg

        init {
            binding.root.setOnClickListener{
                val position = bindingAdapterPosition
                if(position != RecyclerView.NO_POSITION){
                    val image = getItem(position)
                    image?.let {  imageClickedListener.onImageClicked(it, position)}

                }
            }
        }

        fun bind(image : Image) =
                binding.apply {
                    Glide.with(itemView)
                            .load(image.urls.regular)
                            .centerCrop()
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .error(R.drawable.ic_baseline_error_outline_24)
                            .into(imageView)
                }
    }
}

