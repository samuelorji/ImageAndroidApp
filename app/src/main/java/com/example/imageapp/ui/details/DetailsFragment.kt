package com.example.imageapp.ui.details

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.imageapp.R
import com.example.imageapp.databinding.DetailsFragmentBinding

class DetailsFragment : Fragment(R.layout.details_fragment) {
    private val args by navArgs<DetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = DetailsFragmentBinding.bind(view)

        binding.apply {
            val photo = args.photo
            Glide.with(this@DetailsFragment)
                .load(photo.urls.regular)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.ic_baseline_error_outline_24)
                .listener(object: RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        image.isVisible = true
                        blurView.isVisible = true
                        imageAuthor.isVisible = true
                        imageDescription.isVisible = photo.description != null
                        imageReferral.isVisible = true
                        return false
                    }
                })
                .into(image)

            imageDescription.text = photo.description
            imageAuthor.text = photo.user.username
            imageReferral.apply {
                text = "Image by ${photo.user.username} on unsplash"
                setOnClickListener{
                    val imageUri = Uri.parse(photo.user.getReferralUrl())
                    val intent = Intent(Intent.ACTION_VIEW,imageUri)
                    startActivity(intent)
                }
                paint.isUnderlineText = true

            }

        }
    }
}