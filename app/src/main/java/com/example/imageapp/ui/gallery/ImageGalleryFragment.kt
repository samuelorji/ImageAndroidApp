package com.example.imageapp.ui.gallery

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imageapp.R
import com.example.imageapp.adapters.ImageLoadStateAdapter
import com.example.imageapp.adapters.UnsplashImageAdapter
import com.example.imageapp.databinding.GalleryFragmentBinding
import com.example.imageapp.models.Image
import com.example.imageapp.viewmodels.ImageGalleryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.gallery_fragment.*
import kotlinx.android.synthetic.main.image_load_state_footer.*

@AndroidEntryPoint
class ImageGalleryFragment : Fragment(R.layout.gallery_fragment), UnsplashImageAdapter.OnimageClickedListener {

    override fun onImageClicked(image: Image, position: Int) {
        val action = ImageGalleryFragmentDirections.actionImageGalleryFragment3ToDetailsFragment(image)
        findNavController().navigate(action)
    }

    val viewModel by viewModels<ImageGalleryViewModel> ()
    var binding : GalleryFragmentBinding? = null

    fun isConnectedToInternet() : Boolean {
        val cm = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
            capabilities?.hasCapability(NET_CAPABILITY_INTERNET) == true

        } else {
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            activeNetwork?.isConnectedOrConnecting == true
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = GalleryFragmentBinding.bind(view)
        val adapter = UnsplashImageAdapter(this)

        binding?.apply {
            recyclerView.setHasFixedSize(true)
            buttonRetry.setOnClickListener {
                adapter.retry()
            }
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                    header = ImageLoadStateAdapter{ adapter.retry() },
                    footer = ImageLoadStateAdapter{ adapter.retry() }
            )
            val gridLayoutManager = GridLayoutManager(activity,2)
            gridLayoutManager.spanSizeLookup =  object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position == adapter.itemCount  && adapter.itemCount > 0) {
                        2
                    } else {
                        1
                    }
                }
            }
            recyclerView.layoutManager = gridLayoutManager


        }

        viewModel.images.observe(viewLifecycleOwner, Observer { images ->
            adapter.submitData(viewLifecycleOwner.lifecycle, images)
        })

        adapter.addLoadStateListener { loadState ->
            binding?.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                textViewError.isVisible = loadState.source.refresh is LoadState.Error

                if(loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached
                        && adapter.itemCount < 1){
                    // no results found
                    recyclerView.isVisible = false
                    textViewEmpty.isVisible = true
                } else {
                    textViewEmpty.isVisible = false
                }

            }
        }


        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.gallery_menu, menu)

        val searchItem = menu.findItem(R.id.image_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.searchFor(it)
                    binding?.recyclerView?.scrollToPosition(0)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null // remove reference to binding
    }
}