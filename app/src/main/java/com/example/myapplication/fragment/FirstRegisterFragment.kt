package com.example.myapplication.fragment

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentRegisterFirstBinding
import com.google.firebase.auth.FirebaseAuth

class FirstRegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterFirstBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClickListeners()

    }

    private fun onClickListeners() {
        binding.backButton.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

        binding.nextButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (!areLinesEmptyInsideLayout(binding.root, resources.getString(R.string.empty_line_error))
                && isEmailValid(email)) sendData(email, password)
            else Toast.makeText(context, resources.getText(R.string.cant_register), Toast.LENGTH_SHORT).show()
        }
    }


    private fun areLinesEmptyInsideLayout(viewGroup: ViewGroup, errorMessage: String?): Boolean {
        viewGroup.children.forEach {
            if (it is EditText && it.text.isEmpty()) {
                it.error = errorMessage
                return true
            }
        }
        return false
    }

    private fun sendData(email: String, password: String) {
        val action = FirstRegisterFragmentDirections
            .actionFirstRegisterFragmentToSecondRegisterFragment(email, password)
        Navigation.findNavController(binding.root).navigate(action)

    }

    private fun isEmailValid(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()

}