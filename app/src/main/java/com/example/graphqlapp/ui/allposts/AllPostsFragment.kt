package com.example.graphqlapp.ui.allposts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.graphqlapp.R
import com.example.graphqlapp.databinding.FragmentAllPostsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllPostsFragment : Fragment() {

    private val viewModel: AllPostsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAllPostsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_all_posts, container, false
        )

        binding.lifecycleOwner = viewLifecycleOwner
        binding.postListView.adapter = AllPostsAdapter(OnClickListener {
             viewModel.displayDetailPost(it)
        })
        binding.retry.setOnClickListener {
            viewModel.load()
        }

        viewModel.state.observe(this.viewLifecycleOwner, Observer { state ->
            when (state) {
                is AllPostViewState.Failure -> {
                    binding.progressBar.isVisible = false
                    binding.retry.isVisible = true
                    binding.errorText.isVisible = true
                    binding.errorText.text = state.message
                }
                AllPostViewState.Loading -> {
                    binding.progressBar.isVisible = true
                    binding.errorText.isVisible = false
                    binding.retry.isVisible = false
                }
                is AllPostViewState.Success -> {
                    binding.progressBar.isVisible = false
                    binding.errorText.isVisible = false
                    binding.retry.isVisible = false

                    (binding.postListView.adapter as AllPostsAdapter)
                        .submitList(state.data)
                }
            }

        })

        binding.viewModel = viewModel
        binding.postListView.setHasFixedSize(false)

        viewModel.navigateToDetailFragment.observe(this.viewLifecycleOwner, Observer {
            if (it != null) {
                this.findNavController().navigate(
                    AllPostsFragmentDirections.actionAllPostsFragmentToPostDetailFragment(it.id)

                )
                viewModel.doneNavigating()
            }
        })

        return binding.root
    }


}