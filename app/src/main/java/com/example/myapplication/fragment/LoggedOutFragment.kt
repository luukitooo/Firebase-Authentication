package com.example.myapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLoggedOutBinding

class LoggedOutFragment : Fragment() {

    private lateinit var binding: FragmentLoggedOutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoggedOutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClickListeners()

    }

    private fun onClickListeners() {
        binding.logInButton.setOnClickListener {
            navigateToFragment(it, R.id.action_loggedOutFragment_to_logInFragment)
        }
        binding.registerButton.setOnClickListener {
            navigateToFragment(it, R.id.action_loggedOutFragment_to_firstRegisterFragment)
        }
    }

    private fun navigateToFragment(view: View, action: Int) {
        Navigation.findNavController(view).navigate(action)
    }
}