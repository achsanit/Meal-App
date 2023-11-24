package com.achsanit.mealapp.ui.users

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.achsanit.mealapp.databinding.FragmentUsersBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsersFragment : Fragment() {
    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!
    private val usersViewModel: UsersViewModel by viewModel()
    private val adapter: UsersPagingAdapter by lazy {
        UsersPagingAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usersViewModel.getListUsers().observe(viewLifecycleOwner) { data ->
            adapter.submitData(lifecycle,data)
        }

        with(binding) {
            rvListUsers.adapter = adapter
            rvListUsers.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}