package com.example.graphqlapp.ui.allposts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.graphqlapp.databinding.ItemPostListBinding
import com.example.graphqlapp.data.model.Post

class AllPostsAdapter(private val clickListener: OnClickListener) :
    ListAdapter<Post, AllPostsAdapter.PostViewHolder>(DiffCallback) {

    class PostViewHolder(var binding: ItemPostListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            binding.post = post
            binding.executePendingBindings()
        }


    }

    companion object DiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            ItemPostListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.itemView.setOnClickListener {
            clickListener.onClick(post)
        }
        holder.bind(post)
    }

}

class OnClickListener(val clickListener: (post: Post) -> Unit) {
    fun onClick(post: Post) = clickListener(post)
}
