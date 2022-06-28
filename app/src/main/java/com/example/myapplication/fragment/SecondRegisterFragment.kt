package com.example.myapplication.fragment

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentRegisterSecondBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

class SecondRegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterSecondBinding

    private lateinit var email: String
    private lateinit var password: String

    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        onClickListeners()

    }

    private fun init() {
        email = SecondRegisterFragmentArgs.fromBundle(requireArguments()).email
        password = SecondRegisterFragmentArgs.fromBundle(requireArguments()).password
    }

    private fun onClickListeners() {
        binding.nextButton.setOnClickListener {
            val username = binding.usernameInput.text.toString()
            if (isUsernameValid(username)) {
                register(email, password, username)
                return@setOnClickListener
            }
            binding.usernameInput.error = resources.getString(R.string.invalid_username_error)
        }
    }

    private fun isUsernameValid(username: String) = username.isNotEmpty() && username.length > 10

    private fun register(email: String, password: String, username: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            when (it.isSuccessful) {
                true -> {
                    navigateToFragment(binding.root, R.id.action_secondRegisterFragment_to_loggedOutFragment)
                    showToast(requireContext(), "${resources.getString(R.string.success)} $username")
                }
                else -> showToast(requireContext(), resources.getText(R.string.cant_register).toString())
            }
        }
    }

    private fun navigateToFragment(view: View, action: Int) {
        Navigation.findNavController(view).navigate(action)
    }

    private fun showToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}