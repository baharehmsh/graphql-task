package com.example.graphqlapp.ui.allposts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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

        binding.viewModel = viewModel
        binding.postListView.setHasFixedSize(false)

        return binding.root
    }


}