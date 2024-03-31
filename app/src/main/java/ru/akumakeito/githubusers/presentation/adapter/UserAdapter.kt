package ru.akumakeito.githubusers.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.akumakeito.githubusers.R
import ru.akumakeito.githubusers.databinding.ItemUserListBinding
import ru.akumakeito.githubusers.domain.model.User

interface OnInteractionListener {
    fun onUserClicked(user: User)
}

class UserAdapter (
    private val onInteractionListener: OnInteractionListener
) :PagingDataAdapter<User, UserViewHolder>(UserDiffCallback()){
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position) ?: return
        holder.bind(user)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(onInteractionListener, binding)
    }
}

class UserDiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        if (oldItem::class != newItem::class) {
            return false
        }

        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}


class UserViewHolder(
    private val onInteractionListener: OnInteractionListener,
    private val binding: ItemUserListBinding
) : RecyclerView.ViewHolder(binding.root){

    fun bind(user: User) {

        binding.apply {
            Glide.with(ivAvatar)
                .load(user.avatarUrl)
                .circleCrop()
                .placeholder(R.drawable.ic_avatar_placeholder)
                .error(R.drawable.baseline_error_24)
                .into(ivAvatar)

            Log.d("UserRemoteMediator", "user id: ${user.id}")

            tvLogin.text = user.login
            tvUserId.text = user.id.toString()

            userItem.setOnClickListener {
                onInteractionListener.onUserClicked(user)
            }
        }

    }

}
