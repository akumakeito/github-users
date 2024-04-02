package ru.akumakeito.githubusers.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.akumakeito.githubusers.databinding.FragmentUserListBinding
import ru.akumakeito.githubusers.domain.model.User
import ru.akumakeito.githubusers.presentation.adapter.OnInteractionListener
import ru.akumakeito.githubusers.presentation.adapter.UserAdapter
import ru.akumakeito.githubusers.presentation.viewmodel.UserViewModel

@AndroidEntryPoint
class FragmentUserList : Fragment() {

    val viewModel: UserViewModel by viewModels()
    private lateinit var binding: FragmentUserListBinding
    private lateinit var adapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentUserListBinding.inflate(inflater, container, false)

        adapter = UserAdapter(object : OnInteractionListener {
            override fun onUserClicked(user: User) {
                Log.d("FragmentUserDetails", "user = $user")

                Log.d("FragmentUserDetails", "before navigation")

                findNavController().navigate(
                    FragmentUserListDirections.actionFragmentUserListToFragmentUserDetails(
                        user.login
                    )

                )
            }
        })



        binding.rvUserList.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.userList.collect {
                adapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            adapter.loadStateFlow.collect {
                binding.swiperefresh.isRefreshing = it.refresh is LoadState.Loading
            }
        }

        binding.swiperefresh.setOnRefreshListener {
            adapter.refresh()
            binding.swiperefresh.isRefreshing = false
        }


        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { state ->
                binding.swiperefresh.isRefreshing =
                    state.refresh is LoadState.Loading


            }
        }

    }


}