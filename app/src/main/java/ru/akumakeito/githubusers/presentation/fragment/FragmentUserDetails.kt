package ru.akumakeito.githubusers.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.akumakeito.githubusers.R
import ru.akumakeito.githubusers.databinding.FragmentUserInfoBinding
import ru.akumakeito.githubusers.domain.model.User
import ru.akumakeito.githubusers.presentation.Utils
import ru.akumakeito.githubusers.presentation.viewmodel.UserViewModel

@AndroidEntryPoint
class FragmentUserDetails : Fragment() {

    val viewModel: UserViewModel by viewModels()
    private lateinit var binding: FragmentUserInfoBinding


    private val args: FragmentUserDetailsArgs by navArgs()
    private val username: String by lazy {
        args.username

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        Log.d("FragmentUserDetails", "username = $username")
        binding = FragmentUserInfoBinding.inflate(inflater, container, false)


        viewModel.getUserByUsername(username)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.currentUser.collectLatest { it ->
                it?.let { user ->
                    binding.apply {

                        Glide.with(this@FragmentUserDetails)
                            .load(user.avatarUrl)
                            .circleCrop()
                            .placeholder(R.drawable.ic_avatar_placeholder)
                            .error(R.drawable.baseline_error_24)
                            .into(ivAvatar)

                        tvName.text = user.name
                        tvEmail.text = user.email
                        if (user.company != null) {
                            tvOrganization.text = user.company
                        } else {
                            tvOrganization.visibility = View.GONE
                        }

                        tvFollowersCount.text = getString(R.string.followers_count, user.followers)
                        tvFollowingCount.text = getString(R.string.following_count, user.following)

                        tvAccountCreationDate.text =
                            if (user.createdAt != null) Utils.convertStringToDateStrin(user.createdAt!!) else "no date"


                    }
                }
            }
        }

    }


}